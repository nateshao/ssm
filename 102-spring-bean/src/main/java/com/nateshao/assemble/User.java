package com.nateshao.assemble;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/14 9:24
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class User {
    private String username;
    private int password;
    private List<String> list;

    /**
     * 1.使用构造注入
     * 1.1提供带所有参数的有参构造方法。
     */
    public User(String username, Integer password, List<String> list) {
        super();
        this.username = username;
        this.password = password;
        this.list = list;
    }

    /**
     * 2.使用设值注入
     * 2.1提供默认空参构造方法 ;
     * 2.2为所有属性提供setter方法。
     */
    public User() {
        super();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password +
                ", list=" + list + "]";
    }
}
