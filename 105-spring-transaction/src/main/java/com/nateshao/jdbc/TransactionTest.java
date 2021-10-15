package com.nateshao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/15 22:05
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TransactionTest {
    @Test
    public void xmlTest() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 调用实例中的转账方法
        accountDao.transfer("Jack", "Rose", 100.0);
        // 输出提示信息
        System.out.println("转账成功！");
    }

    @Test
    public void annotationTest() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext-annotation.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 调用实例中的转账方法
        accountDao.transfer("Jack", "Rose", 100.0);
        // 输出提示信息
        System.out.println("转账成功！");
    }

}
