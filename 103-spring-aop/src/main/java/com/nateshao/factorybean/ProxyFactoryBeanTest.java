package com.nateshao.factorybean;

import com.nateshao.jdk.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:41
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 测试类
 */
public class ProxyFactoryBeanTest {
    public static void main(String args[]) {
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 从Spring容器获得内容
        UserDao userDao = (UserDao) applicationContext.getBean("userDaoProxy");
        // 执行方法
        userDao.addUser();
        userDao.deleteUser();
    }
}
