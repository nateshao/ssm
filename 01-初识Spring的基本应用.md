---
create by 千羽 2021-10-12
---

[TOC]

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

![](https://gitee.com/nateshao/images/raw/master/img/20211013183047.png)

> https://gitee.com/nateshao/ssm/tree/master/100-spring-hello

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

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013203341.png" style="zoom:50%;" />

docs文件夹中包含API文档和开发规范

libs文件夹中包含JAR包和源码

Schema文件夹中包含开发所需要的schema文件

**打开libs目录可以看到60个JAR文件，具体如下：**

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013203642.png" style="zoom:50%;" />

   在libs目录中有四个Spring的基础包，分别对应Spring核心容器的四个模块。

- **spring-core-4.3.6.RELEASE.jar**

  包含Spring框架的核心工具类，Spring其它组件都要用到这个包里的类。

- **spring-beans-4.3.6.RELEASE.jar**

  所有应用都要用到的JAR包，它包含访问配置文件、创建和管理Bean以及进行控制反转或者依赖注入操作相关的所有类。

- **spring-context-4.3.6.RELEASE.jar**

  提供了在基础IoC功能上的扩展服务，还提供了许多企业级服务的支持

- **spring-expression-4.3.6.RELEASE.jar**

  定义了Spring的表达式语言。

2.**第三方依赖包**

在使用Spring开发时，除了要使用自带的JAR包外，Spring的核心容器还需要依赖commons.logging的JAR包。

下载地址：http://commons.apache.org/proper/commons-logging/download_logging.cgi

## 2. Spring的核心容器

> 概述：Spring容器会负责控制程序之间的关系，而不是由程序代码直接控制。Spring为我们提供了两种核心容器，分别为BeanFactory和ApplicationContext，本节将对这两种核心容器进行简单介绍。

​    创建BeanFactory实例时，需要提供Spring所管理容器的详细配置信息，这些信息通常采用XML文件形式来管理，其加载配置信息的语法如下：

```java
BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("F:/applicationContext.xml"));
```

**小提示**：**这种加载方式在实际开发中并不多用，读者作为了解即可。**

​     ApplicationContext是BeanFactory的子接口，是另一种常用的Spring核心容器。它由org.springframework.context.ApplicationContext接口定义，不仅包含了BeanFactory的所有功能，还添加了对国际化、资源访问、事件传播等方面的支持。创建ApplicationContext接口实例，通常采用**两种方法**，具体如下：

1.**通过ClassPathXmlApplicationContext创建** 

```java
      ApplicationContext applicationContext =
                                     new ClassPathXmlApplicationContext(String configLocation);
```

​    ClassPathXmlApplicationContext会从类路径classPath中寻找指定的XML配置文件，找到并装载完成ApplicationContext的实例化工作。

2.**通过FileSystemXmlApplicationContext创建**   

```java
     ApplicationContext applicationContext =
                                 new FileSystemXmlApplicationContext(String configLocation);
```

​    FileSystemXmlApplicationContext会从指定的文件系统路径（绝对路径）中寻找指定的XML配置文件，找到并装载完成ApplicationContext的实例化工作。

​     在Java项目中，会通过ClassPathXmlApplicationContext类来实例化ApplicationContext容器。而在Web项目中，ApplicationContext容器的实例化工作会交由Web服务器来完成。

​     Web服务器实例化ApplicationContext容器时，通常会使用ContextLoaderListener来实现，此种方式只需要在web.xml中添加如下代码：

```xml
       <context-param>
               <param-name>contextConfigLocation</param-name> 
               <param-value>
                          classpath:spring/applicationContext.xml
               </param-value>
       </context-param> 
       <listener>
               <listener-class>
                         org.springframework.web.context.ContextLoaderListener
               </listener-class>
       </listener>
```

​     创建Spring容器后，就可以获取Spring容器中的Bean。Spring获取Bean的实例通常采用以下**两种**方法：

```java
Object getBean(String name);
根据容器中Bean的id或name来获取指定的Bean，获取之后需要进行强制类型转换。

<T> T getBean(Class<T> requiredType);
根据类的类型来获取Bean的实例。由于此方法为泛型方法，因此在获取Bean之后不需要进行强制类型转换。
```

## 3. Spring的入门程序

在IEDA中，创建一个名为100-spring-hello的Web项目，将Spring的4个基础包以及commons-logging的JAR包复制到lib目录中，并发布到类路径下。

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.nateshao</groupId>
  <artifactId>100-spring-hello</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>100-spring-hello</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.3.8</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>5.3.8</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>5.3.8</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-expression</artifactId>
      <version>5.3.8</version>
    </dependency>
    <dependency>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
      <version>1.2</version>
    </dependency>

  </dependencies>

  <build>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <!-- clean lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#clean_Lifecycle -->
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <!-- default lifecycle, jar packaging: see https://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_jar_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-jar-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
        <!-- site lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#site_Lifecycle -->
        <plugin>
          <artifactId>maven-site-plugin</artifactId>
          <version>3.7.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-project-info-reports-plugin</artifactId>
          <version>3.0.0</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```

在main/java目录下，创建一个com.nateshao.ioc包，并在包中创建接口UserDao，然后在接口中定义一个say()方法。

```java
package com.nateshao.ioc;

/**
 * @date Created by 邵桐杰 on 2021/10/13 20:58
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class UserDaoImpl implements UserDao {
    public void say() {
        System.out.println("userDao say hello World !");
    }
}
```

​        在com.nateshao.ioc包下，创建UserDao接口的实现类UserDaoImpl，该类需要实现接口中的say()方法，并在方法中编写一条输出语句。

```java
package com.nateshao.ioc;

/**
 * @date Created by 邵桐杰 on 2021/10/13 20:58
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class UserDaoImpl implements UserDao {
    public void say() {
        System.out.println("userDao say hello World !");
    }
}
```

​        在main/resources目录下，创建Spring的配置文件applicationContext.xml，并在配置文件中创建一个id为userDao的Bean。

applicationContext.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="userDao" class="com.nateshao.ioc.UserDaoImpl"/>

</beans>
```

​        在com.nateshao.ioc包下，创建测试类TestIoC，并在类中编写main()方法。在main()方法中，需要初始化Spring容器，并加载配置文件，然后通过Spring容器获取userDao实例（即Java对象），最后调用实例中的say()方法。

TestIoC.java

```java
package com.nateshao.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/13 21:03
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TestIoC {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.say();
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211013210758.png)

## 4. 依赖注入

> 概念：DI的全称是Dependency Injection，中文称之为依赖注入。它与控制反转（IoC）的含义相同，只不过这两个称呼是从两个角度描述的同一个概念。

**IOC**：在使用Spring框架之后，对象的实例不再由调用者来创建，而是由Spring容器来创建，Spring容器会负责控制程序之间的关系，而不是由调用者的程序代码直接控制。这样，控制权由应用代码转移到了Spring容器，控制权发生了反转，这就是控制反转。

**DI**：从Spring容器的角度来看，Spring容器负责将被依赖对象赋值给调用者的成员变量，这相当于为调用者注入了它依赖的实例，这就是Spring的依赖注入。

## 使用setter方法实现依赖注入

1. 在com.nateshao.ioc包中，创建接口UserService，在接口中编写一个say()方法。

UserDao.java

```java
package com.nateshao.ioc;

/**
 * @date Created by 邵桐杰 on 2021/10/13 20:57
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface UserDao {
    public void say();
}
```

​       2. 在com.nateshao.ioc包中，创建UserService接口的实现类UserServiceImpl，在类中声明userDao属性，并添加属性的setter方法。

UserServiceImpl.java

```java
package com.nateshao.ioc;

/**
 * @date Created by 邵桐杰 on 2021/10/13 21:10
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void say() {
        this.userDao.say();
        System.out.println("userService say hello World !");
    }

}
```

3. 在配置文件applicationContext.xml中，创建一个id为userService的Bean，该Bean用于实例化UserServiceImpl类的信息，并将userDao的实例注入到userService中。

   applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="userDao" class="com.nateshao.ioc.UserDaoImpl"/>

    <!--添加一个id为userService的实例 -->
    <bean id="userService" class="com.nateshao.ioc.UserServiceImpl">
        <!-- 将id为userDao的Bean实例注入到userService实例中 -->
        <property name="userDao" ref="userDao" />
    </bean>
</beans>
```

4. 在com.nateshao.ioc包中，创建测试类TestDI，来对程序进行测试。

```java
package com.nateshao.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/13 21:16
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TestDI {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService =
                (UserService) applicationContext.getBean("userService");
        userService.say();
    }
}
```

运行结果

![](https://gitee.com/nateshao/images/raw/master/img/20211013212610.png)

所以这里，主要介绍了Spring框架入门的一些基础知识，包括Spring的概念、优点、体系结构、核心容器、依赖注入等,同时通过一个入门程序讲解了Spring的基本使用。

   通过这里的学习，可以对Spring框架及其体系结构有一个初步的了解，能够初步的掌握Spring框架的使用，并能够理Spring框架中IoC和DI的思想，掌握属性setter方法注入的实现。

**小问题**

请简述Spring框架的优点。

请简述什么是Spring的IoC和DI。

**✎** **下一篇**

Bean的实例化有哪几种方式？

Bean的作用域是什么？

Bean有几种装配方式？











