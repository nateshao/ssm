package com.nateshao.mybatis.model;

import lombok.Data;

import java.util.Date;

/**
 * @date Created by 邵桐杰 on 2021/11/11 15:43
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Data
public class Student {
    private String studName;
    private int stuNo;
    private String sex;
    private String nation;
    private String political;
    private String school;
    private String major;
    private Date birthday;
    private Date entranceTime;
}
