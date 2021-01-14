package com.zzx.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author zzx
 * @date 2021-01-14 14:45:10
 */
// 网关的主启动类需要加@EnableZuulProxy这个注解
@EnableZuulProxy
@SpringBootApplication
public class CrowdMemberZuul {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMemberZuul.class, args);
    }
}
