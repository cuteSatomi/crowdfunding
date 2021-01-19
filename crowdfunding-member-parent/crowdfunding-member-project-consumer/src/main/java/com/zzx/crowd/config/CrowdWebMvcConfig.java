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
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 这里的view-controller是在project微服务内部定义的，因此无需经过zuul网关，也就不需要加在zuul配置文件中的那个"/project"前缀
        registry.addViewController("/agree/protocol/page").setViewName("project-agree");
        registry.addViewController("/launch/project/page").setViewName("project-launch");
        registry.addViewController("/return/info/page").setViewName("project-return");
    }
}
