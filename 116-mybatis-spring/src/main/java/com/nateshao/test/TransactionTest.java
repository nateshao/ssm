package com.nateshao.test;

import com.nateshao.po.Customer;
import com.nateshao.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/26 15:13
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 测试事务
 */
public class TransactionTest {
    public static void main(String[] args) {
        ApplicationContext act =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomerService customerService =
                act.getBean(CustomerService.class);
        Customer customer = new Customer();
        customer.setUsername("zhangsan");
        customer.setJobs("manager");
        customer.setPhone("13233334444");
        customerService.addCustomer(customer);
    }
}

