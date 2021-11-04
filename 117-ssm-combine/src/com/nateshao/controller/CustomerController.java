package com.nateshao.controller;

import com.nateshao.po.Customer;
import com.nateshao.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/26 21:51
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 根据id查询客户详情
     */
    @RequestMapping("/findCustomerById")
    public String findCustomerById(Integer id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        //返回客户信息展示页面
        return "customer";
    }

    /**
     * 根据id查询客户详情
     */
    @RequestMapping("/findCustomer")
    public String findCustomer(Customer customer, Model model) {
        List<Customer> customerList = customerService.findCustomer(customer);
        for (Customer customer1 : customerList) {
            System.out.println(customer1);
        }
        model.addAttribute("customer", customer);
        //返回客户信息展示页面
        return "customer";
    }

}
