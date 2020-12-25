package com.zzx.crowd.test;

import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.mapper.AdminMapper;
import com.zzx.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author zzx
 * @Date 2020-12-24 20:57
 */
//在类上标记必要的注解，spring整合junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Resource
    private DataSource dataSource;

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private AdminService adminService;

    @Test
    public void testTx(){
        Admin admin = new Admin(null, "wll", "456", "武老赖", "wll@mgbd.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testLog(){
        // 获取logger对象
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);

        // 根据不同的日志级别打印日志
        logger.debug("debug level!!!");
        logger.debug("debug level!!!");
        logger.debug("debug level!!!");

        logger.info("info level!!!");
        logger.info("info level!!!");
        logger.info("info level!!!");

        logger.warn("warn level!!!");
        logger.warn("warn level!!!");
        logger.warn("warn level!!!");

        logger.error("error level!!!");
        logger.error("error level!!!");
        logger.error("error level!!!");
    }

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "zzx", "123", "蜘蛛侠", "zzx@hdsome.com", null);
        int count = adminMapper.insert(admin);
        System.out.println("受影响的行数="+count);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
