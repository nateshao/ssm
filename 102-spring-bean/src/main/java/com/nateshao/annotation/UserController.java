package com.nateshao.annotation;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @date Created by 邵桐杰 on 2021/10/14 10:17
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller("userController")
public class UserController {

    @Resource(name="userService")
    private UserService userService;

    public void save(){
        this.userService.save();
        System.out.println("userController...save...");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
