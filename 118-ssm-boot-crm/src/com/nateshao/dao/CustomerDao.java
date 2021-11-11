package com.nateshao.dao;

import com.nateshao.po.Customer;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/11/11 22:47
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface CustomerDao {
    // 客户列表
    public List<Customer> selectCustomerList(Customer customer);
    // 客户数
    public Integer selectCustomerListCount(Customer customer);

    // 创建客户
    public int createCustomer(Customer customer);
    // 通过id查询客户
    public Customer getCustomerById(Integer id);
    // 更新客户信息
    public int updateCustomer(Customer customer);
    // 删除客户
    int deleteCustomer (Integer id);
}
