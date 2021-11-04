package com.nateshao.cglib;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:25
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class CglibTest {
    public static void main(String[] args) {
        // 创建代理对象
        CglibProxy cglibProxy = new CglibProxy();
        // 创建目标对象
        UserDao userDao = new UserDao();
        // 获取增强后的目标对象
        UserDao userDao1 = (UserDao) cglibProxy.createProxy(userDao);
        // 执行方法
        userDao1.addUser();
        userDao1.deleteUser();
    }
}
