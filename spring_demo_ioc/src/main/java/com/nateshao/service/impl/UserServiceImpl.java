package com.nateshao.service.impl;

import com.nateshao.dao.UserDao;
import com.nateshao.dao.impl.UserDaoImpl;
import com.nateshao.dao.impl.UserDaoMySqlImpl;
import com.nateshao.service.UserService;

public class UserServiceImpl implements UserService {

    //    private UserDao userDao = new UserDaoImpl();
//    @Override
//    public void getUser() {
//        userDao.getUser();
//    }
//    private UserDao userDao = new UserDaoMySqlImpl();
//
//    @Override
//    public void getUser() {
//        userDao.getUser();
//    }
    private UserDao userDao;

    // 利用set实现
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
