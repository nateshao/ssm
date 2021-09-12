package com.nateshao.dao.impl;

import com.nateshao.dao.UserDao;

public class UserDaoImpl implements UserDao {

    @Override
    public void getUser() {
        System.out.println("获取用户数据");
    }
}