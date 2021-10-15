---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/103-spring-aop
>
> Gitee：https://gitee.com/nateshao/ssm/tree/master/103-spring-aop

![](https://gitee.com/nateshao/images/raw/master/img/20211014104629.png)

![](https://gitee.com/nateshao/images/raw/master/img/20211014202044.png)

## 1. Spring AOP简介

**什么是AOP？**

> AOP的全称是Aspect-Oriented Programming，即面向切面编程（也称面向方面编程）。它是面向对象编程（OOP）的一种补充，目前已成为一种比较成熟的编程方式。

  在传统的业务处理代码中，通常都会进行**事务处理、日志记录**等操作。虽然使用OOP可以通过组合或者继承的方式来达到代码的重用，但如果要实现某个功能（如日志记录），同样的代码仍然会分散到各个方法中。这样，如果想要关闭某个功能，或者对其进行修改，就必须要修改所有的相关方法。这不但增加了开发人员的工作量，而且提高了代码的出错率。

​     为了解决这一问题，AOP思想随之产生。AOP采取**横向抽取机制**，将分散在各个方法中的**重复代码提取出来**，然后在程序编译或运行时，再将这些提取出来的代码应用到**需要执行的地方**。这种采用横向抽取机制的方式，采用传统的OOP思想显然是无法办到的，因为OOP只能实现父子关系的纵向的重用。虽然AOP是一种新的编程思想，但却**不是OOP的替代品**，它只是**OOP的延伸和补充**。

**类与切面的关系**

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014115136.png" style="zoom:80%;" />

​    AOP的使用，使开发人员在编写业务逻辑时可以专心于核心业务，而不用过多的关注于其他业务逻辑的实现，这不但提高了开发效率，而且增强了代码的可维护性。

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014175438.png" style="zoom:80%;" />

- Proxy（代理）：将通知应用到目标对象之后，被动态创建的对象。
- Weaving（织入）：将切面代码插入到目标对象上，从而生成代理对象的过程。

## 2. 动态代理

## jdk动态代理

>    JDK动态代理是通过java.lang.reflect.Proxy 类来实现的，我们可以调用Proxy类的newProxyInstance()方法来创建代理对象。对于使用业务接口的类，Spring默认会使用JDK动态代理来实现AOP。

**UserDao.java**

```java
public interface UserDao {
    public void addUser();
    public void deleteUser();
}
```

**UserDaoImpl.java**

```java
package com.nateshao.aop;

import org.springframework.stereotype.Repository;

/**
 * @date Created by 邵桐杰 on 2021/10/14 17:59
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao{
    @Override
    public void addUser() {
        System.out.println("添加用户");
    }

    @Override
    public void deleteUser() {
        System.out.println("删除用户");
    }
}
```

**JdkProxy.java**

```java
package com.nateshao.aop;

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
    public  Object createProxy(UserDao userDao) {
        this.userDao = userDao;
        // 1.类加载器
        ClassLoader classLoader = JdkProxy.class.getClassLoader();
        // 2.被代理对象实现的所有接口
        Class[] clazz = userDao.getClass().getInterfaces();
        // 3.使用代理类，进行增强，返回的是代理后的对象
        return  Proxy.newProxyInstance(classLoader,clazz,this);
    }

    /**
     * 所有动态代理类的方法调用，都会交由invoke()方法去处理
     * @param proxy 被代理后的对象
     * @param method 将要被执行的方法信息（反射）
     * @param args 执行方法时需要的参数
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
```

## CGLIB代理

  通过前面的学习可知，JDK的动态代理用起来非常简单，但它是有局限性的，使用动态代理的对象必须实现一个或多个接口。

**如果想代理没有实现接口的类，那么可以使用CGLIB代理。**

> ​    CGLIB（Code Generation Library）是一个高性能开源的代码生成包，它采用非常底层的字节码技术，对指定的目标类生成一个子类，并对子类进行增强。

**UserDao.java**

```java
public class UserDao {

    public void addUser(){
        System.out.println("添加用户");
    }

    public void deleteUser(){
        System.out.println("添加用户");
    }
}
```

**CglibProxy.java**

```java
package com.nateshao.cglib;

import com.nateshao.aspect.MyAspect;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:18
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
// 代理类
public class CglibProxy implements MethodInterceptor {
    // 代理方法
    public  Object createProxy(Object target) {
        // 创建一个动态类对象
        Enhancer enhancer = new Enhancer();
        // 确定需要增强的类，设置其父类
        enhancer.setSuperclass(target.getClass());
        // 添加回调函数
        enhancer.setCallback(this);
        // 返回创建的代理类
        return enhancer.create();
    }

    /**
     * @param proxy CGlib根据指定父类生成的代理对象
     * @param method 拦截的方法
     * @param args 拦截方法的参数数组
     * @param methodProxy 方法的代理对象，用于执行父类的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        // 创建切面类对象
        MyAspect myAspect = new MyAspect();
        // 前增强
        myAspect.check_Permissions();
        // 目标方法执行
        Object obj = methodProxy.invokeSuper(proxy, args);
        // 后增强
        myAspect.log();
        return obj;
    }
}
```

**CglibTest.java**

```java
package com.nateshao.cglib;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:25
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class CglibTest {
    public static void main(String[] args) {
        // 创建代理对象
        CglibProxy cglibProxy = new CglibProxy();
        // 创建目标对象
        UserDao userDao = new UserDao();
        // 获取增强后的目标对象
        UserDao userDao1 = (UserDao)cglibProxy.createProxy(userDao);
        // 执行方法
        userDao1.addUser();
        userDao1.deleteUser();
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211014183448.png)

## 3. 基于代理类的AOP实现

## Spring的通知类型

​    Spring按照通知在目标类方法的连接点位置，可以分为5种类型，具体如下：

1. org.springframework.aop.**MethodBeforeAdvice**（前置通知）

      在目标方法执行前实施增强，可以应用于权限管理等功能。

2. org.springframework.aop.**AfterReturningAdvice**（后置通知）

      在目标方法执行后实施增强，可以应用于关闭流、上传文件、删除临时文件等功能。

3. org.aopalliance.intercept.**MethodInterceptor**（环绕通知）

      在目标方法执行前后实施增强，可以应用于日志、事务管理等功能。

4. org.springframework.aop.**ThrowsAdvice**（异常抛出通知）

      在方法抛出异常后实施增强，可以应用于处理异常记录日志等功能。

5. org.springframework.aop.**IntroductionInterceptor**（引介通知）

      在目标类中添加一些新的方法和属性，可以应用于修改老版本程序。

## ProxyFactoryBean

> ​    ProxyFactoryBean是FactoryBean接口的实现类，FactoryBean负责实例化一个Bean，而ProxyFactoryBean负责为其他Bean创建代理实例。在Spring中，使用ProxyFactoryBean是创建AOP代理的基本方式。

  ProxyFactoryBean类中的常用可配置属性如下：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014183454.png" style="zoom:80%;" />

代码实现

**MyAspect.java**

```java
package com.nateshao.factorybean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:36
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:  切面类
 */
public class MyAspect implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        check_Permissions();
        // 执行目标方法
        Object obj = mi.proceed();
        log();
        return obj;
    }
    public void check_Permissions(){
        System.out.println("模拟检查权限...");
    }
    public void log(){
        System.out.println("模拟记录日志...");
    }
}
```

**applicationContext.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
    <!-- 1 目标类 -->
    <bean id="userDao" class="com.nateshao.jdk.UserDaoImpl" />
    <!-- 2 切面类 -->
    <bean id="myAspect" class="com.nateshao.factorybean.MyAspect" />
    <!-- 3 使用Spring代理工厂定义一个名称为userDaoProxy的代理对象 -->
    <bean id="userDaoProxy"
          class="org.springframework.aop.framework.ProxyFactoryBean">
        <!-- 3.1 指定代理实现的接口-->
        <property name="proxyInterfaces"
                  value="com.nateshao.jdk.UserDao" />
        <!-- 3.2 指定目标对象 -->
        <property name="target" ref="userDao" />
        <!-- 3.3 指定切面,织入环绕通知 -->
        <property name="interceptorNames" value="myAspect" />
        <!-- 3.4 指定代理方式，true：使用cglib，false(默认)：使用jdk动态代理 -->
        <property name="proxyTargetClass" value="true" />
    </bean>
</beans>
```

**ProxyFactoryBeanTest.java**

```java
package com.nateshao.factorybean;

import com.nateshao.jdk.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/14 18:41
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 测试类
 */
public class ProxyFactoryBeanTest {
    public static void main(String args[]) {
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 从Spring容器获得内容
        UserDao userDao = (UserDao) applicationContext.getBean("userDaoProxy");
        // 执行方法
        userDao.addUser();
        userDao.deleteUser();
    }
}
```

## 4. AspectJ开发

> 概述： AspectJ是一个基于Java语言的AOP框架，它提供了强大的AOP功能。Spring 2.0以后，Spring AOP引入了对AspectJ的支持，并允许直接使用AspectJ进行编程，而Spring自身的AOP API也尽量与AspectJ保持一致。新版本的Spring框架，也建议使用AspectJ来开发AOP。
>
>   使用AspectJ实现AOP有两种方式：一种是基于XML的声明式AspectJ，另一种是基于注解的声明式AspectJ。

## 基于XML的声明式AspectJ

> ​    基于XML的声明式AspectJ是指通过XML文件来定义切面、切入点及通知，所有的切面、切入点和通知都必须定义在< aop:config >元素内。

< aop:config >元素及其子元素如下：

![](https://gitee.com/nateshao/images/raw/master/img/20211014184839.png)

**小提示**：**图中灰色部分标注的元素即为常用的配置元素**



​    XML文件中常用元素的配置方式如下：

```xml
<bean id="myAspect" class="com.nateshao.aspectj.xml.MyAspect" />
<aop:config>
<aop:aspect  id="aspect"  ref="myAspect">
    <aop:pointcut expression="execution(* com.nateshao.jdk.*.*(..))“ id="myPointCut" />
                  <aop:before method="myBefore" pointcut-ref="myPointCut" />
                  <aop:after-returning method="myAfterReturning“ pointcut-ref="myPointCut"                      						returning="returnVal" />
    <aop:around method="myAround" pointcut-ref="myPointCut" />
    <aop:after-throwing method="myAfterThrowing“ pointcut-ref="myPointCut" throwing="e" />
                  <aop:after method="myAfter" pointcut-ref="myPointCut" />
    </aop:aspect>
</aop:config>
```

1. 配置切面

   > 在Spring的配置文件中，配置切面使用的是< aop:aspect >元素，该元素会将一个已定义好的Spring Bean转换成切面Bean，所以要在配置文件中先定义一个普通的Spring Bean。

​    **配置< aop:aspect >元素时，通常会指定id和ref两个属性。**

​		id：用于定义该切面的唯一标识名称。
​		ref：用于引用普通的Spring Bean

2. 配置切入点

> 当< aop:pointcut>元素作为< aop:config>元素的子元素定义时，表示该切入点是全局切入点，它可被多个切面所共享；当< aop:pointcut>元素作为< aop:aspect>元素的子元素时，表示该切入点只对当前切面有效。

​    **在定义< aop:pointcut>元素时，通常会指定id和expression两个属性。**

​		id：用于指定切入点的唯-标识名称。.
​		expressione：用于指定切入点关联的切入点表达式

**切入点表达式**

​    execution(* com.nateshao.jdk. * . * (..)) 是定义的切入点表达式，该切入点表达式的意思是匹配com.nateshao.jdk包中任意类的任意方法的执行。

- execution(* com.nateshao.jdk.*.*(..)) ：表达式的主体
- execution(* ：* 表示所有返回类型

- com.nateshao.jdk：需要拦截的包名字
- execution(* com.nateshao.jdk. * ：* 代表所有类
- execution(* com.nateshao.jdk. * . * ：方法名，使用* 代表所有方法
- execution(* com.nateshao.jdk.*.*(..)) ： . .  表示任意参数

3. 配置通知

> ​    使用< aop:aspect>的子元素可以配置5种常用通知，这5个子元素不支持使用子元素，但在使用时可以指定一些属性，其常用属性及其描述如下：

![](https://gitee.com/nateshao/images/raw/master/img/20211014192452.png)

**MyAspect.java**

```java
package com.nateshao.aspectj.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
/**
 * @date Created by 邵桐杰 on 2021/10/14 19:56
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 切面类，在此类中编写通知
 */
public class MyAspect {
    // 前置通知
    public void myBefore(JoinPoint joinPoint) {
        System.out.print("前置通知 ：模拟执行权限检查...,");
        System.out.print("目标类是："+joinPoint.getTarget() );
        System.out.println(",被织入增强处理的目标方法为："
                +joinPoint.getSignature().getName());
    }
    // 后置通知
    public void myAfterReturning(JoinPoint joinPoint) {
        System.out.print("后置通知：模拟记录日志...," );
        System.out.println("被织入增强处理的目标方法为："
                + joinPoint.getSignature().getName());
    }
    /**
     * 环绕通知
     * ProceedingJoinPoint 是JoinPoint子接口，表示可以执行目标方法
     * 1.必须是Object类型的返回值
     * 2.必须接收一个参数，类型为ProceedingJoinPoint
     * 3.必须throws Throwable
     */
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        // 开始
        System.out.println("环绕开始：执行目标方法之前，模拟开启事务...");
        // 执行当前目标方法
        Object obj = proceedingJoinPoint.proceed();
        // 结束
        System.out.println("环绕结束：执行目标方法之后，模拟关闭事务...");
        return obj;
    }
    // 异常通知
    public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println("异常通知：" + "出错了" + e.getMessage());
    }
    // 最终通知
    public void myAfter() {
        System.out.println("最终通知：模拟方法结束后的释放资源...");
    }
}
```

**config.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
    <!-- 1 目标类 -->
    <bean id="userDao" class="com.nateshao.jdk.UserDaoImpl" />
    <!-- 2 切面类 -->
    <bean id="myAspect" class="com.nateshao.factorybean.MyAspect" />
    <!-- 3 使用Spring代理工厂定义一个名称为userDaoProxy的代理对象 -->
    <bean id="userDaoProxy"
          class="org.springframework.aop.framework.ProxyFactoryBean">
        <!-- 3.1 指定代理实现的接口-->
        <property name="proxyInterfaces"
                  value="com.nateshao.jdk.UserDao" />
        <!-- 3.2 指定目标对象 -->
        <property name="target" ref="userDao" />
        <!-- 3.3 指定切面,织入环绕通知 -->
        <property name="interceptorNames" value="myAspect" />
        <!-- 3.4 指定代理方式，true：使用cglib，false(默认)：使用jdk动态代理 -->
        <property name="proxyTargetClass" value="true" />
    </bean>
</beans>
```

**TestXmlAspectj.java**

```java
package com.nateshao.aspectj.xml;

import com.nateshao.jdk.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/14 19:58
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TestXmlAspectj {
    public static void main(String args[]) {
        String xmlPath =
                "config.xml";
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(xmlPath);
        // 1 从spring容器获得内容
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        // 2 执行方法
        userDao.addUser();
    }
}
```

## 基于注解的声明式AspectJ(常用)

> AspectJ框架为AOP的实现提供了一套注解，用以取代Spring配置文件中为实现AOP功能所配置的臃肿代码。AspectJ的注解及其描述如下所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211014201352.png)

**MyAspect.java**

```java
package com.nateshao.aspectj.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @date Created by 邵桐杰 on 2021/10/14 20:06
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 切面类，在此类中编写通知
 */
@Aspect
@Component
public class MyAspect {
    // 定义切入点表达式
    @Pointcut("execution(* com.nateshao.jdk.*.*(..))")
    // 使用一个返回值为void、方法体为空的方法来命名切入点
    private void myPointCut(){}
    // 前置通知
    @Before("myPointCut()")
    public void myBefore(JoinPoint joinPoint) {
        System.out.print("前置通知 ：模拟执行权限检查...,");
        System.out.print("目标类是："+joinPoint.getTarget() );
        System.out.println(",被织入增强处理的目标方法为："
                +joinPoint.getSignature().getName());
    }
    // 后置通知
    @AfterReturning(value="myPointCut()")
    public void myAfterReturning(JoinPoint joinPoint) {
        System.out.print("后置通知：模拟记录日志...," );
        System.out.println("被织入增强处理的目标方法为："
                + joinPoint.getSignature().getName());
    }
    // 环绕通知
    @Around("myPointCut()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        // 开始
        System.out.println("环绕开始：执行目标方法之前，模拟开启事务...");
        // 执行当前目标方法
        Object obj = proceedingJoinPoint.proceed();
        // 结束
        System.out.println("环绕结束：执行目标方法之后，模拟关闭事务...");
        return obj;
    }
    // 异常通知
    @AfterThrowing(value="myPointCut()",throwing="e")
    public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println("异常通知：" + "出错了" + e.getMessage());
    }
    // 最终通知
    @After("myPointCut()")
    public void myAfter() {
        System.out.println("最终通知：模拟方法结束后的释放资源...");
    }
}
```

**annotation.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
  http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
  http://www.springframework.org/schema/aop
  http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
  http://www.springframework.org/schema/context
  http://www.springframework.org/schema/context/spring-context-4.3.xsd">
    <!-- 指定需要扫描的包，使注解生效 -->
    <context:component-scan base-package="com.nateshao" />
    <!-- 启动基于注解的声明式AspectJ支持 -->
    <aop:aspectj-autoproxy />
</beans>
```

**TestAnnotationAspectj.java**

```java
package com.nateshao.aspectj.annotation;

import com.nateshao.jdk.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @date Created by 邵桐杰 on 2021/10/14 20:09
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TestAnnotationAspectj {
    public static void main(String args[]) {
        String xmlPath = "annotation.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 1 从spring容器获得内容
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        // 2 执行方法
        userDao.addUser();
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211014201655.png)



































