package com.zzx.crowd;

import com.zzx.crowd.entity.po.MemberPO;
import com.zzx.crowd.entity.vo.ProjectVO;
import com.zzx.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zzx
 * @date 2021-01-14 10:49:13
 */
// 远程调用对应crowd-mysql微服务名称的接口
@FeignClient("crowd-mysql")
@Component
public interface MySQLRemoteService {

    /**
     * 调用mysql的远程接口
     * @param memberPO
     * @return
     */
    @RequestMapping("save/member/remote")
    public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);

    /**
     * 根据RequestMapping中的url调用远程接口(即crowd-mysql微服务下的同url同方法名同参数列表的方法)进行查询
     * @param loginacct
     * @return
     */
    @RequestMapping("get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO,@RequestParam("memberId") Integer memberId);
}
