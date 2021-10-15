package com.nateshao.jdbc;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/15 21:39
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
    // 更新
    public int updateAccount(Account account);
    // 删除
    public int deleteAccount(int id);

    // 通过id查询
    public Account findAccountById(int id);
    // 查询所有账户
    public List<Account> findAllAccount();

    // 转账
    public void transfer(String outUser,String inUser,Double money);
}
