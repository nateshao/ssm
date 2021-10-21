package com.nateshao.json.controller;

import com.nateshao.json.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @date Created by 邵桐杰 on 2021/10/21 22:17
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class UserController {
    /**
     * 接收页面请求的JSON数据，并返回JSON格式结果
     */
    @RequestMapping("/testJson")
    @ResponseBody
    public User testJson(@RequestBody User user) {
        // 打印接收的JSON格式数据
        System.out.println(user);
        // 返回JSON格式的响应
        return user;
    }

    /**
     * 接收RESTful风格的请求,其接收方式为GET
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User selectUser(@PathVariable("id") String id) {
        //查看数据接收
        System.out.println("id=" + id);
        User user = new User();
        //模拟根据id查询出到用户对象数据
        if (id.equals("1234")) {
            user.setUsername("tom");
        }
        //返回JSON格式的数据
        return user;
    }

}
