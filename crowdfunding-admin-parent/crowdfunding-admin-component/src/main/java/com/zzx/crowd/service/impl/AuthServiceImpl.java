package com.zzx.crowd.service.impl;

import com.zzx.crowd.entity.Auth;
import com.zzx.crowd.entity.AuthExample;
import com.zzx.crowd.mapper.AuthMapper;
import com.zzx.crowd.service.api.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @date 2021-01-07 16:54:44
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 获取roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        // 删除旧的关联关系
        authMapper.deleteOldRelationship(roleId);

        // 获取authIdList
        List<Integer> authIdList = map.get("authIdArray");

        // 判断authIdList是否为空
        if (authIdList != null && authIdList.size() > 0) {
            // 建立新的关联关系
            authMapper.insertNewRelationship(roleId, authIdList);
        }

    }

    @Override
    public List<String> getAssignAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignAuthNameByAdminId(adminId);
    }
}
