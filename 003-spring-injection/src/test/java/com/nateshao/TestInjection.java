package com.nateshao;

import com.nateshao.dao.InjectionDaoImpl;
import com.nateshao.service.InjectionServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:48
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TestInjection {
    @Test
    public void testSetter() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-inject.xml");
        InjectionServiceImpl service = applicationContext.getBean("injectionService01", InjectionServiceImpl.class);
        service.save("this is save data");
    }


}
