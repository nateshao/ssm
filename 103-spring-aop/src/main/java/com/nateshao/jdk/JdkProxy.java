package com.nateshao.jdk;

import com.nateshao.aspect.MyAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:01
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: JDK代理类
 */
public class JdkProxy implements InvocationHandler {
    // 声明目标类接口
    private UserDao userDao;

    // 创建代理方法
    public Object createProxy(UserDao userDao) {
        this.userDao = userDao;
        // 1.类加载器
        ClassLoader classLoader = JdkProxy.class.getClassLoader();
        // 2.被代理对象实现的所有接口
        Class[] clazz = userDao.getClass().getInterfaces();
        // 3.使用代理类，进行增强，返回的是代理后的对象
        return Proxy.newProxyInstance(classLoader, clazz, this);
    }

    /**
     * 所有动态代理类的方法调用，都会交由invoke()方法去处理
     *
     * @param proxy  被代理后的对象
     * @param method 将要被执行的方法信息（反射）
     * @param args   执行方法时需要的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        // 声明切面
        MyAspect myAspect = new MyAspect();
        // 前增强
        myAspect.check_Permissions();
        // 在目标类上调用方法，并传入参数
        Object obj = method.invoke(userDao, args);
        // 后增强
        myAspect.log();
        return obj;
    }
}
