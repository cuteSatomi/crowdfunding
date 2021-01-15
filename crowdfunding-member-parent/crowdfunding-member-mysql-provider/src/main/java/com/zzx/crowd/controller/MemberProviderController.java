package com.zzx.crowd.controller;

import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.po.MemberPO;
import com.zzx.crowd.service.MemberService;
import com.zzx.crowd.util.ResultEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zzx
 * @date 2021-01-14 10:59:11
 */
@RestController
public class MemberProviderController {

    @Resource
    private MemberService memberService;

    @RequestMapping("save/member/remote")
    public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO) {
        try {
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                // 由于在数据库中添加了loginacct的唯一约束，如果是DuplicateKeyException异常，返回账号不唯一的响应结果
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCOUNT_ALREADY_EXIST);
            }
        }
        return null;
    }

    /**
     * 调用本地service根据登录账号查询member
     *
     * @param loginacct
     * @return
     */
    @RequestMapping("get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
