package com.zzx.crowd.service.api;

import com.zzx.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @date 2021-01-07 16:54:36
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignAuthNameByAdminId(Integer adminId);
}
