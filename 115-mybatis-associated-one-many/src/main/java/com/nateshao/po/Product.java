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
 * Description: 商品持久化类
 */
@Data
public class Product {
    private Integer id;  //商品id
    private String name; //商品名称
    private Double price;//商品单价
    private List<Orders> orders; //与订单的关联属性
}
