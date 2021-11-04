package com.nateshao.service.impl;

import com.nateshao.dao.CustomerDao;
import com.nateshao.po.Customer;
import com.nateshao.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/26 21:54
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    //注解注入CustomerDao
    @Autowired
    private CustomerDao customerDao;

    //查询客户
    public Customer findCustomerById(Integer id) {
        return this.customerDao.findCustomerById(id);
    }

    @Override
    public List<Customer> findCustomer(Customer customer) {
        return this.customerDao.findCustomer(customer);
    }
}
