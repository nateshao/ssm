package com.nateshao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @date Created by 邵桐杰 on 2021/10/21 18:11
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping("testModelAndView")
    public ModelAndView testModelAndView(){

        ModelAndView mav = new ModelAndView();
        mav.addObject("success","程序员千羽。。");

        mav.setViewName("hello");
        return mav;
    }
}
