package com.nateshao.annotation;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @date Created by 邵桐杰 on 2021/10/14 10:14
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    @Resource(name="userDao")
    private UserDao userDao;

    @Override
    public void save() {
        //调用userDao中的save方法
        this.userDao.save();
        System.out.println("userservice....save...");
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
