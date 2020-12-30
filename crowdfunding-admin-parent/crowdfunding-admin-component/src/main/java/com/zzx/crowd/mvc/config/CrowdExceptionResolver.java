package com.zzx.crowd.mvc.config;

import com.google.gson.Gson;
import com.zzx.crowd.constant.CrowdConstant;
import com.zzx.crowd.exception.AccessForbiddenException;
import com.zzx.crowd.exception.LoginAcctAlreadyExistException;
import com.zzx.crowd.exception.LoginAcctAlreadyExistForUpdateException;
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

    /**
     * 修改一个账户时，其账户已存在产生的异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyExistForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyExistForUpdateException(
            LoginAcctAlreadyExistForUpdateException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 新增一个账户已存在的用户产生的异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyExistException.class)
    public ModelAndView resolveLoginAcctAlreadyExistException(
            LoginAcctAlreadyExistException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 未登录状态下访问受保护资源产生的异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resolveAccessForbiddenException(
            AccessForbiddenException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 登录失败的异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return 转发到登录页面
     * @throws IOException
     */
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
     * @param viewName 要去的页面
     * @param exception      拦截到的异常
     * @param request        当前请求对象
     * @param response       当前响应对象
     * @return
     */
    //@ExceptionHandler(value = NullPointerException.class)
    public ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request,
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
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
