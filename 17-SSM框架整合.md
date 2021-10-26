---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/111-springmvc-file-upload
>

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

要实现SSM框架的整合，首先要准备这三个框架的JAR包，以及其他整合所需的JAR包。在讲解Spring与MyBatis框架整合时，已经介绍了Spring与MyBatis整合所需要的JAR包，这里只需要再加入Spring MVC的相关JAR包即可，具体如下：









## 2. 整合应用测试 

