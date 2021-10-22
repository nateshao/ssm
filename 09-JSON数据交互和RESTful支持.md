---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/109-springmvc-json-restful
>
> Gitee：https://gitee.com/nateshao/ssm/tree/master/109-springmvc-json-restful

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)





## 1. JSON数据交互 

**什么是JSON？**

> ​    JSON（JavaScript Object Notation，JS对象标记）是一种轻量级的数据交换格式。它是基于JavaScript的一个子集，使用了C、C++、C#、Java、JavaScript、Perl、Python等其他语言的约定，采用完全独立于编程语言的文本格式来存储和表示数据。 

**JSON有什么特点？**

- JSON与XML非常相似，都是用来存储数据的，并且都是基于纯文本的数据格式。与XML相比，JSON解析速度更快，占用空间更小，且易于阅读和编写，同时也易于机器解析和生成。

   JSON有如下两种数据结构：

1. 对象结构

2. 数组结构

   

## JSON对象结构

>  在对象结构以“{”开始，以“}”结束。中间部分由0个或多个以英文“，”分隔的name:value对构成（注意name和value之间以英文“：”分隔），其存储形式如下图所示。

![](https://gitee.com/nateshao/images/raw/master/img/20211019223948.png)

  对象结构的语法结构代码如下：

>例如：一个address对象包含城市、街道、邮编等信息，使用JSON的表示形式如下：{"city":"Beijing","street":"Xisanqi","postcode":100096}

## JSON数组结构

>  数组结构以“[”开始，以“]”结束。中间部分由0个或多个以英文“，”分隔的值的列表组成，其存储形式如下图所示。

![](https://gitee.com/nateshao/images/raw/master/img/20211019224013.png)

  对象结构的语法结构代码如下：

```java
[
            value1,
            value2,
             ...
]
```

  例如，一个数组包含了String、Number、Boolean、null类型数据，使用JSON的表示形式如下：

```java
["abc",12345,false,null]
```

对象、数组数据结构也可以分别组合构成更为复杂的数据结构。例如：一个person对象包含name、hobby和address对象，其代码表现形式如下：

```java
{
    "name": "zhangsan"
    "hobby":["篮球","羽毛球","游泳"]
    "address":{
        "city":"Beijing"
        "street":"Xisanqi"
        "postcode":100096
    }
 }
```

>  注意：如果使用JSON存储单个数据（如“abc”），一定要使用数组的形式，不要使用Object形式，因为Object形式必须是“名称：值”的形式。

## JSON数据转换

>    Spring提供了一个HttpMessageConverter<T>接口来实现浏览器与控制器类（Controller）之间的数据交互。该接口主要用于将请求信息中的数据转换为一个类型为T的对象，并将类型为T的对象绑定到请求方法的参数中，或者将对象转换为响应信息传递给浏览器显示。

​    HttpMessageConverter< T >接口有很多实现类，这些实现类可以对不同类型的数据进行信息转换。其中MappingJackson2HttpMessageConverter是Spring MVC默认处理JSON格式请求响应的实现类。该实现类利用Jackson开源包读写JSON数据，将Java对象转换为JSON对象和XML文档，同时也可以将JSON对象和XML文档转换为Java对象。

   要使用MappingJackson2HttpMessageConverter对数据进行转换，就需要使用Jackson的开源包，开发时所需的开源包及其描述如下所示：

- jackson-annoations-2.8.8.jar：JSON转换注解包；
- jackson-core-2.8.8.jar：JSON转换核心包；
- jackson-databind-2.8.8.jar：JSON转换的数据绑定包。

下载地址：http://mvnrepository.com/artifact/com.fasterxml.jackson.core

### 使用的注解

>   在使用注解式开发时，需要用到2个重要的JSON格式转换注解，分别为@RequestBody和@ResponseBody，关于这两个注解的说明如下表所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211019224437.png)

JSON数据转换代码演示：





**多学一招：1.使用< bean >标签方式的JSON转换器配置**

![](https://gitee.com/nateshao/images/raw/master/img/20211019224645.png)

小提示：使用< bean >标签配置注解方式的处理器映射器和处理器适配器必须配对使用。

**多学一招：2. 配置静态资源的访问方式**

除了使用< mvc:resources >元素可以实现对静态资源的访问外，还有另外2种静态资源访问的配置方式，分别如下：

1. 在springmvc-config.xml文件中，使用< mvc:default-servlet-handler >标签

   ```java
   <mvc:default-servlet-handler />
   ```

2. 激活Tomcat默认的Servlet来处理静态文件访问

   ```java
   <!--激活tomcat的静态资源拦截，需要哪些静态文件，再往下追加-->
   <servlet-mapping>
   	<servlet-name>default</servlet-name>
   	<url-pattern>*.js</url-pattern>
   </servlet-mapping>
   <servlet-mapping>
   	<servlet-name>default</servlet-name>
   	<url-pattern>*.css</url-pattern>
   </servlet-mapping>
   ...
   ```

   以上两种方式本质上是一样的，都是使用Web服务器默认的Servlet来处理静态资源文件的访问。其中Servelt名称也是由使用的服务器来确定的，不同的服务器需要使用不同的名称，常用服务器及其Servlet名称如下：

- Tomcat, Jetty, JBoss, and GlassFish默认Servlet的名称—— "default"；

- Google App Engine默认Servlet的名称——"_ah_default"；

- Resin默认Servlet的名称——"resin-file"；
- WebLogic默认Servlet的名称—— "FileServlet"；
- WebSphere默认Servlet的名称——"SimpleFileServlet"。

## 2. RESTful支持

**什么是RESTful？**

>    RESTful也称之为REST，是英文“Representational State Transfer”的简称。可以将他理解为一种软件架构风格或设计风格，而不是一个标准。

**简单来说，RESTful风格就是把请求参数变成请求路径的一种风格。**

![](https://gitee.com/nateshao/images/raw/master/img/20211019225212.png)

代码演示：将采用RESTful风格的请求实现对用户信息的查询，同时返回JSON格式的数据。

## 小结

主要对Spring MVC中的JSON数据交互和RESTful风格的请求进行了详细的讲解。

首先简单介绍了JSON的概念、作用和结构，

然后通过案例讲解了Spring MVC中如何实现JSON数据的交互。

接着讲解了什么是RESTful，最后通过用户信息查询案例来演示RESTful的实际使用。

通过本章的学习，我们可以掌握Spring MVC中的JSON数据交互和对RESTful风格支持，这对今后实际工作开发有极大的帮助。









