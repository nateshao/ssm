package com.nateshao.po;

import lombok.Data;

import java.util.Date;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:19
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Data
public class User {
    private Integer id;
    private String username;
    private Date birthday;
    private char sex;
    private String address;

}
