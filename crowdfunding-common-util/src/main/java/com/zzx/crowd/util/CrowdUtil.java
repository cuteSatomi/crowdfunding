package com.zzx.crowd.util;

import com.zzx.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zzx
 * @date 2020-12-26 18:51
 */
public class CrowdUtil {

    /**
     * md5加密的工具方法
     *
     * @param source
     * @return
     */
    public static String md5(String source) {
        // 如果传入的source是null或者是空字符串则抛出异常
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            // 加密方式
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // messageDigest的digest加密方法需要原始字符串的字节数组作为输入
            byte[] input = source.getBytes();
            // 得到加密后的字节数组
            byte[] output = messageDigest.digest(input);
            // 按照输出的字节数组创建BigInteger对象
            // 定义signum，1表示正数
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            // 将得到的bigInteger按16进制转换为字符串
            int radix = 16;
            String encode = bigInteger.toString(radix).toUpperCase();
            // 将最终结果返回
            return encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 判断所给请求是普通请求还是AJAX请求
     *
     * @param request 需要判断的请求
     * @return 返回值为true则表示该请求是AJAX，false则表示是普通请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        // 获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestedHeader = request.getHeader("X-Requested-With");

        // 进行判断
        return (acceptHeader != null && acceptHeader.contains("application/json")) ||
                ("XMLHttpRequest".equals(xRequestedHeader));
    }
}
