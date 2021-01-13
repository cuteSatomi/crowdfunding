package com.zzx.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zzx
 * @date 2021-01-13 22:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
