package com.zzx.crowd.test;

import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.mapper.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * @Author zzx
 * @Date 2020-12-24 20:57
 */
//在类上标记必要的注解，spring整合junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {

    @Resource
    private DataSource dataSource;

    @Resource
    private AdminMapper adminMapper;

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
