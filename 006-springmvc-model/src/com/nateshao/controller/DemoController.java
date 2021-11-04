package com.nateshao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @date Created by 邵桐杰 on 2021/10/21 18:33
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class DemoController {
    @RequestMapping("/testModel")
    public ModelAndView testModel() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("success", "testModel  nateshao");
        mav.setViewName("hello");
        return mav;
    }

}
