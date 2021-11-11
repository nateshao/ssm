---
create by 千羽 2021-10-12
---

[TOC]

![](https://gitee.com/nateshao/images/raw/master/img/20211022221033.png)

> GitHub：https://github.com/nateshao/ssm/tree/master/111-springmvc-file-upload

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

## 1.  什么是MyBatis 

**什么是MyBatis？**

> MyBatis（前身是iBatis）是一个支持普通SQL查询、存储过程以及高级映射的持久层框架。 

> MyBatis框架也被称之为ORM（Object/Relation Mapping，即对象关系映射）框架。所谓的ORM就是一种为了解决面向对象与关系型数据库中数据类型不匹配的技术，它通过描述Java对象与数据库表之间的映射关系，自动将Java应用程序中的对象持久化到关系型数据库的表中。

![ORM框架的工作原理](https://gitee.com/nateshao/images/raw/master/img/20211022221358.png)

**Hibernate与MyBatis有什么区别？**

**Hibernate**

1. Hibernate是一个全表映射的框架。
2. 通常开发者只需定义好持久化对象到数据库表的映射关系，就可以通过Hibernate提供的方法完成持久层操作。
3. 开发者并不需要熟练的掌握SQL语句的编写，Hibernate会根据制定的存储逻辑，自动的生成对应的SQL，并调用JDBC接口来执行，所以其开发效率会高于MyBatis。
4. Hibernate也存在一些缺点，例如它在多表关联时，对SQL查询的支持较差；更新数据时，需要发送所有字段；不支持存储过程；不能通过优化SQL来优化性能等。

**MyBatis**

1. MyBatis是一个半自动映射的框架。
2. “半自动”是相对于Hibernate全表映射而言的，MyBatis需要手动匹配提供POJO、SQL和映射关系，而Hibernate只需提供POJO和映射关系即可。
3. 与Hibernate相比，虽然使用MyBatis手动编写SQL要比使用Hibernate的工作量大，但MyBatis可以配置动态SQL并优化SQL，可以通过配置决定SQL的映射规则，它还支持存储过程等。对于一些复杂的和需要优化性能的项目来说，显然使用MyBatis更加合适。



## 2.  MyBatis的下载和使用 

下载地址：https://github.com/mybatis/mybatis-3/releases

![](https://gitee.com/nateshao/images/raw/master/img/20211022221743.png)

使用MyBatis框架非常简单，只需在应用程序中引入MyBatis的核心包和lib目录中的依赖包即可。

  **注意：如果底层采用的是MySQL数据库，那么还需要将MySQL数据库的驱动JAR包添加到应用程序的类路径中；如果采用其他类型的数据库，则同样需要将对应类型的数据库驱动包添加到应用程序的类路径中。**

## 3.  MyBatis的工作原理 

![](https://gitee.com/nateshao/images/raw/master/img/20211022221927.png)

**识记！！！**

## 4.  MyBatis入门程序

​    在实际开发中，查询操作通常都会涉及到单条数据的精确查询，以及多条数据的模糊查询。

1. 根据客户编号查询客户信息。
2. 根据客户名模糊查询客户信息。

**根据客户编号查询客户信息**

1. MySQL数据库中，创建一个名为`mybatis`的数据库，在此数据库中创建一个`t_customer`表，同时预先插入几条数据。

   ```sql
   /*
   Navicat MySQL Data Transfer
   
   Source Server         : localhost_3306
   Source Server Version : 50717
   Source Host           : localhost:3306
   Source Database       : mybatis
   
   Target Server Type    : MYSQL
   Target Server Version : 50717
   File Encoding         : 65001
   
   Date: 2021-10-22 22:24:17
   */
   
   SET FOREIGN_KEY_CHECKS=0;
   
   -- ----------------------------
   -- Table structure for `t_customer`
   -- ----------------------------
   DROP TABLE IF EXISTS `t_customer`;
   CREATE TABLE `t_customer` (
     `id` int(32) NOT NULL AUTO_INCREMENT,
     `username` varchar(50) DEFAULT NULL,
     `jobs` varchar(50) DEFAULT NULL,
     `phone` varchar(16) DEFAULT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
   
   -- ----------------------------
   -- Records of t_customer
   -- ----------------------------
   INSERT INTO `t_customer` VALUES ('1', 'aaa', 'dada', '11111111');
   INSERT INTO `t_customer` VALUES ('2', 'jack', 'teacher', '13521210112');
   INSERT INTO `t_customer` VALUES ('3', 'worker', 'worker', '13311111234');
   INSERT INTO `t_customer` VALUES ('4', 'zhangsan', 'maiyu', '10086');
   INSERT INTO `t_customer` VALUES ('5', 'zhangsan', 'manager', '13233334444');
   INSERT INTO `t_customer` VALUES ('6', 'zhangsan', 'manager', '13233334444');
   ```

   ![](https://gitee.com/nateshao/images/raw/master/img/20211022222537.png)

​      

2. 在IDEA中，创建一个名为`112-mybatis-hello`的maven项目，将MyBatis包、以及MySQL数据库的驱动包一同添加到项目的pom.xml下, 并发布到类路径中。

   项目结构如下：

   ![](https://gitee.com/nateshao/images/raw/master/img/20211022225701.png)

   pom.xml

   ```xml
   <dependencies>
           <!-- mybatis核心包 -->
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>3.5.1</version>
           </dependency>
           <!-- mysql驱动包 -->
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>5.1.47</version>
           </dependency>
           <!-- junit测试包 -->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.13.1</version>
               <scope>test</scope>
           </dependency>
           <dependency>
               <groupId>org.junit.jupiter</groupId>
               <artifactId>junit-jupiter-api</artifactId>
               <version>5.7.2</version>
           </dependency>
   
       </dependencies>
   ```

   **mybatis-config.xml**

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <!--1.配置环境 ，默认的环境id为mysql-->
       <environments default="mysql">
           <!--1.2.配置id为mysql的数据库环境 -->
           <environment id="mysql">
               <!-- 使用JDBC的事务管理 -->
               <transactionManager type="JDBC" />
               <!--数据库连接池 -->
               <dataSource type="POOLED">
   			  <property name="driver" value="com.mysql.jdbc.Driver" />
   			  <property name="url" 
                               value="jdbc:mysql://localhost:3306/mybatis?useSSL=false" />
   			  <property name="username" value="root" />
   			  <property name="password" value="123456" />
               </dataSource>
           </environment>
       </environments>
       <!--2.配置Mapper的位置 -->
       <mappers>
   		<mapper resource="mapper/CustomerMapper.xml" />
       </mappers>
   </configuration>
   ```

   **log4j.properties**

   ```sql
   # Global logging configuration
   log4j.rootLogger=ERROR, stdout
   # MyBatis logging configuration...
   log4j.logger.com.nateshao=DEBUG
   # Console output...
   log4j.appender.stdout=org.apache.log4j.ConsoleAppender
   log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
   log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
   ```

   **CustomerMapper.xml**

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!-- namespace表示命名空间 -->
   <mapper namespace="com.nateshao.mapper.CustomerMapper">
       <!--根据客户编号获取客户信息 -->
   	<select id="findCustomerById" parameterType="Integer"
   		resultType="com.nateshao.po.Customer">
   		select * from t_customer where id = #{id}
   	</select>
   	
   	<!--根据客户名模糊查询客户信息列表-->
   	<select id="findCustomerByName" parameterType="String"
   	    resultType="com.nateshao.po.Customer">
   	    <!-- select * from t_customer where username like '%${value}%' -->
   	    select * from t_customer where username like concat('%',#{value},'%')
   	</select>
   	
   	<!-- 添加客户信息 -->
   	<insert id="addCustomer" parameterType="com.nateshao.po.Customer">
   	    insert into t_customer(username,jobs,phone)
   	    values(#{username},#{jobs},#{phone})
   	</insert>
   	
   	<!-- 更新客户信息 -->
   	<update id="updateCustomer" parameterType="com.nateshao.po.Customer">
   	    update t_customer set
   	    username=#{username},jobs=#{jobs},phone=#{phone}
   	    where id=#{id}
   	</update>
   	
   	<!-- 删除客户信息 -->
   	<delete id="deleteCustomer" parameterType="Integer">
   	    delete from t_customer where id=#{id}
   	</delete>
   </mapper>
   ```

   **Customer.java**

   ```java
   package com.nateshao.po;
   
   /**
    * @date Created by 邵桐杰 on 2021/10/22 22:37
    * @微信公众号 程序员千羽
    * @个人网站 www.nateshao.cn
    * @博客 https://nateshao.gitee.io
    * @GitHub https://github.com/nateshao
    * @Gitee https://gitee.com/nateshao
    * Description:
    */
   public class Customer {
       private Integer id;
       private String username;
       private String jobs;
       private String phone;
   
       @Override
       public String toString() {
           return "Customer{" +
                   "id=" + id +
                   ", username='" + username + '\'' +
                   ", jobs='" + jobs + '\'' +
                   ", phone='" + phone + '\'' +
                   '}';
       }
   
       public Integer getId() {
           return id;
       }
   
       public void setId(Integer id) {
           this.id = id;
       }
   
       public String getUsername() {
           return username;
       }
   
       public void setUsername(String username) {
           this.username = username;
       }
   
       public String getJobs() {
           return jobs;
       }
   
       public void setJobs(String jobs) {
           this.jobs = jobs;
       }
   
       public String getPhone() {
           return phone;
       }
   
       public void setPhone(String phone) {
           this.phone = phone;
       }
   }
   ```

   **MyBatisTest.java**

   ```java
   package com.nateshao.test;
   
   import com.nateshao.po.Customer;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   import org.junit.jupiter.api.Test;
   import java.io.InputStream;
   import java.util.List;
   
   /**
    * @date Created by 邵桐杰 on 2021/10/22 22:41
    * @微信公众号 程序员千羽
    * @个人网站 www.nateshao.cn
    * @博客 https://nateshao.gitee.io
    * @GitHub https://github.com/nateshao
    * @Gitee https://gitee.com/nateshao
    * Description: Mybatis 测试 CRUD
    */
   
   public class MybatisTest {
   
       /**
        * 根据客户编号查询客户信息
        *
        * @throws Exception
        */
       @Test
       public void findCustomerByIdTest() throws Exception {
           // 1、读取配置文件
           String resource = "mybatis-config.xml";
           InputStream inputStream =
                   Resources.getResourceAsStream(resource);
           // 2、根据配置文件构建SqlSessionFactory
           SqlSessionFactory sqlSessionFactory =
                   new SqlSessionFactoryBuilder().build(inputStream);
           // 3、通过SqlSessionFactory创建SqlSession
           SqlSession sqlSession = sqlSessionFactory.openSession();
           // 4、SqlSession执行映射文件中定义的SQL，并返回映射结果
           Customer customer = sqlSession.selectOne("com.nateshao.mapper"
                   + ".CustomerMapper.findCustomerById", 1);
           // 打印输出结果
           System.out.println(customer.toString());
           // 5、关闭SqlSession
           sqlSession.close();
       }
   
       /**
        * 根据用户名称来模糊查询用户信息列表
        *
        * @throws Exception
        */
       @Test
       public void findCustomerByNameTest() throws Exception {
           // 1、读取配置文件
           String resource = "mybatis-config.xml";
           InputStream inputStream = Resources.getResourceAsStream(resource);
           // 2、根据配置文件构建SqlSessionFactory
           SqlSessionFactory sqlSessionFactory =
                   new SqlSessionFactoryBuilder().build(inputStream);
           // 3、通过SqlSessionFactory创建SqlSession
           SqlSession sqlSession = sqlSessionFactory.openSession();
           // 4、SqlSession执行映射文件中定义的SQL，并返回映射结果
           List<Customer> customers = sqlSession.selectList("com.nateshao.mapper"
                   + ".CustomerMapper.findCustomerByName", "j");
           for (Customer customer : customers) {
               //打印输出结果集
               System.out.println(customer);
           }
           // 5、关闭SqlSession
           sqlSession.close();
       }
   
       /**
        * 添加客户
        *
        * @throws Exception
        */
       @Test
       public void addCustomerTest() throws Exception {
           // 1、读取配置文件
           String resource = "mybatis-config.xml";
           InputStream inputStream = Resources.getResourceAsStream(resource);
           // 2、根据配置文件构建SqlSessionFactory
           SqlSessionFactory sqlSessionFactory =
                   new SqlSessionFactoryBuilder().build(inputStream);
           // 3、通过SqlSessionFactory创建SqlSession
           SqlSession sqlSession = sqlSessionFactory.openSession();
           // 4、SqlSession执行添加操作
           // 4.1创建Customer对象，并向对象中添加数据
           Customer customer = new Customer();
           customer.setUsername("rose");
           customer.setJobs("student");
           customer.setPhone("13333533092");
           // 4.2执行SqlSession的插入方法，返回的是SQL语句影响的行数
           int rows = sqlSession.insert("com.nateshao.mapper"
                   + ".CustomerMapper.addCustomer", customer);
           // 4.3通过返回结果判断插入操作是否执行成功
           if (rows > 0) {
               System.out.println("您成功插入了" + rows + "条数据！");
           } else {
               System.out.println("执行插入操作失败！！！");
           }
           // 4.4提交事务
           sqlSession.commit();
           // 5、关闭SqlSession
           sqlSession.close();
       }
   
       /**
        * 更新客户
        *
        * @throws Exception
        */
       @Test
       public void updateCustomerTest() throws Exception {
           // 1、读取配置文件
           String resource = "mybatis-config.xml";
           InputStream inputStream = Resources.getResourceAsStream(resource);
           // 2、根据配置文件构建SqlSessionFactory
           SqlSessionFactory sqlSessionFactory =
                   new SqlSessionFactoryBuilder().build(inputStream);
           // 3、通过SqlSessionFactory创建SqlSession
           SqlSession sqlSession = sqlSessionFactory.openSession();
           // 4、SqlSession执行更新操作
           // 4.1创建Customer对象，对对象中的数据进行模拟更新
           Customer customer = new Customer();
           customer.setId(4);
           customer.setUsername("rose");
           customer.setJobs("programmer");
           customer.setPhone("13311111111");
           // 4.2执行SqlSession的更新方法，返回的是SQL语句影响的行数
           int rows = sqlSession.update("com.nateshao.mapper"
                   + ".CustomerMapper.updateCustomer", customer);
           // 4.3通过返回结果判断更新操作是否执行成功
           if (rows > 0) {
               System.out.println("您成功修改了" + rows + "条数据！");
           } else {
               System.out.println("执行修改操作失败！！！");
           }
           // 4.4提交事务
           sqlSession.commit();
           // 5、关闭SqlSession
           sqlSession.close();
       }
   
       /**
        * 删除客户
        *
        * @throws Exception
        */
       @Test
       public void deleteCustomerTest() throws Exception {
           // 1、读取配置文件
           String resource = "mybatis-config.xml";
           InputStream inputStream = Resources.getResourceAsStream(resource);
           // 2、根据配置文件构建SqlSessionFactory
           SqlSessionFactory sqlSessionFactory =
                   new SqlSessionFactoryBuilder().build(inputStream);
           // 3、通过SqlSessionFactory创建SqlSession
           SqlSession sqlSession = sqlSessionFactory.openSession();
           // 4、SqlSession执行删除操作
           // 4.1执行SqlSession的删除方法，返回的是SQL语句影响的行数
           int rows = sqlSession.delete("com.nateshao.mapper"
                   + ".CustomerMapper.deleteCustomer", 4);
           // 4.2通过返回结果判断删除操作是否执行成功
           if (rows > 0) {
               System.out.println("您成功删除了" + rows + "条数据！");
           } else {
               System.out.println("执行删除操作失败！！！");
           }
           // 4.3提交事务
           sqlSession.commit();
           // 5、关闭SqlSession
           sqlSession.close();
       }
   }
   ```

   

## 总结

1. 读取配置文件
2. 根据配置文件构建SqlSessionFactory
3. 通过SqlSessionFactory创建SqlSession
4. 使用SqlSession对象操作数据库 
5. 关闭SqlSession

