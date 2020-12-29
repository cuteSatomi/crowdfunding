package com.zzx.crowd.mvc.controller;

import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author zzx
 * @date 2020-12-29 15:22:50
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录入口
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return 后台主页面admin-main
     */
    @RequestMapping("admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session) {

        // 根据账户查询账号，在service层已经处理好了各种异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
        // 将查询出来的admin对象存入
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "admin-main";
    }
}
