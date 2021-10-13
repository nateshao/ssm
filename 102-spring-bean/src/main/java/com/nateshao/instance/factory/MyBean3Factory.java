package com.nateshao.instance.factory;

/**
 * @date Created by 邵桐杰 on 2021/10/13 23:00
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class MyBean3Factory {
    public MyBean3Factory() {
        System.out.println("bean3工厂实例化中");
    }
    //创建Bean3实例的方法
    public Bean3 createBean(){
        return new Bean3();
    }
}
