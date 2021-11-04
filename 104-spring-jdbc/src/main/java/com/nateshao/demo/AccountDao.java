package com.nateshao.demo;

import com.nateshao.jdbc.Account;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/15 19:24
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface AccountDao {
    // 添加
    public int addAccount(Account account);

    // 修改
    public int updateAccount(int id);

    // 删除
    public int deleteAccount(int id);

    // 查询
    public List<Account> findById();

    // 查询
    public List<Account> findByAll();


}
