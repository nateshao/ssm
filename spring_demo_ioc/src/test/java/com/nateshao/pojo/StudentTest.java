package com.nateshao.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @date Created by 邵桐杰 on 2021/9/12 22:45
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class StudentTest {
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student.getName());

    }

    @Test
    public void setName() {
    }

    @Test
    public void setAddress() {
    }

    @Test
    public void setBooks() {
    }

    @Test
    public void setHobbys() {
    }

    @Test
    public void setCard() {
    }

    @Test
    public void setGames() {
    }

    @Test
    public void setWife() {
    }

    @Test
    public void setInfo() {
    }

    @Test
    public void show() {
    }
}