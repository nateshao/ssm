package com.nateshao.service;

import com.nateshao.po.Customer;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/26 21:53
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface CustomerService {
    public Customer findCustomerById(Integer id);

    List<Customer> findCustomer(Customer customer);
}
