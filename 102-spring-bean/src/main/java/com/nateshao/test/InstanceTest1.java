package com.nateshao.test;

import com.nateshao.instance.constructor.Bean1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/13 22:46
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class InstanceTest1 {
    public static void main(String[] args) {
        String xmlPath = "beans1.xml";
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(xmlPath);
        Bean1 bean = (Bean1) applicationContext.getBean("bean1");
        System.out.println(bean);
    }
}
