package com.nateshao.controller;

import com.nateshao.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.nateshao.service.UserService;
import com.nateshao.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @date Created by 邵桐杰 on 2021/10/21 19:37
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(User user, HttpSession httpSession, Model model) {

        if (userService.login(user)) {
            System.out.println("login successful");
            httpSession.setAttribute("u", user);
            return "main";
        } else {
            System.out.println("login failure..");
            model.addAttribute("inputname", user.getName());
            model.addAttribute("inputpwd", user.getPwd());
            return "login";
        }

    }

    @RequestMapping("/register")
    public String register() {

        return "register";
    }


}
