package com.zzx.crowd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zzx
 * @date 2021-01-14 14:11:13
 */
@Controller
public class PortalController {

    @RequestMapping("/")
    public String showPortalPage(){
        return "portal";
    }
}
