package com.nateshao.databind.po;

/**
 * @date Created by 邵桐杰 on 2021/10/17 15:23
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class Orders {
    private Integer ordersId; // 订单编号
    private User user;          // 用户POJO，所属用户
    public Integer getOrdersId() {
        return ordersId;
    }
    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}