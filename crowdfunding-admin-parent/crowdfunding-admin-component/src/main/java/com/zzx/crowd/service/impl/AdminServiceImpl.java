package com.zzx.crowd.service.impl;

import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.mapper.AdminMapper;
import com.zzx.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzx
 * @date 2020-12-25 15:46:04
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }
}
