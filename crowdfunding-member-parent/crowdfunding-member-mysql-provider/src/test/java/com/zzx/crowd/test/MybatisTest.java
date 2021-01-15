package com.zzx.crowd.test;

import com.zzx.crowd.entity.po.MemberPO;
import com.zzx.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Resource
    private MemberPOMapper memberPOMapper;

    @Test
    public void testMapper() {

        MemberPO memberPO = new MemberPO(null, "zzx", "zzx", "蜘蛛侠", "zzx@hdsome.com", 1, 1, "赵忠祥", "zzz", 2);
        memberPOMapper.insert(memberPO);
    }

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(new BCryptPasswordEncoder().encode("zzx"));
        System.out.println(connection);
    }
}
