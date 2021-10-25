package com.nateshao.po;

import lombok.Data;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/25 11:27
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 用户持久化类
 */
@Data
public class User {
    private Integer id;                 // 用户编号
    private String username;           // 用户姓名
    private String address;            // 用户地址
    private List<Orders> ordersList; //用户关联的订单
}
