package com.nateshao.test;

import com.nateshao.dao.CustomerDao;
import com.nateshao.mapper.CustomerMapper;
import com.nateshao.po.Customer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/26 15:12
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class DaoTest {
    @Test
    public void findCustomerByIdDaoTest() {
        ApplicationContext act =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 根据容器中Bean的id来获取指定的Bean
        CustomerDao customerDao =
                (CustomerDao) act.getBean("customerDao");
//	     CustomerDao customerDao = act.getBean(CustomerDao.class);
        Customer customer = customerDao.findCustomerById(1);
        System.out.println(customer);
    }

    @Test
    public void findCustomerByIdMapperTest() {
        ApplicationContext act =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomerMapper customerMapper = act.getBean(CustomerMapper.class);
        Customer customer = customerMapper.findCustomerByIdOne(1);
        System.out.println(customer);
    }
}
