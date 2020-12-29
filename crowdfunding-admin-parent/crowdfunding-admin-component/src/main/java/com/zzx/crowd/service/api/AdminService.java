package com.zzx.crowd.service.api;

import com.zzx.crowd.entity.Admin;

import java.util.List;

/**
 * @author zzx
 * @date 2020-12-25 15:45:47
 */
public interface AdminService {

    /**
     * 存入管理员
     * @param admin 要存储的管理员信息
     */
    void saveAdmin(Admin admin);

    /**
     * 查询出所有的管理员信息
     * @return 管理员的集合
     */
    List<Admin> listAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);
}
