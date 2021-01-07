package com.zzx.crowd.mvc.controller;

import com.zzx.crowd.entity.Role;
import com.zzx.crowd.service.api.AdminService;
import com.zzx.crowd.service.api.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzx
 * @date 2021-01-07 10:51:44
 */
@Controller
public class AssignController {

    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    /**
     * 建立新的用户角色关系
     *
     * @param adminId
     * @param pageNum
     * @param keyword
     * @param roleIdList 允许用户删除全部角色再提交
     * @return
     */
    @RequestMapping("assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
    ) {
        roleService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 根据当前用户查询已经分配的角色以及未分配的角色
     *
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {

        // 查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // addAttribute其实就是request.addAttribute("attrName",attrValue) ，实质上是转发，因此参数没有接受pageNum等信息，因为这些信息会被转发回去
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

        return "assign-role";
    }
}
