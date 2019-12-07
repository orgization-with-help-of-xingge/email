package com.hdu.email.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 功能描述: 登录拦截器
 * @Author: sixl
 * @Date: 2019/12/7 11:47
 */
@Slf4j
public class EmailUserInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //拦截特定的请求方式（vue的OPINION请求直接放行）
        String method = httpServletRequest.getMethod();
        if (!"POST".equals(method)){
            return true;
        }
        ServletContext session = httpServletRequest.getServletContext();
        String parameter = httpServletRequest.getHeader("X-Token");
        String key=httpServletRequest.getHeader("X-Token")+"@sixl.xyz";
        if (session.getAttribute(key) != null){
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        String[] split = requestURL.toString().split(":");
        log.info(split[1].substring(2)+"进行访问");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
