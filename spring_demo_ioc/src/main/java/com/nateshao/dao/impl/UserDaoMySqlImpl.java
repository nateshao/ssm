package com.nateshao.dao.impl;

import com.nateshao.dao.UserDao;

public class UserDaoMySqlImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("MySql获取用户数据");
    }
}
