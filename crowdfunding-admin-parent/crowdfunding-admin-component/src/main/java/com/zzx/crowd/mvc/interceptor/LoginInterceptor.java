package com.zzx.crowd.mvc.interceptor;

import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.entity.Admin;
import com.zzx.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zzx
 * @date 2020-12-30 14:26:01
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 得到session对象
        HttpSession session = request.getSession();

        // 从session中取出admin对象
        Admin admin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

        // 判断admin对象是否为空，如果为空则说明未登录，抛出自定义异常
        if(admin == null){
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }
        // 如果是登录状态，则放行
        return true;
    }
}
