package com.zzx.crowd.mvc.config;

import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.entity.Role;
import com.zzx.crowd.service.api.AdminService;
import com.zzx.crowd.service.api.AuthService;
import com.zzx.crowd.service.api.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzx
 * @date 2021-01-10 17:07
 */
@Component
public class CrowdUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    @Resource
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据账户查询
        Admin admin = adminService.getAdminByLoginAcct(username);

        // 获取adminId
        Integer adminId = admin.getId();

        // 根据adminId查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 根据adminId获取权限信息
        List<String> authNameList = authService.getAssignAuthNameByAdminId(adminId);

        // 创建集合对象来存储GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 遍历role集合存入角色信息，角色信息需要加前缀
        for (Role role : assignedRoleList) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }

        // 权限名称不需要加前缀
        for (String authName : authNameList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        // 最后封装SecurityAdmin对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);

        // 返回即可
        return securityAdmin;
    }
}
