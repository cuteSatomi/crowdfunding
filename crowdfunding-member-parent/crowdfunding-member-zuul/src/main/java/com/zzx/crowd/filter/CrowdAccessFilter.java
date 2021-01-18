package com.zzx.crowd.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zzx.crowd.constant.AccessPassResources;
import com.zzx.crowd.constant.CrowdConstant;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author zzx
 * @date 2021-01-18 13:59:06
 */
@Component
public class CrowdAccessFilter extends ZuulFilter {
    public String filterType() {
        // 在目标微服务前执行过滤
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String servletPath = request.getServletPath();
        // 如果是允许放行的路径，则直接放行
        if (AccessPassResources.PASS_RES_SET.contains(servletPath)) {
            return false;
        }
        // 如果访问的是静态资源，也直接放行
        return !AccessPassResources.isStaticRequest(servletPath);
    }

    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpSession session = request.getSession();
        Object loginMember = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        if(loginMember == null){
            // 需要使用重定向，所以要得到response对象
            HttpServletResponse response = requestContext.getResponse();
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
