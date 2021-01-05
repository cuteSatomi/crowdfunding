package com.zzx.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.zzx.crowd.entity.Role;
import com.zzx.crowd.service.api.RoleService;
import com.zzx.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zzx
 * @date 2020-12-31 15:08:39
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 根据所给的id数组删除对应的角色
     * @param roleIdList
     * @return
     */
    @ResponseBody
    @RequestMapping("role/remove/by/role/id/array.json")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }

    /**
     * 根据主键更新角色
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("role/update.json")
    public ResultEntity<String> updateRole(Role role) {
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("role/save.json")
    public ResultEntity<String> saveRole(Role role) {
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    /**
     * 分页查询角色的方法
     *
     * @param keyword  查询角色名称，不传入参数则默认空字符串
     * @param pageNum  分页查询的起始页码，默认第一页
     * @param pageSize 每页的大小，默认第五页
     * @return 返回到页面admin-page
     */
    @ResponseBody
    @RequestMapping("role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword


    ) {
        // 查询得到PageInfo对象
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        // 封装到ResultEntity中返回，如果出现异常交给异常映射机制处理
        return ResultEntity.successWithData(pageInfo);
    }
}
