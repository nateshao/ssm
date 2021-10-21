package com.nateshao.service;

import com.nateshao.vo.User;

/**
 * @date Created by 邵桐杰 on 2021/10/21 19:39
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface UserService {
    boolean login(User user);
    boolean register(User user);
}
