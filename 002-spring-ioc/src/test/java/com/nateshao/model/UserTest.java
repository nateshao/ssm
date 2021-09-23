    package com.nateshao.model;

    import org.junit.Test;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    import static org.junit.Assert.*;

    /**
     * @date Created by 邵桐杰 on 2021/9/23 19:23
     * @微信公众号 千羽的编程时光
     * @个人网站 www.nateshao.cn
     * @博客 https://nateshao.gitee.io
     * @GitHub https://github.com/nateshao
     * @Gitee https://gitee.com/nateshao
     * Description:
     */
    public class UserTest {
        @Test
        public void testUser() {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            User user = (User) applicationContext.getBean("nateshao");
            System.out.println(user.toString());
            System.out.println("------------------------------------------------");
            user.setAge(19);
            user.setName("千羽的编程时光");
            System.out.println(user.toString());
        }

    }