package com.zzx.crowd.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zzx
 * @date 2021-01-18 10:07:24
 */
public class AccessPassResources {
    /**
     * 为允许访问的资源初始化一个set集合
     */
    public static final Set<String> PASS_RES_SET = new HashSet<String>();

    /** 未登录就允许访问的资源 */
    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page");
        PASS_RES_SET.add("/auth/member/to/login/page");
        PASS_RES_SET.add("/auth/member/do/logout");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/member/do/register");
        PASS_RES_SET.add("/auth/member/send/verify/code.json");
    }

    /**
     * 为静态资源初始化一个set集合
     */
    public static final Set<String> STATIC_RES_SET = new HashSet<String>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 判断所给请求是否是对于静态资源的请求
     * @param servletPath
     * @return
     */
    public static boolean isStaticRequest(String servletPath) {
        if (servletPath == null || servletPath.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        String[] split = servletPath.split("/");
        String firstLevelPath = split[1];
        return STATIC_RES_SET.contains(firstLevelPath);
    }
}
