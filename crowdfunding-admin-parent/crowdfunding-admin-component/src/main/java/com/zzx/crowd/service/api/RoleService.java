package com.zzx.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.zzx.crowd.entity.Role;

import java.util.List;

/**
 * @author zzx
 * @date 2020-12-31 15:07:07
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIdList);
}
