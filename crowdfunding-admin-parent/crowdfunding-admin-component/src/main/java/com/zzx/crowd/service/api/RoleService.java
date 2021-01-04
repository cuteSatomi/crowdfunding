package com.zzx.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.zzx.crowd.entity.Role;

/**
 * @author zzx
 * @date 2020-12-31 15:07:07
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);
}
