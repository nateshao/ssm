package com.nateshao.test;

import com.nateshao.annotation.UserController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/14 10:20
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class AnnotationAssembleTest {
    public static void main(String[] args) {
        // 定义配置文件路径
        String xmlPath = "beans6.xml";
        // 加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 获取UserController实例
        UserController userController = (UserController) applicationContext.getBean("userController");
        // 调用UserController中的save()方法
        userController.save();
    }
}
