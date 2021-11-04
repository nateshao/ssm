package com.nateshao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @date Created by 邵桐杰 on 2021/10/16 21:37
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String handleRequest(HttpServletRequest request,
                                HttpServletResponse response, Model model) throws Exception {
        // 向模型对象中添加数据
        model.addAttribute("msg", "这是我的第一个Spring MVC程序");
        // 返回视图页面
        return "hello";
    }
}




















