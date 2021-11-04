package com.nateshao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date Created by 邵桐杰 on 2021/10/16 16:12
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class HelloController {

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

}
