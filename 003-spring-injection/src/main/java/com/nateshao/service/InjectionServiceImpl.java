package com.nateshao.service;

import com.nateshao.dao.InjectionDao;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:39
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class InjectionServiceImpl implements InjectionService {
    private InjectionDao injectionDao;

    public void setInjectionDao(InjectionDao injectionDao) {
        this.injectionDao = injectionDao;
    }

    @Override
    public void save(String arg) {
        System.out.println("Service accepts a parameter");
        arg = arg + ":" + this.hashCode();
        injectionDao.save(arg);
    }
}
