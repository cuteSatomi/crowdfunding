package com.zzx.crowd.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zzx
 * @date 2020-12-26 18:51
 */
public class CrowdUtil {

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
