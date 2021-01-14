package com.zzx.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zzx
 * @date 2021-01-14 14:00:14
 */
@EnableFeignClients
@SpringBootApplication
public class CrowdMemberAuth {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMemberAuth.class, args);
    }
}
