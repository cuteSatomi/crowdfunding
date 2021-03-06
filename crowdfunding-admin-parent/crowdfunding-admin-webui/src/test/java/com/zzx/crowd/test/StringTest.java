package com.zzx.crowd.test;

import com.zzx.crowd.util.CrowdUtil;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zzx
 * @date 2020-12-29 17:15:06
 */
public class StringTest {

    @Test
    public void testMD5(){
        String str = "zzx";
        System.out.println(CrowdUtil.md5(str));
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("zzx"));
    }
}
