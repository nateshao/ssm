package com.nateshao.ssm.repository;

import com.nateshao.ssm.entity.User;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/9/30 10:59
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface UserRepository {
    public List<User> findAll();
}
