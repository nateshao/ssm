package com.nateshao.service;

import com.nateshao.vo.User;
import org.springframework.stereotype.Service;

/**
 * @date Created by 邵桐杰 on 2021/10/21 19:41
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean login(User user) {
        if (user.getName().equals("nateshao") && user.getPwd().equals("123456")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean register(User user) {
        if (user.getName().equals("nateshao") && user.getPwd().equals("123456")) {
            return true;
        } else {
            return false;
        }
    }
}
