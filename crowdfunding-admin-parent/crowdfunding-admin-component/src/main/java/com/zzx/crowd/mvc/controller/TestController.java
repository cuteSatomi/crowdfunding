package com.zzx.crowd.mvc.controller;

import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.entity.ParamData;
import com.zzx.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zzx
 * @date 2020-12-26 12:30
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("ssm.html")
    public String testSsm(ModelMap modelMap) {
        List<Admin> adminList = adminService.listAll();
        modelMap.addAttribute("adminList", adminList);
        System.out.println(10/0);
        return "target";
    }

    @ResponseBody
    @RequestMapping("sendArray/one.html")
    public String testSendArrayOne(@RequestParam("array[]") List<Integer> array) {
        for (Integer num : array) {
            System.out.println("number = " + num);
        }
        return "成功";
    }

    @ResponseBody
    @RequestMapping("sendArray/two.html")
    public String testSendArrayTwo(ParamData paramData) {
        List<Integer> array = paramData.getArray();
        for (Integer num : array) {
            System.out.println("number = " + num);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("sendArray/three.html")
    public String testSendArrayThree(@RequestBody List<Integer> array) {
        for (Integer num : array) {
            System.out.println("number = " + num);
        }
        return "success";
    }
}
