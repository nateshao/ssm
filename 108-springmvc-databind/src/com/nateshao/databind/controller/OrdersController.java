package com.nateshao.databind.controller;

import com.nateshao.databind.po.Orders;
import com.nateshao.databind.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date Created by 邵桐杰 on 2021/10/17 15:30
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class OrdersController {
    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping("/tofindOrdersWithUser")
    public String tofindOrdersWithUser() {
        return "orders";
    }

    /**
     * 查询订单和用户信息
     *
     * @param orders
     * @return
     */
    @RequestMapping("/findOrdersWithUser")
    public String findOrdersWithUser(Orders orders) {
//        Orders orders1 = new Orders();
//        orders1.setOrdersId(1);
//        orders1.setUser(new User());
        Integer ordersId = orders.getOrdersId();
        User user = orders.getUser();
        String username = user.getUsername();
        System.out.println("orderId=" + ordersId);
        System.out.println("username=" + username);
        return "success";
    }
}
