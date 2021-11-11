---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/105-spring-transaction
>

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

![](https://gitee.com/nateshao/images/raw/master/img/20211015203518.png)



## 1.Spring事务管理概述

**什么是Spring的事务管理？**

>    在实际开发中，操作数据库时都会涉及到事务管理问题，为此Spring提供了专门用于事务处理的API。Spring的事务管理简化了传统的事务管理流程，并且在一定程度上减少了开发者的工作量。

**Spring事务管理的三个核心接口。**

在该JAR包的org.springframework.transaction包中，有3个接口文件PlatformTransactionManager、TransactionDefinition和TransactionStatus

![](https://gitee.com/nateshao/images/raw/master/img/20211015210630.png)

## 事务管理的核心接口

**Platform TransactionManager**

>  PlatformTransactionManager接口是Spring提供的平台事务管理器，主要用于管理事务。该接口中提供了三个事务操作的方法，具体如下：

- `TransactionStatus getTransaction(TransactionDefinition definition)`：用于获取事务状态信息
- `void commit(TransactionStatus status)`：用于提交事务
- `void rollback(TransactionStatus status)`：用于回滚事务

​     PlatformTransactionManager接口只是代表事务管理的接口，并不知道底层是如何管理事务的，具体如何管理事务则由它的实现类来完成。该接口常见的几个实现类如下：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015211014.png" style="zoom:80%;" />

**小提示**：当底层采用不同的持久层技术时，系统只需使用不同的PlatformTransactionManager实现类即可。

**TransactionDefinition**

> TransactionDefinition接口是事务定义（描述）的对象，该对象中定义了事务规则，并提供了获取事务相关信息的方法，具体如下：

- `String getName( );  `			获取事务对象名称
- `int getIsolationLevel( ); `   获取事务的隔离级别
- `int getPropagationBehavior( );`  获取事务的传播行为
- `int getTimeout( );  `获取事务的超时时间
- `boolean isReadOnly( ); `      获取事务是否只读

​    上述方法中，事务的传播行为是指在同一个方法中，不同操作前后所使用的事务。传播行为有很多种，具体如下表所示：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015211659.png" style="zoom:80%;" />

> 在事务管理过程中，传播行为可以控制是否需要创建事务以及如何创建事务，通常情况下，数据的查询不会影响原数据的改变，所以不需要进行事务管理，而对于数据的插入、更新和删除操作，必须进行事务管理。如果没有指定事务的传播行为，Spring默认传播行为是REQUIRED。

**TransactionStatus**

> TransactionStatus接口是事务的状态，它描述了某一时间点上事务的状态信息。该接口中包含6个方法，具体如下：

- `void flush();`              刷新事务
- `boolean hasSavepoint();`     获取是否存在保存点
- `boolean isCompleted();`     获取事务是否完成
- `boolean isNewTransaction();` 获取是否为新事务
- `boolean isRollbackOnly();`   获取事务是否回滚
- `void setRollbackOnly();`     设置事务回滚

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015212259.png" style="zoom:80%;" />

​     **声明式事务管理最大的优点在于开发者无需通过编程的方式来管理事务，**只需在配置文件中进行相关的事务规则声明，就可以将事务应用到业务逻辑中。这使得开发人员可以更加专注于核心业务逻辑代码的编写，在一定程度上减少了工作量，提高了开发效率，所以在实际开发中，通常都推荐使用声明式事务管理。

## 2. 声明式事务管理

**如何实现Spring的声明式事务管理？**

> Spring的声明式事务管理可以通过两种方式来实现，**一种是基于XML的方式，另一种是基于Annotation的方式。**接下来的两个小节中，将对这两种声明式事务管理方式进行详细讲解。

![](https://gitee.com/nateshao/images/raw/master/img/20211015212519.png)







## 基于XML方式的声明式事务

>    配置< tx:advice >元素的重点是配置< tx:method >子元素，上图中使用灰色标注的几个属性是< tx:method >元素中的常用属性。其属性描述具体如下：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015212628.png" style="zoom:80%;" />

**Account.java**

```java
@Data
public class Account {
    private Integer id;       // 账户id
    private String username; // 用户名
    private Double balance;  // 账户余额
}
```

**AccountDao.java**

```java
public interface AccountDao {
    .........
    // 转账
    public void transfer(String outUser,String inUser,Double money);
}
```

**AccountDaoImpl.java**

```java
@Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT, readOnly = false)
    @Override
    public void transfer(String outUser, String inUser, Double money) {
        // 收款时，收款用户的余额=现有余额+所汇金额
        this.jdbcTemplate.update("update account set balance = balance +? "
                + "where username = ?",money, inUser);
        // 模拟系统运行时的突发性问题
//        int i = 1/0;
        // 汇款时，汇款用户的余额=现有余额-所汇金额
        this.jdbcTemplate.update("update account set balance = balance-? "
                + "where username = ?",money, outUser);
    }
```

**TransactionTest.java**

```java
package com.nateshao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/15 22:05
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TransactionTest {
     @Test
    public void xmlTest() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        // 调用实例中的转账方法
        accountDao.transfer("千羽", "千寻", 100.0);
        // 输出提示信息
        System.out.println("转账成功！");
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211015221615.png)

## 基于Annotation方式的声明式事务



1. 在Spring容器中注册事务注解驱动；

   ```xml
   <tx:annotation-driven transaction-manager="transactionManager"/>
   ```

2. 在需要事务管理的类或方法上使用`@Transactional`注解。

   如果将注解添加在`Bean`类上，则表示事务的设置对整个`Bean`类的所有方法都起作用；如果将注解添加在`Bean`类中的某个方法上，则表示事务的设置只对该方法有效。

使用`@Transactional`注解时，可以通过参数配置事务详情：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015213138.png" style="zoom:80%;" />

**applicationContext-annotation.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx" 
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
    http://www.springframework.org/schema/tx 
    http://www.springframework.org/schema/tx/spring-tx-4.3.xsd
    http://www.springframework.org/schema/context 
    http://www.springframework.org/schema/context/spring-context-4.3.xsd
    http://www.springframework.org/schema/aop 
    http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">
    <!-- 1.配置数据源 -->
    <bean id="dataSource" 
    class="org.springframework.jdbc.datasource.DriverManagerDataSource">
      <!--数据库驱动 -->
      <property name="driverClassName" value="com.mysql.jdbc.Driver" />
      <!--连接数据库的url -->
      <property name="url" value="jdbc:mysql://localhost/spring?useSSL=false" />
      <!--连接数据库的用户名 -->
      <property name="username" value="root" />
      <!--连接数据库的密码 -->
      <property name="password" value="123456" />
   </bean>
   <!-- 2.配置JDBC模板 -->
   <bean id="jdbcTemplate" 
            class="org.springframework.jdbc.core.JdbcTemplate">
      <!-- 默认必须使用数据源 -->
      <property name="dataSource" ref="dataSource" />
   </bean>
   <!--3.定义id为accountDao的Bean -->
   <bean id="accountDao" class="com.nateshao.jdbc.AccountDaoImpl">
      <!-- 将jdbcTemplate注入到AccountDao实例中 -->
      <property name="jdbcTemplate" ref="jdbcTemplate" />
   </bean>
   <!-- 4.事务管理器，依赖于数据源 -->
   <bean id="transactionManager" class=
     "org.springframework.jdbc.datasource.DataSourceTransactionManager">
      <property name="dataSource" ref="dataSource" />
   </bean>    
    <!-- 5.注册事务管理器驱动 -->
   <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

**TransactionTest.java**

```java
package com.nateshao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/15 22:05
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TransactionTest {
    @Test
    public void annotationTest() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext-annotation.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 调用实例中的转账方法
        accountDao.transfer("千寻111", "千羽111", 100.0);
        // 输出提示信息
        System.out.println("转账成功！");
    }

}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211015222001.png)

## 总结

本章主要对Spring中的事务管理进行了详细讲解。

1. 首先讲解了Spring事务管理所涉及的3个核心接口，
2. 然后对Spring中事务管理的两种方式进行了介绍，
3. 最后通过案例分别对基于XML方式和基于Annotation方式的声明式事务处理的使用进行了详细讲解。

通过本章的学习，我相信大家可以对Spring的事务管理知识有一定的了解，并能够掌握Spring声明式事务管理的使用。



