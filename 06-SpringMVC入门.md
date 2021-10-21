---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/106-springmvc-hello
>
> Gitee：https://gitee.com/nateshao/ssm/tree/master/106-springmvc-hello

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

![](https://gitee.com/nateshao/images/raw/master/img/20211015230012.png)



## 1. Spring MVC概述

**什么是Spring MVC？**

> ​    Spring MVC是Spring提供的一个实现了Web MVC设计模式的轻量级Web框架。它与Struts2框架一样，都属于MVC框架，但其使用和性能等方面比Struts2更加优异。

**Spring MVC具有以下特点：**

1. 是Spring框架的一部分，可以方便的利用Spring所提供的其他功能。
2. 灵活性强，易于与其他框架集成。
3. 提供了一个前端控制器DispatcherServlet，使开发人员无需额外开发控制器对象。
4. 可自动绑定用户输入，并能正确的转换数据类型。
5. 内置了常见的校验器，可以校验用户输入。如果校验不能通过，那么就会重定向到输入表单。
6. 支持国际化。可以根据用户区域显示多国语言。
7. 支持多种视图技术。它支持JSP、Velocity和FreeMarker等视图技术。
8. 使用基于XML的配置文件，在编辑后，不需要重新编译应用程序。

## 2. 第一个Spring MVC应用

1. 在IDEA中，创建一个名称为106-springmvc-hello的Web项目，具体参考：https://github.com/nateshao/ssm/tree/master/106-springmvc-hello

2. 在**web.xml**中，配置Spring MVC的前端控制器DispatcherServlet。

   ![](https://gitee.com/nateshao/images/raw/master/img/20211016163418.png)
   
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0">
       <context-param>
           <param-name>contextConfigLocation</param-name>
           <param-value>/WEB-INF/applicationContext.xml</param-value>
       </context-param>
       <listener>
           <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
       </listener>
       <servlet>
           <servlet-name>dispatcher</servlet-name>
           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
           <load-on-startup>1</load-on-startup>
       </servlet>
       <servlet-mapping>
           <servlet-name>dispatcher</servlet-name>
   <!--        <url-pattern>*.form</url-pattern>-->
           <url-pattern>/</url-pattern>
       </servlet-mapping>
   </web-app>
   ```
   

**dispatcher-servlet.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <context:component-scan base-package="com.nateshao"/>
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

**hello.jsp**

```jsp
<%--
  Created by IntelliJ IDEA.
  User: 邵桐杰
  Date: 2021/10/16
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
	<h2>hello springmvc</h2>
</body>
</html>
```

```xml
<%-- 用EL表达式获取后台处理器封装的信息 --%>
  $END$
```

控制台输出：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211016163317.png" style="zoom:80%;" />

浏览器访问：http://localhost:8080/106_springmvc_hello_war_exploded/hello

![](https://gitee.com/nateshao/images/raw/master/img/20211016163338.png)

## 3. Spring MVC的工作流程（重点!!!）

**Spring MVC是如何工作的呢？**

> 通过入门案例的学习，相信读者对Spring MVC的使用已经有了一个初步的了解。在实际开发中，我们的实际工作主要集中在控制器和视图页面上，但Spring MVC内部完成了很多工作，这些程序在项目中具体是怎么执行的呢？接下来，将通过一张图来展示Spring MVC程序的执行情况。

![](https://gitee.com/nateshao/images/raw/master/img/20211016164818.png)

1. 用户通过浏览器向服务器发送请求，请求会被Spring MVC的前端控制器DispatcherServlet所拦截;
2. DispatcherServlet拦截到请求后，会调用HandlerMapping处理器映射器;
3. 处理器映射器根据请求URL找到具体的处理器，生成处理器对象及处理器拦截器(如果有则生成)一并返回给DispatcherServlet;
4. DispatcherServlet会通过返回信息选择合适的HandlerAdapter (处理器适配器);
5. HandlerAdapter会调用并执行Handler (处理器)，这里的处理器指的就是程序中编写的Controller类，也被称之为后端控制器;
6. Controller执行完成后，会返回一个ModelAndView对象，该对象中会包含视图名或包含模型和视图名;
7. HandlerAdapter将ModelAndView对象返回给DispatcherServlet;
8. DispatcherServlet会根据ModelAndView对象选择一个 合适的ViewReslover(视图解析器) ;
9. ViewReslover解析后，会向DispatcherServlet中返回具体的View (视图) ;
10. DispatcherServlet对View进行渲染( 即将模型数据填充至视图中) ;















