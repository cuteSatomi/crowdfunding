package com.zzx.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zzx
 * @date 2021-01-14 16:44:42
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器访问的地址
        String urlPath = "auth/member/to/reg/page";
        // 目标视图的名称
        String viewName = "member-reg";
        registry.addViewController(urlPath).setViewName(viewName);
        registry.addViewController("auth/member/to/login/page").setViewName("member-login");
        registry.addViewController("auth/member/to/center/page").setViewName("member-center");
    }
}
