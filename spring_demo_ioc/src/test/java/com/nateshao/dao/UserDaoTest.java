package com.nateshao.dao;

import com.nateshao.dao.impl.UserDaoMySqlImpl;
import com.nateshao.dao.impl.UserDaoOracleImpl;
import com.nateshao.pojo.Hello;
import com.nateshao.service.UserService;
import com.nateshao.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoTest {

    @Test
    public void getUser() {
    }

    @Test
    public void test() {
        UserService userService = new UserServiceImpl();
        userService.getUser();
    }

    @Test
    public void userDaoTest() {
        UserServiceImpl service = new UserServiceImpl();
        service.setUserDao(new UserDaoMySqlImpl());
        service.getUser();
        //那我们现在又想用Oracle去实现呢
        service.setUserDao(new UserDaoOracleImpl());
        service.getUser();
    }

    @Test
    public void testXml() {
        //解析beans.xml文件 , 生成管理相应的Bean对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //getBean : 参数即为spring配置文件中bean的id .
        Hello hello = (Hello) context.getBean("hello");
        hello.show();
    }
}