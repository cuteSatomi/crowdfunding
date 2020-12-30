package com.zzx.crowd.service.api;

import com.github.pagehelper.PageInfo;
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

    /**
     * 分页查询
     * @param keyword 查询的关键字
     * @param pageNum 查询起始页码
     * @param pageSize 一页的大小
     * @return PageInfo对象，其实是一个List
     */
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
