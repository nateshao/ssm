package com.nateshao.databind.vo;

import com.nateshao.databind.po.User;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/17 15:22
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 用户包装类
 */
public class UserVO {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
