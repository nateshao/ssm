package com.nateshao.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @date Created by 邵桐杰 on 2021/10/22 12:52
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 实现了HandlerInterceptor接口的自定义拦截器类
 */
public class CustomInterceptor implements HandlerInterceptor {
    /**
     * 该方法会在控制器方法前执行，其返回值表示是否中断后续操作。
     * 当其返回值为true时，表示继续向下执行；
     * 当其返回值为false时，会中断后续的所有操作。
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("CustomInterceptor...preHandle");
        //对拦截的请求进行放行处理
        return true;
    }

    /**
     * 该方法会在控制器方法调用之后，且解析视图之前执行。
     * 可以通过此方法对请求域中的模型和视图做出进一步的修改。
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("CustomInterceptor...postHandle");
    }

    /**
     * 该方法会在整个请求完成，即视图渲染结束之后执行。
     * 可以通过此方法实现一些资源清理、记录日志信息等工作。
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        System.out.println("CustomInterceptor...afterCompletion");
    }
}
