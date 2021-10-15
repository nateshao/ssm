package com.nateshao.jdbc;

import lombok.Data;

/**
 * @date Created by 邵桐杰 on 2021/10/15 21:36
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Data
public class Account {
    private Integer id;       // 账户id
    private String username; // 用户名
    private Double balance;  // 账户余额
}
