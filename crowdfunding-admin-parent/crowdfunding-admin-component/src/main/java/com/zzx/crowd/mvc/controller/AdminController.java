package com.zzx.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.exception.DeleteFailedException;
import com.zzx.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
     * 更新用户
     * @param admin
     * @param pageNum 当前页码
     * @param keyword 当前搜索关键词
     * @return
     */
    @RequestMapping("admin/update.html")
    public String update(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword
    ) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 根据id查询用户信息，将其转发到admin-edit页面
     *
     * @param adminId  用户id
     * @param modelMap
     * @return
     */
    @RequestMapping("admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }

    /**
     * 新增用户
     *
     * @param admin
     * @return
     */
    @RequestMapping("admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    /**
     * 根据adminId删除用户
     *
     * @param adminId 要删除的用户的id
     * @param pageNum 当前页码
     * @param keyword 关键字
     * @param session 需要从session中得到当前用户，自己无法删除自己
     * @return
     */
    @RequestMapping("admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword,
            HttpSession session
    ) {
        // 得到当前登陆用户
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        // 比较id，因为只有id是唯一的
        if (adminId.equals(admin.getId())) {
            throw new DeleteFailedException(CrowdConstant.MESSAGE_DELETE_FAILED);
        }
        adminService.remove(adminId);
        // 使用转发的话地址栏不会发生改变，导致删除完后返回页面时又进行了一次删除，浪费性能，因此选择重定向
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

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
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

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
