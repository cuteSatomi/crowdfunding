package com.zzx.crowd.mvc.config;

import com.zzx.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * 对 Spring Security 的User类进行扩展
 * 扩展类只需要原始Admin对象的登陆账户、密码以及角色权限属性
 * @author zzx
 * @date 2021-01-10 17:00
 */
public class SecurityAdmin extends User {

    private static final long serialVersionUID = 1L;

    /** 原始的Admin对象，包含Admin对象的全部属性 */
    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
        this.originalAdmin = originalAdmin;

        // 将原始Admin对象中的密码擦除，提升安全性
        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
