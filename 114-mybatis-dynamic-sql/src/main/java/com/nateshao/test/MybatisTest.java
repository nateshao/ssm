package com.nateshao.test;

import com.nateshao.po.Customer;
import com.nateshao.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:27
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 入门程序测试类
 */
public class MybatisTest {
    /**
     * 根据客户姓名和职业组合条件查询客户信息列表
     */
    @Test
    public void findCustomerByNameAndJobsTest() {
        // 通过工具类生成SqlSession对象
        SqlSession session = MybatisUtils.getSession();
        // 创建Customer对象，封装需要组合查询的条件
        Customer customer = new Customer();
        customer.setUsername("jack");
//		customer.setJobs("teacher");
        // 执行SqlSession的查询方法，返回结果集
        List<Customer> customers = session.selectList("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerByNameAndJobs", customer);
        // 输出查询结果信息
        for (Customer customer2 : customers) {
            // 打印输出结果
            System.out.println(customer2);
        }
        // 关闭SqlSession
        session.close();
    }

    /**
     * 根据客户姓名或职业查询客户信息列表
     */
    @Test
    public void findCustomerByNameOrJobsTest() {
        // 通过工具类生成SqlSession对象
        SqlSession session = MybatisUtils.getSession();
        // 创建Customer对象，封装需要组合查询的条件
        Customer customer = new Customer();
//	    customer.setUsername("jack");
//	    customer.setJobs("teacher");
        // 执行SqlSession的查询方法，返回结果集
        List<Customer> customers = session.selectList("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerByNameOrJobs", customer);
        // 输出查询结果信息
        for (Customer customer2 : customers) {
            // 打印输出结果
            System.out.println(customer2);
        }
        // 关闭SqlSession
        session.close();
    }

    /**
     * 更新客户
     */
    @Test
    public void updateCustomerTest() {
        // 获取SqlSession
        SqlSession sqlSession = MybatisUtils.getSession();
        // 创建Customer对象，并向对象中添加数据
        Customer customer = new Customer();
        customer.setId(3);
        customer.setPhone("13311111234");
        // 执行SqlSession的更新方法，返回的是SQL语句影响的行数
        int rows = sqlSession.update("com.nateshao.mapper"
                + ".CustomerMapper.updateCustomer", customer);
        // 通过返回结果判断更新操作是否执行成功
        if (rows > 0) {
            System.out.println("您成功修改了" + rows + "条数据！");
        } else {
            System.out.println("执行修改操作失败！！！");
        }
        // 提交事务
        sqlSession.commit();
        // 关闭SqlSession
        sqlSession.close();
    }

    /**
     * 根据客户编号批量查询客户信息
     */
    @Test
    public void findCustomerByIdsTest() {
        // 获取SqlSession
        SqlSession session = MybatisUtils.getSession();
        // 创建List集合，封装查询id
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        // 执行SqlSession的查询方法，返回结果集
        List<Customer> customers = session.selectList("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerByIds", ids);
        // 输出查询结果信息	
        for (Customer customer : customers) {
            // 打印输出结果
            System.out.println(customer);
        }
        // 关闭SqlSession
        session.close();
    }

    /**
     * bind元素的使用：根据客户名模糊查询客户信息
     */
    @Test
    public void findCustomerByNameTest() {
        // 通过工具类生成SqlSession对象
        SqlSession session = MybatisUtils.getSession();
        // 创建Customer对象，封装查询的条件
        Customer customer = new Customer();
        customer.setUsername("j");
        // 执行sqlSession的查询方法，返回结果集
        List<Customer> customers = session.selectList("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerByName", customer);
        // 输出查询结果信息	
        for (Customer customer2 : customers) {
            // 打印输出结果
            System.out.println(customer2);
        }
        // 关闭SqlSession
        session.close();
    }

}
