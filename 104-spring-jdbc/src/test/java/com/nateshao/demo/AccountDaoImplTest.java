package com.nateshao.demo;

import com.nateshao.jdbc.Account;
import com.nateshao.jdbc.AccountDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @date Created by 邵桐杰 on 2021/10/15 19:38
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class AccountDaoImplTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void addAccount() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        com.nateshao.jdbc.AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 创建Account对象，并向Account对象中添加数据
        Account account = new Account();
        account.setUsername("千羽");
        account.setBalance(1200.00);
        int i = accountDao.addAccount(account);
        System.out.println(i);
    }

    @Test
    public void updateAccount() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        com.nateshao.jdbc.AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 创建Account对象，并向Account对象中添加数据
        Account account = new Account();
        account.setId(2);
        account.setUsername("程序员千羽");
        account.setBalance(2000.00);
        int num = accountDao.updateAccount(account);
        System.out.println(num);
    }

    @Test
    public void deleteAccount() {
    }

    @Test
    public void findById() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        com.nateshao.jdbc.AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 创建Account对象，并向Account对象中添加数据
        Account id = accountDao.findAccountById(2);
        System.out.println(id);
    }

    @Test
    public void findByAllTest() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        com.nateshao.jdbc.AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        List<Account> allAccount = accountDao.findAllAccount();
        for (Account account : allAccount) {
            System.out.println(account);

        }

    }

}