---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/106-springmvc-hello
>
> Gitee：https://gitee.com/nateshao/ssm/tree/master/106-springmvc-hello

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

<img src="https://gitee.com/nateshao/images/raw/master/img/20211016193752.png" style="zoom:80%;" />

## 1. DispatcherServlet

>    DispatcherServlet的全名是org.springframework.web.servlet.DispatcherServlet，它在程序中充当着前端控制器的角色。在使用时，只需将其配置在项目的web.xml文件中，其配置代码如下：

```xml
<servlet>
    <!-- 如果没有通过< init-param >元素配置，则应用程序会默认去WEB-INF目录下寻找以servletName-servlet.xml方式命名的配置文件，这里的servletName指下面的springmvc -->
    <servlet-name>springmvc</servlet-name>
    <servlet-class>
          org.springframework.web.servlet.DispatcherServlet
    </servlet-class>
<!-- 如果< init-param >元素存在并且通过其子元素配置了Spring MVC配置文件的路径，则应用程序在启动时会加载配置路径下的配置文件 -->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc-config.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

## 2. @Controller注解类型

> org.springframework.stereotype.Controller注解类型用于指示Spring类的实例是一个控制器，其注解形式为@Controller。该注解在使用时不需要再实现Controller接口，只需要将@Controller注解加入到控制器类上，然后通过Spring的扫描机制找到标注了该注解的控制器即可。

  @Controller注解在控制器类中的使用示例如下：

```java
@Controller
public class HelloController {

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
}
```

为了保证Spring能够找到控制器类，还需要在Spring MVC的配置文件中添加相应的扫描配置信息，一个完整的配置文件示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           <!-- 引入context信息 -->
            xmlns:context="http://www.springframework.org/schema/context"
            xsi:schemaLocation="http://www.springframework.org/schema/beans
                                             http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
                                             http://www.springframework.org/schema/context 
                                             http://www.springframework.org/schema/context/spring-context-4.3.xsd">
    <!-- 指定需要扫描的 -->
	<context:component-scan base-package="com.nateshao.controller" />
</beans> 
```

**注意**：使用注解方式时，程序的运行需要依赖Spring的AOP包，因此需要向lib目录中添加spring-aop-4.3.6.RELEASE.jar，否则程序运行时会报错！

## 3. @RequestMapping注解类型

> Spring通过@Controller注解找到相应的控制器类后，还需要知道控制器内部对每一个请求是如何处理的，这就需要使用@RequestMapping注解类型，它用于映射一个请求或一个方法。使用时，可以标注在一个方法或一个类上。

**1.** **标注在方法上：**作为请求处理方法在程序接收到对应的URL请求时被调用： 

```java
@Controller
public class FirstController{
	@RequestMapping(value="/firstController")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {
                           ...
		return mav;
```

**2.** **标注在类上：**该类中的所有方法都将映射为相对于类级别的请求，表示该控制器所处理的所有请求都被映射到value属性值所指定的路径下。

```java
@Controller
@RequestMapping(value="/hello")
public class FirstController{
	@RequestMapping(value="/firstController")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {
                           ...
		return mav;
	}
}
```

由于在类上添加了@RequestMapping注解，并且其value属性值为“/hello”，所以上述代码方法的请求路径将变为：http://localhost:8080/106-springmvc-hello/hello

@RequestMapping注解除了可以指定value属性外，还可以指定其他一些属性，如下表所示。

<img src="https://gitee.com/nateshao/images/raw/master/img/20211016195213.png" style="zoom:80%;" />

表中所有属性都是可选的，但其默认属性是value。当value是其唯一属性时，可以省略属性名。例如，下面两种标注的含义相同：

@RequestMapping(value="/firstController")

@RequestMapping("/firstController")

**组合注解**

> Spring框架的4.3版本中，引入了新的组合注解，来帮助简化常用的HTTP方法的映射，并更好的表达被注解方法的语义。

  Spring的4.3版本中的组合注解及其描述如下所示：

- @GetMapping：匹配GET方式的请求；
- @PostMapping：匹配POST方式的请求；
- @PutMapping：匹配PUT方式的请求；
- @DeleteMapping：匹配DELETE方式的请求；
- @PatchMapping：匹配PATCH方式的请求。

​    以@GetMapping为例，该组合注解是@RequestMapping(method = RequestMethod.GET)的缩写，它会将HTTP GET请求映射到特定的处理方法上。

Ø    在实际开发中，传统的@RequestMapping注解使用方式如下：

```java
@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
public String selectUserById(String id){
    ...
}
```

Ø    使用@GetMapping注解后的简化代码如下：

```java
@GetMapping(value="/user/{id}")
public String selectUserById(String id){
    ...
}
```

**请求处理方法的参数和返回类型**

​    在控制器类中，每一个请求处理方法都可以有多个不同类型的参数，以及一个多种类型的返回结果。在请求处理方法中，可以出现的参数类型如下：

```java
javax.servlet.ServletRequest / javax.servlet.http.HttpServletRequest
javax.servlet.ServletResponse / javax.servlet.http.HttpServletResponse
javax.servlet.http.HttpSession
org.springframework.web.context.request.WebRequest或
org.springframework.web.context.request.NativeWebRequest
java.util.Locale
java.util.TimeZone (Java 6+) / java.time.ZoneId (on Java 8)
java.io.InputStream / java.io.Reader
java.io.OutputStream / java.io.Writer
org.springframework.http.HttpMethod
java.security.Principal
@PathVariable、@MatrixVariable、@RequestParam、@RequestHeader、@RequestBody、@RequestPart、@SessionAttribute、@RequestAttribute注解
HttpEntity<?>
java.util.Map / org.springframework.ui.Model /org.springframework.ui.ModelMap
org.springframework.web.servlet.mvc.support.RedirectAttributes
org.springframework.validation.Errors /org.springframework.validation.BindingResult
org.springframework.web.bind.support.SessionStatus
org.springframework.web.util.UriComponentsBuilder
```

该类型不是一个Servlet API类型，而是一个包含了Map对象的Spring MVC类型。如果方法中添加了Model参数，则每次调用该请求处理方法时，Spring MVC都会创建Model对象，并将其作为参数传递给方法

**请求处理方法的返回类型**

Spring MVC所支持的常见方法返回类型如下：

![](https://gitee.com/nateshao/images/raw/master/img/20211016201053.png)

**由于ModelAndView类型未能实现数据与视图之间的解耦，所以在企业开发时，方法的返回类型通常都会使用String。**

既然String类型的返回值不能携带数据，那么在方法中是如何将数据带入视图页面的呢？

通过Model参数类型，即可添加需要在视图中显示的属性，其示例代码如下：

```java
@RequestMapping(value="/firstController")
public String handleRequest(HttpServletRequest request,
                                               HttpServletResponse response, Model model) {
        model.addAttribute("msg", "这是我的第一个Spring MVC程序");
        return "/WEB-INF/jsp/first.jsp";
}
```

​    String类型除了可以返回上述代码中的视图页面外，还可以进行重定向与请求转发，具体方式如下：

​    1. redirect 重定向。例如，在修改用户信息操作后，将请求重定向到用户查询方法的实现代码如下:

```java
@RequestMapping(value="/update")
public String update(HttpServletRequest request,HttpServletResponse response, Model model){
       ... 
       return "redirect:queryUser";
}
```

​    2. forward 请求转发。例如，用户执行修改操作时，转发到用户修改页面的实现代码如下:

```java
@RequestMapping(value="/toEdit")
public String update(HttpServletRequest request,HttpServletResponse response, Model model){
       ... 
       return "forward:editUser";
}
```

  

## 4. ViewResolver(视图解析器)

Spring MVC中的视图解析器负责解析视图。可以通过在配置文件中定义一个ViewResolver来配置视图解析器，其配置示例如下：

```java
<bean id="viewResolver"    class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp" />
</bean>
```

​    在上述代码中，定义了一个视图解析器，并设置了视图的前缀和后缀属性。这样设置后，方法中所定义的view路径将可以简化。例如，入门案例中的逻辑视图名只需设置为“first”，而不再需要设置为“/WEB-INF/jsp/first.jsp”，在访问时视图解析器会自动的增加前缀和后缀。

## 5. 基于注解的Spring MVC应用

代码附上！！

**web.xml**

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
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    
    <context:component-scan base-package="com.nateshao.controller"/>
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
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
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${msg}
</body>
</html>
```

**HelloController.java**

```java
package com.nateshao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @date Created by 邵桐杰 on 2021/10/16 21:37
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String handleRequest(HttpServletRequest request,
                                HttpServletResponse response, Model model) throws Exception {
        // 向模型对象中添加数据
        model.addAttribute("msg", "这是我的第一个Spring MVC程序");
        // 返回视图页面
        return "hello";
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211016220303.png)











