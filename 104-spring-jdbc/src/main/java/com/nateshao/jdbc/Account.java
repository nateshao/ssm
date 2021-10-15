package com.nateshao.jdbc;

/**
 * @date Created by 邵桐杰 on 2021/10/15 15:50
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class Account {
    private Integer id;       // 账户id
    private String username; // 用户名
    private Double balance;  // 账户余额
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public String toString() {
        return "Account [id=" + id + ", "
                + "username=" + username +
                ", balance=" + balance + "]";
    }
}
