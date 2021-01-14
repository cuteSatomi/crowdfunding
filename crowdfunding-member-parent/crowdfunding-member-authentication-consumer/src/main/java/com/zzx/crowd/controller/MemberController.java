package com.zzx.crowd.controller;

import com.aliyuncs.exceptions.ClientException;
import com.zzx.crowd.RedisRemoteService;
import com.zzx.crowd.config.SmsProperties;
import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.util.NumberUtils;
import com.zzx.crowd.util.ResultEntity;
import com.zzx.crowd.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping("auth/member/send/verify/code.json")
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
