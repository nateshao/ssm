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
 * Description: 订单持久化类
 */
@Data
public class Orders {
    private Integer id;    //订单id
    private String number;//订单编号
    //关联商品集合信息
    private List<Product> productList;
}
