package com.nateshao.dao;

import com.nateshao.po.User;
import org.apache.ibatis.annotations.Param;

/**
 * @date Created by 邵桐杰 on 2021/11/11 22:47
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface UserDao {
    /**
     * 通过账号和密码查询用户
     */
    public User findUser(@Param("usercode") String usercode,
                         @Param("password") String password);
}
