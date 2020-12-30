package com.zzx.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
     * 分页查询用户的方法
     *
     * @param keyword  查询用户的关键字，包括用户名、姓名和邮箱，不传入参数则默认空字符串
     * @param pageNum  分页查询的起始页码，默认第一页
     * @param pageSize 每页的大小，默认第五页
     * @param modelMap
     * @return 返回到页面admin-page
     */
    @RequestMapping("admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap

    ) {
        // 查询得到PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        //
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);

        return "admin-page";
    }

    /**
     * 注销登录，只需将session中的admin清空，这里的做法是强制使session失效
     *
     * @param session
     * @return
     */
    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        // 重定向到登录页面
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 登录入口
     *
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return 重定向到后台主页面admin-main
     */
    @RequestMapping("admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session) {

        // 根据账户查询账号，在service层已经处理好了各种异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        // 将查询出来的admin对象存入
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        // 如果使用转发，页面刷新的时候会重复提交表单，所以这里使用重定向，需要在spring-web-mvc.xml配置文件中配置
        // return "admin-main";
        return "redirect:/admin/to/main/page.html";
    }
}
