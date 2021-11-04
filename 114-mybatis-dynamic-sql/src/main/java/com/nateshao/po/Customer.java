package com.nateshao.po;

import lombok.Data;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:17
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 客户持久化类
 */
@Data
public class Customer {
    private Integer id;       // 主键id
    private String username; // 客户名称
    private String jobs;      // 职业
    private String phone;     // 电话
}