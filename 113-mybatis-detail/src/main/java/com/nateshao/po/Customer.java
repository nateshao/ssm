package com.nateshao.po;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:17
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
/**
 * 客户持久化类
 */
public class Customer {
    private Integer id;       // 主键id
    private String username; // 客户名称
    private String jobs;      // 职业
    private String phone;     // 电话
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
    public String getJobs() {
        return jobs;
    }
    public void setJobs(String jobs) {
        this.jobs = jobs;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "Customer [id=" + id + ", username=" + username +
                ", jobs=" + jobs + ", phone=" + phone + "]";
    }
}