---
create by 千羽 2021-10-26
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/117-ssm-combine


<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

## 1. 整合环境搭建  

**如何进行SSM框架整合？**

> 由于Spring MVC是Spring框架中的一个模块，所以Spring MVC与Spring之间不存在整合的问题，只要引入相应JAR包就可以直接使用。因此SSM框架的整合就只涉及到了**Spring与MyBatis的整合，以及Spring MVC与MyBatis的整合。**

SSM框架整合图如下所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211022215941.png)

**如何确定SSM框架整合成功？**

在Spring与MyBatis框架的整合时，

1. 我们是通过Spring实例化Bean，
2. 然后调用实例对象中的查询方法来执行MyBatis映射文件中的SQL语句的，如果能够正确查询出数据库中的数据，那么我们就认为Spring与MyBatis框架整合成功。

同样，整合之后，如果我们可以通过前台页面来执行查询方法，并且查询出的数据能够在页面中正确显示，那么我们也可以认为三大框架整合成功。

**准备所需JAR包**

要实现SSM框架的整合，首先要准备这三个框架的JAR包，以及其他整合所需的JAR包。在讲解Spring与MyBatis框架整合时，已经介绍了Spring与MyBatis整合所需要的JAR包，这里只需要再加入Spring MVC的相关JAR包即可

1. 在IDEA中，创建一个名为117-ssm-combine的Spring Web项目，将整合所需的JAR包添加到项目的lib目录中，并发布到类路径下。

2. 在117-ssm-combine项目下，创建一个名为resources的源文件夹（resources Folder），在该文件夹中分别创建数据库常量配置文件db.properties、Spring配置文件applicationContext.xml，以及MyBatis的配置文件mybatis-config.xml。

**db.properties**

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis?useSSL=false
jdbc.username=root
jdbc.password=123456
jdbc.maxTotal=30
jdbc.maxIdle=10
jdbc.initialSize=5
```

**dispatcher-servlet.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 读取db.properties -->
    <context:property-placeholder location="classpath:db.properties"/>
    <!-- 配置数据源 -->
    <bean id="dataSource"
          class="org.apache.commons.dbcp2.BasicDataSource">
        <!--数据库驱动 -->
        <property name="driverClassName" value="${jdbc.driver}" />
        <!--连接数据库的url -->
        <property name="url" value="${jdbc.url}" />
        <!--连接数据库的用户名 -->
        <property name="username" value="${jdbc.username}" />
        <!--连接数据库的密码 -->
        <property name="password" value="${jdbc.password}" />
        <!--最大连接数 -->
        <property name="maxTotal" value="${jdbc.maxTotal}" />
        <!--最大空闲连接  -->
        <property name="maxIdle" value="${jdbc.maxIdle}" />
        <!--初始化连接数  -->
        <property name="initialSize" value="${jdbc.initialSize}" />
    </bean>
    <!-- 事务管理器，依赖于数据源 -->
    <bean id="transactionManager" class=
            "org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>
    <!-- 开启事务注解 -->
    <tx:annotation-driven transaction-manager="transactionManager"/>
    <!-- 配置MyBatis工厂SqlSessionFactory -->
    <bean id="sqlSessionFactory"
          class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--注入数据源 -->
        <property name="dataSource" ref="dataSource" />
        <!--指定核MyBatis心配置文件位置 -->
<!--        <property name="configLocation" value="classpath:WEB-INF/mybatis-config.xml" />-->
        <property name="configLocation" value="classpath:mybatis-config.xml" />
    </bean>
    <!-- 配置mapper扫描器 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.nateshao.dao"/>
    </bean>
    <!-- 扫描Service -->
    <context:component-scan base-package="com.nateshao.service" />

    <!-- 配置包扫描器，扫描@Controller注解的类 -->
    <context:component-scan base-package="com.nateshao.controller" />
    <!-- 加载注解驱动 -->
    <mvc:annotation-driven />
    <!-- 配置视图解析器 -->
    <bean class=
                  "org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

**log4j.properties**

```properties
# Global logging configuration
log4j.rootLogger=ERROR, stdout
# MyBatis logging configuration...
log4j.logger.com.nateshao=DEBUG
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

**mybatis-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
      "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
   <!-- 别名定义 -->
   <typeAliases>
      <package name="com.nateshao.po" />
   </typeAliases>
</configuration>
```

​     由于在Spring中已经配置了数据源信息以及mapper接口文件扫描器，所以在MyBatis的配置文件中只需要根据POJO类路径进行别名配置即可。

![](https://gitee.com/nateshao/images/raw/master/img/20211027000311.png)

## 2. 整合应用测试 

​      在src目录下，创建com.nateshao.po包，并在包中创建持久化类Customer：

```java
public class Customer {
    private Integer id;       // 主键id
    private String username; // 客户名称
    private String jobs;      // 职业
    private String phone;     // 电话
    
    // getter/setter 省略---
}
```

​      在src目录下，创建一个com.nateshao.dao包，并在包中创建接口文件CustomerDao以及对应的映射文件CustomerDao.xml，编辑后分别如下所示：

```java
public interface CustomerDao {
    /**
     * 根据id查询客户信息
     */
    public Customer findCustomerById(Integer id);

    public List<Customer> findCustomer(Customer customer);
}
```

**CustomerDao.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nateshao.dao.CustomerDao">
   <!--根据id查询客户信息 -->
   <select id="findCustomerById" parameterType="Integer"
                                     resultType="com.nateshao.po.Customer">
      select * from t_customer where id = #{id}
   </select>

   <select id="findCustomer" parameterType="com.nateshao.po.Customer"
         resultType="com.nateshao.po.Customer">
      select * from t_customer
   </select>
</mapper>
```

*小提示：在前面环境搭建时，已经在配置文件dispatcher-servlet.xml中使用包扫描的形式加入了扫描包com.nateshao.dao，所以在这里完成DAO层接口及映射文件开发后，就不必再进行映射文件的扫描配置了。*



在src目录下，创建com.nateshao.service包，然后在包中创建接口文件CustomerService，并在CustomerService中定义通过id查询客户的方法：

```java
public interface CustomerService {
    public Customer findCustomerById(Integer id);

    List<Customer> findCustomer(Customer customer);
}
```
并在包中创建CustomerService接口的实现类CustomerServiceImpl：
```java
package com.nateshao.service.impl;

import com.nateshao.dao.CustomerDao;
import com.nateshao.po.Customer;
import com.nateshao.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/26 21:54
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    //注解注入CustomerDao
    @Autowired
    private CustomerDao customerDao;
    //查询客户
    public Customer findCustomerById(Integer id) {
        return this.customerDao.findCustomerById(id);
    }

    @Override
    public List<Customer> findCustomer(Customer customer) {
        return this.customerDao.findCustomer(customer);
    }
}
```

在src目录下，创建一个com.nateshao.controller包，并在包中创建用于处理页面请求的控制类CustomerController：

```java
package com.nateshao.controller;

import com.nateshao.po.Customer;
import com.nateshao.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/26 21:51
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 根据id查询客户详情
     */
    @RequestMapping("/findCustomerById")
    public String findCustomerById(Integer id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        //返回客户信息展示页面
        return "customer";
    }
    /**
     * 根据id查询客户详情
     */
    @RequestMapping("/findCustomer")
    public String findCustomer(Customer customer, Model model) {
        List<Customer> customerList = customerService.findCustomer(customer);
        for (Customer customer1 : customerList) {
            System.out.println(customer1);
        }
        model.addAttribute("customer", customer);
        //返回客户信息展示页面
        return "customer";
    }

}
```

**customer.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户信息</title>
</head>
<body>
   <table border=1>
      <tr>
         <td>编号</td>
         <td>名称</td>
         <td>职业</td>
         <td>电话</td>
      </tr>
      <tr>
         <td>${customer.id}</td>
         <td>${customer.username}</td>
         <td>${customer.jobs}</td>
         <td>${customer.phone}</td>
      </tr>
   </table>
</body>
</html>
```

​       将项目发布到Tomcat服务器并启动，在浏览器中访问地址http://localhost:8080/117_ssm_combine_war_exploded/findCustomerById?id=1，显示效果如下所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211027001317.png)

从上图可以看出，通过浏览器已经成功查询出了t_customer表中id为1的客户信息，这也就说明SSM框架整合成功。
