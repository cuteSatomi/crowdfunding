package com.zzx.crowd.controller;

import com.aliyuncs.exceptions.ClientException;
import com.zzx.crowd.MySQLRemoteService;
import com.zzx.crowd.RedisRemoteService;
import com.zzx.crowd.config.SmsProperties;
import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.po.MemberPO;
import com.zzx.crowd.entity.vo.MemberLoginVO;
import com.zzx.crowd.entity.vo.MemberVO;
import com.zzx.crowd.util.NumberUtils;
import com.zzx.crowd.util.ResultEntity;
import com.zzx.crowd.utils.SmsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zzx
 * @date 2021-01-14 17:11:38
 */
@Controller
public class MemberController {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mysqlRemoteService;

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/auth/member/do/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:http://localhost:8888/";
    }

    /**
     * 登录接口
     *
     * @param loginacct
     * @param userpswd
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/auth/member/do/login")
    public String login(@RequestParam("loginacct") String loginacct, @RequestParam("userpswd") String userpswd, ModelMap modelMap, HttpSession session) {
        // 通过提交表单中的账号去查库看是否存在该账号
        ResultEntity<MemberPO> resultEntity = mysqlRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-login";
        }
        MemberPO memberPO = resultEntity.getData();
        if (memberPO == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 对比数据库中与表单提交的密码看是否一致
        String passwordDataBase = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 由于每次生成的密码是随机的，所以无法使用将表单的密码加密与数据库中的密码比对这种方法，BCryptPasswordEncoder提供了matches方法
        boolean isMatch = passwordEncoder.matches(userpswd, passwordDataBase);
        if (!isMatch) {
            // 如果密码不正确，回到登录页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        // 如果密码正确，则登录成功，将用户存储到session中
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);

        return "redirect:http://localhost:8888/auth/member/to/center/page";
    }

    @RequestMapping("/auth/member/do/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {
        // 获取用户输入的手机号，从redis中读取该手机号对应的验证码
        String phoneNum = memberVO.getPhoneNum();
        String key = CrowdConstant.REDIS_VERIFY_CODE_PREFIX + phoneNum;
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);
        // 获取从redis中的查询结果并且进行判断
        String result = resultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
        }

        // 如果从redis中请求成功，则从结果中获取验证码
        String redisCode = resultEntity.getData();
        if (redisCode == null) {
            // 如果查询出来的结果是null，返回验证码过期的提示信息，并返回到注册页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        // 验证码不为null，则比较表单中提交的验证码与redis中查询出的验证码是否一致
        String formCode = memberVO.getCode();
        if (!Objects.equals(formCode, redisCode)) {
            // 如果不相等，则返回验证码错误的提示消息给注册页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }

        // 执行到这说明验证码正确，需要从redis中删除该验证码
        redisRemoteService.removeRedisKeyRemote(key);

        // 对密码进行加密然后执行保存
        // 得到BCryptPasswordEncoder对象
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberVO.setUserpswd(passwordEncoder.encode(memberVO.getUserpswd()));

        // 创建空的MemberPO对象，并将MemberVO的属性复制到MemberPO
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);

        // 调用mysql提供的远程方法执行保存
        ResultEntity<String> saveMemberResultEntity = mysqlRemoteService.saveMemberRemote(memberPO);

        // 保存方法也可能出错，所以需要判断
        if (ResultEntity.FAILED.equals(saveMemberResultEntity.getResult())) {
            // 如果发生错误，携带错误信息到注册页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberResultEntity.getMessage());
            return "member-reg";
        }
        // 使用重定向避免刷新浏览器导致重新执行注册流程
        return "redirect:/auth/member/to/login/page";
    }

    @ResponseBody
    @RequestMapping("/auth/member/send/verify/code.json")
    public ResultEntity<String> sendVerifyCode(@RequestParam("phoneNum") String phoneNum) throws ClientException {
        // 生成验证码
        String code = NumberUtils.generateCode(6);
        // 尝试发送短信
        ResultEntity<String> sendMessageResultEntity = smsUtils.sendSms(phoneNum, code, smsProperties.getSignName(), smsProperties.getVerifyCodeTemplate());

        // 判断短信发送结果
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
            // 发送成功则将验证码存入redis，过期时间五分钟
            String key = CrowdConstant.REDIS_VERIFY_CODE_PREFIX + phoneNum;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 5, TimeUnit.MINUTES);
            // 存入redis的操作也可能发生失败
            if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
                return ResultEntity.successWithoutData();
            } else {
                return saveCodeResultEntity;
            }
        } else {
            return sendMessageResultEntity;
        }
    }
}
