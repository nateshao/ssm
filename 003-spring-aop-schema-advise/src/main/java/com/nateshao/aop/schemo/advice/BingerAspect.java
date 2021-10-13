package com.nateshao.aop.schemo.advice;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @date Created by 邵桐杰 on 2021/9/30 15:37
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class BingerAspect {
    public void before() {
        System.out.println("BingerAspect before");
    }

    public void afterReturning() {
        System.out.println("BingerAspect afterReturning");
    }

    public void afterThrowing() {
        System.out.println("BingerAspect afterThrowing");
    }

    public void after() {
        System.out.println("BingerAspect after");
    }

    public Object around(ProceedingJoinPoint pjp) {

        Object obj = null;
        try {
            System.out.println(" BingerAspect around 1");
            pjp.proceed();
            System.out.println(" BingerAspect around 2");

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }


    public Object aroundInit(ProceedingJoinPoint pjp, String bizName, int times) {
        System.out.println(bizName + "   " + times);

        Object obj = null;
        try {
            System.out.println(" BingerAspect aroundInit 1");
            pjp.proceed();
            System.out.println(" BingerAspect aroundInit 2");

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}

