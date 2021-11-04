package com.nateshao.aspect;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:02
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 切面类：可以存在多个通知Advice（即增强的方法）
 */
public class MyAspect {
    public void check_Permissions() {
        System.out.println("模拟检查权限...");
    }

    public void log() {
        System.out.println("模拟记录日志...");
    }
}
