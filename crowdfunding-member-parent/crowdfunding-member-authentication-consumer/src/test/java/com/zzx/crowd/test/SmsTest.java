package com.zzx.crowd.test;

import com.aliyuncs.exceptions.ClientException;
import com.zzx.crowd.config.SmsProperties;
import com.zzx.crowd.utils.SmsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zzx
 * @date 2021-01-14 15:58:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {

    @Autowired
    private SmsProperties smsProperties;

    @Test
    public void testSms() throws ClientException {

        SmsUtils smsUtils = new SmsUtils();
        smsUtils.sendSms("13757575849","696969","乐优商城","SMS_203672955");
    }
}
