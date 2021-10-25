package com.nateshao.po;

import lombok.Data;

/**
 * @date Created by 邵桐杰 on 2021/10/25 11:31
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 个人持久化类
 */
@Data
public class Person {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private IdCard card;  //个人关联的证件
}
