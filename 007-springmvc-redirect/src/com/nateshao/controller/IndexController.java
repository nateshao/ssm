package com.nateshao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date Created by 邵桐杰 on 2021/10/21 19:13
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class IndexController {

    @RequestMapping("/isLogin")
    public String login() {
        return "forword:/isLogin";
    }

    @RequestMapping("/isRegister")
    public String isRegister() {
        return "redirect:/isRegister";
    }


}
