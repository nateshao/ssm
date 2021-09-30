package com.nateshao;

import com.nateshao.aop.schemo.advice.biz.AspectBiz;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
//    public static void main( String[] args )
//
//    {
//        System.out.println( "Hello World!" );
//    }

    private AspectBiz aspectBiz() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop-schema-advice.xml");
        AspectBiz aspectBiz = applicationContext.getBean("aspectBiz", AspectBiz.class);
        return aspectBiz;
    }

    @Test
    public void testBefore() {
        aspectBiz().biz();
    }

    @Test
    public void testInit() {
        aspectBiz().init("aspectBiz", 33);
    }

}
