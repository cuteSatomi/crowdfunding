package com.zzx.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * SMS阿里短信接口的配置读取类
 *
 * @author zzx
 * @date 2020-09-29 11:05:01
 */
@ConfigurationProperties(prefix = "crowd.sms") // 读取crowd.sms为前缀的
@EnableConfigurationProperties(SmsProperties.class)
public class SmsProperties {
    // 属性名，与application.yml中的配置名保持一致
    String accessKeyId;
    String accessKeySecret;
    String signName;
    String verifyCodeTemplate;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVerifyCodeTemplate() {
        return verifyCodeTemplate;
    }

    public void setVerifyCodeTemplate(String verifyCodeTemplate) {
        this.verifyCodeTemplate = verifyCodeTemplate;
    }
}
