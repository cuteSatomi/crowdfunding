package com.zzx.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzx.crowd.entity.Role;
import com.zzx.crowd.entity.RoleExample;
import com.zzx.crowd.mapper.RoleMapper;
import com.zzx.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzx
 * @date 2020-12-31 15:07:13
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页查询角色
     *
     * @param pageNum  查询的页码
     * @param pageSize 页码的大小
     * @param keyword  查询的关键字
     * @return
     */
    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 通过mapper查询结果
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        // 返回PageInfo对象
        return new PageInfo(roleList);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(roleIdList);

        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

    /**
     * 需要先根据adminId删除中间表中的对应信息，再插入新的关系
     *
     * @param adminId
     * @param roleIdList
     */
    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 删除旧的关系
        roleMapper.deleteOldRelationship(adminId);
        // 插入新的关系
        if (roleIdList != null && roleIdList.size() > 0) {
            roleMapper.insertNewRelationship(adminId, roleIdList);
        }
    }
}
