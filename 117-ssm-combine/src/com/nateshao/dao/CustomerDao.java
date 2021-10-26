package com.nateshao.dao;

import com.nateshao.po.Customer;

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
public interface CustomerDao {
    /**
     * 根据id查询客户信息
     */
    public Customer findCustomerById(Integer id);

    public List<Customer> findCustomer(Customer customer);
}
