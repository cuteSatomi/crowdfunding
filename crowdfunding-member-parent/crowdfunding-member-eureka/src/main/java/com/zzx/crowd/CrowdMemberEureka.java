package com.zzx.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zzx
 * @date 2021-01-13 20:59
 */
// eureka的server端需要加@EnableEurekaServer 这个注解
@EnableEurekaServer
@SpringBootApplication
public class CrowdMemberEureka {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMemberEureka.class, args);
    }
}
