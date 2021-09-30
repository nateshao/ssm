package com.nateshao.aop.schemo.advice.biz;

/**
 * @date Created by 邵桐杰 on 2021/9/30 15:35
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class AspectBiz {
    public void biz() {
        System.out.println("AspectBiz biz");
    }

    public void init(String bizName, int time) {
        System.out.println("AspectBiz init : " + bizName + " " + time);
    }
}
