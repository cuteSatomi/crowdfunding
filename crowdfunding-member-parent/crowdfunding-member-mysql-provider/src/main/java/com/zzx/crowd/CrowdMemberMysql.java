package com.zzx.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zzx
 * @date 2021-01-13 21:58
 */
@SpringBootApplication
// mapper接口包扫描目录
@MapperScan("com.zzx.crowd.mapper")
public class CrowdMemberMysql {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMemberMysql.class, args);
    }
}
