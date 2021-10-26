package com.nateshao.service.impl;

import com.nateshao.mapper.CustomerMapper;
import com.nateshao.po.Customer;
import com.nateshao.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date Created by 邵桐杰 on 2021/10/26 15:11
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
    //注解注入CustomerMapper
    @Autowired
    private CustomerMapper customerMapper;
    //添加客户
    public void addCustomer(Customer customer) {
        this.customerMapper.addCustomer(customer);
//        int i=1/0; //模拟添加操作后系统突然出现的异常问题
    }
}
