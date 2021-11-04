package com.nateshao.annotation;

import org.springframework.stereotype.Repository;

/**
 * @date Created by 邵桐杰 on 2021/10/14 10:12
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("userdao...save...");
    }
}
