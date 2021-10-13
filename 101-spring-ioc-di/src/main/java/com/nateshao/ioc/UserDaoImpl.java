package com.nateshao.ioc;

/**
 * @date Created by 邵桐杰 on 2021/10/13 20:58
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class UserDaoImpl implements UserDao {
    public void say() {
        System.out.println("userDao say hello World !");
    }

    public void setUserService(UserDaoImpl userService) {
    }
}

