package com.nateshao.databind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @date Created by 邵桐杰 on 2021/10/17 15:26
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class DateController {
    /**
     * 使用自定义类型数据绑定日期数据
     */
    @RequestMapping("/customDate")
    public String CustomDate(Date date) {
        System.out.println("date = " + date);
        return "success";
    }
}
