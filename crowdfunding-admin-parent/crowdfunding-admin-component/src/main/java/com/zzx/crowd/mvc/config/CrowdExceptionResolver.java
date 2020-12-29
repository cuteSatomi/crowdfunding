package com.zzx.crowd.mvc.config;

import com.google.gson.Gson;
import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.exception.LoginFailedException;
import com.zzx.crowd.util.CrowdUtil;
import com.zzx.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zzx
 * @date 2020-12-26 19:08
 */

// ControllerAdvice表示注解表示当前类是一个基于注解的异常处理类
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String viewName = "admin-login";

        return commonResolve(viewName, exception, request, response);
    }

    /**
     * ExceptionHandler表示将一个具体的异常类型和方法关联起来
     *
     * @param targetViewName 要去的页面
     * @param exception 拦截到的异常
     * @param request 当前请求对象
     * @param response 当前响应对象
     * @return
     */
    //@ExceptionHandler(value = NullPointerException.class)
    public ModelAndView commonResolve(String targetViewName,Exception exception, HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        // 判断请求类型，是否是ajax请求
        boolean isAjax = CrowdUtil.isAjaxRequest(request);
        if (isAjax) {
            // 创建resultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            // 将resultEntity对象转化为JSON字符串
            String json = new Gson().toJson(resultEntity);
            // 将JSON字符串作为响应返回给浏览器
            response.getWriter().write(json);
            // 如果是ajax请求，则直接返回null，不需要ModelAndView对其处理
            return null;
        }

        // 如果不是ajax请求，返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        modelAndView.setViewName(targetViewName);
        return modelAndView;
    }
}
