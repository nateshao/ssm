---
create by 千羽 2021-10-12
---

[TOC]

![](https://gitee.com/nateshao/images/raw/master/img/20211013183047.png)

## 1. Spring概述

**什么是Spring？**

> Spring是分层的JavaSE/EE full-stack 轻量级开源框架，以IoC（Inverse of Control 控制反转）和AOP（Aspect Oriented Programming 面向切面编程）为内核，使用基本的JavaBean来完成以前只可能由EJB完成的工作，取代了EJB的臃肿、低效的开发模式。

在实际开发中，通常服务器端在采用三层体系架构，分别为表示层(Web)、业务逻辑层(Service)、持久层(Dao)， Spring对每一层都提供了技术支持。

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013183756.png" style="zoom:80%;" />

**Spring框架的优点**

> Spring具有简单、可测试和松耦合等特点。Spring不仅可以用于服务器端开发，也可以应用于任何Java应用的开发中。

**Spring框架的7大优点**

1. 非侵入式设计
2. 方便解耦、简化开发
3. 支持AOP
4. 支持声明式事务处理
5. 方便程序测试
6. 方便集成各种优秀框架
7. 降低Java EE API的使用难度

**Spring的体系结构**

   Spring框架采用的是分层架构，它一系列的功能要素被分成20个模块。

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013184249.png" style="zoom: 67%;" />

### **1.Core Container(核心容器)**

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013185032.png" style="zoom:67%;" />

**Beans**：提供了BeanFactory，Spring将管理对象称为Bean。
**Core** ：提供了Spring框架的基本组成部分，包括10oC和DI功能。
**Context**：建立在Core和Beans模块的基础之上，它是访问定义和配置的任何对象的媒介。
**SpEL**：Spring3.0后新增的模块，是运行时查询和操作对象图的强大的表达式语言。

### **2.Data Access/Integration(数据访问/集成)**

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013185448.png" style="zoom:50%;" />

**JDBC**：提供了一个JDBC的抽象层，大幅度的减少了在开发过程中对数据库操作的编码。
**ORM**：对流行的对象关系映射API，包括JPA、JDO和Hibernate提供了集成层支持。
**0XM**：提供了一个支持对象/ XML映射的抽象层实现，如JAXB、Castor、XML Beans、JiBX和XStream。
**JMS**：指Java消息传递服务，包含使用和产生信息的特性，自4.1版本后支持与Spring-message模块的集成。
**Transactions**：支持对实现特殊接口以及所有POJO类的编程和声.明式的事务管理。

### 3.Web

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013185946.png" style="zoom:50%;" />****
**WebSocket**：Spring4.0以后新增的模块，它提供了WebSocket 和SockJS的实现，以及对STOMP的支持。
**Servlet**：也称Spring-webmvc模块， 包含Spring模型一视图一控制器(MVC)和REST Web Services实现的Web程序。
**Web**：提供了基本的Web开发集成特性，如:多文件上传、使用Servlet监听器来初始化IoC容器以及Web应用上下文。
**Portlet**：提供了在portlet环境中使用MVC实现，类似Servlet模块的功能。

### 4. 其他模块

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013190224.png" style="zoom:50%;" />

**AOP** ：提供了面向切面编程实现，允许定义方法拦截器和切入点,将代码按照功能进行分离，以降低耦合性。

**Aspects**：提供了与AspectJ的集成功能，AspectJ是一个功能强大且成熟的面向切面编程(AOP)框架。

**Instrumentation**：提供了类工具的支持和类加载器的实现，可以在特定的应用服务器中使用。

**Messaging**：Sring4.0以后新增的模块，它提供了对消息传递体系结构和协议的支持。

**Test**：提供了对单元测试和集成测试的支持。

## **Spring的下载及目录结构**

​     Spring开发所需的jar包分为两个部分:Spring框架包和第三方依赖包。

下载地址：http://repo.spring.io/simple/libs-release-local/org/springframework/spring/4.3.6.RELEASE/













## 2. Spring的核心容器



## 3. Spring的入门程序



## 4. 依赖注入







