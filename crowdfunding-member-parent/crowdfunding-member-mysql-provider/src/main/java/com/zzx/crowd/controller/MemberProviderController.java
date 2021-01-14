package com.zzx.crowd.controller;

import com.zzx.crowd.entity.po.MemberPO;
import com.zzx.crowd.service.MemberService;
import com.zzx.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zzx
 * @date 2021-01-14 10:59:11
 */
@RestController
public class MemberProviderController {

    @Resource
    private MemberService memberService;

    /**
     * 调用本地service根据登录账号查询member
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
