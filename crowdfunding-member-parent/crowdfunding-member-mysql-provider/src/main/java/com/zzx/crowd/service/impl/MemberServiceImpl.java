package com.zzx.crowd.service.impl;

import com.zzx.crowd.entity.po.MemberPO;
import com.zzx.crowd.entity.po.MemberPOExample;
import com.zzx.crowd.mapper.MemberPOMapper;
import com.zzx.crowd.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzx
 * @date 2021-01-14 11:01:42
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberPOMapper memberPOMapper;

    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample example = new MemberPOExample();
        MemberPOExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<MemberPO> memberPOList = memberPOMapper.selectByExample(example);
        return memberPOList.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
