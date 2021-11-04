package com.nateshao.test;

import com.nateshao.po.Customer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/22 22:41
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: Mybatis 测试 CRUD
 */

public class MybatisTest {

    /**
     * 根据客户编号查询客户信息
     *
     * @throws Exception
     */
    @Test
    public void findCustomerByIdTest() throws Exception {
        // 1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream =
                Resources.getResourceAsStream(resource);
        // 2、根据配置文件构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 3、通过SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、SqlSession执行映射文件中定义的SQL，并返回映射结果
        Customer customer = sqlSession.selectOne("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerById", 1);
        // 打印输出结果
        System.out.println(customer.toString());
        // 5、关闭SqlSession
        sqlSession.close();
    }

    /**
     * 根据用户名称来模糊查询用户信息列表
     *
     * @throws Exception
     */
    @Test
    public void findCustomerByNameTest() throws Exception {
        // 1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 2、根据配置文件构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 3、通过SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、SqlSession执行映射文件中定义的SQL，并返回映射结果
        List<Customer> customers = sqlSession.selectList("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerByName", "j");
        for (Customer customer : customers) {
            //打印输出结果集
            System.out.println(customer);
        }
        // 5、关闭SqlSession
        sqlSession.close();
    }

    /**
     * 添加客户
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addCustomerTest() throws Exception {
        // 1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 2、根据配置文件构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 3、通过SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、SqlSession执行添加操作
        // 4.1创建Customer对象，并向对象中添加数据
        Customer customer = new Customer();
        customer.setUsername("rose");
        customer.setJobs("student");
        customer.setPhone("13333533092");
        // 4.2执行SqlSession的插入方法，返回的是SQL语句影响的行数
        int rows = sqlSession.insert("com.nateshao.mapper"
                + ".CustomerMapper.addCustomer", customer);
        // 4.3通过返回结果判断插入操作是否执行成功
        if (rows > 0) {
            System.out.println("您成功插入了" + rows + "条数据！");
        } else {
            System.out.println("执行插入操作失败！！！");
        }
        // 4.4提交事务
        sqlSession.commit();
        // 5、关闭SqlSession
        sqlSession.close();
    }

    /**
     * 更新客户
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void updateCustomerTest() throws Exception {
        // 1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 2、根据配置文件构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 3、通过SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、SqlSession执行更新操作
        // 4.1创建Customer对象，对对象中的数据进行模拟更新
        Customer customer = new Customer();
        customer.setId(4);
        customer.setUsername("rose");
        customer.setJobs("programmer");
        customer.setPhone("13311111111");
        // 4.2执行SqlSession的更新方法，返回的是SQL语句影响的行数
        int rows = sqlSession.update("com.nateshao.mapper"
                + ".CustomerMapper.updateCustomer", customer);
        // 4.3通过返回结果判断更新操作是否执行成功
        if (rows > 0) {
            System.out.println("您成功修改了" + rows + "条数据！");
        } else {
            System.out.println("执行修改操作失败！！！");
        }
        // 4.4提交事务
        sqlSession.commit();
        // 5、关闭SqlSession
        sqlSession.close();
    }

    /**
     * 删除客户
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteCustomerTest() throws Exception {
        // 1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 2、根据配置文件构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 3、通过SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、SqlSession执行删除操作
        // 4.1执行SqlSession的删除方法，返回的是SQL语句影响的行数
        int rows = sqlSession.delete("com.nateshao.mapper"
                + ".CustomerMapper.deleteCustomer", 4);
        // 4.2通过返回结果判断删除操作是否执行成功
        if (rows > 0) {
            System.out.println("您成功删除了" + rows + "条数据！");
        } else {
            System.out.println("执行删除操作失败！！！");
        }
        // 4.3提交事务
        sqlSession.commit();
        // 5、关闭SqlSession
        sqlSession.close();
    }


}
