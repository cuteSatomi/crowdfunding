package com.zzx.crowd.service;

import com.zzx.crowd.entity.po.MemberPO;

/**
 * @author zzx
 * @date 2021-01-14 11:01:16
 */
public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);
}
