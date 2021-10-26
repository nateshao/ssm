---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/116-mybatis-spring

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

## 1. 整合环境搭建 

>    要实现MyBatis与Spring的整合，很明显需要这两个框架的JAR包，但是只使用这两个框架中所提供的JAR包是不够的，还需要其他的JAR包来配合使用，整合时所需准备的JAR包具体如下。

### Spring框架所需的JAR包

```xml
<dependencies>
   <!-- AOP开发使用的JAR -->
    <dependency>
        <groupId>aopalliance</groupId>
        <artifactId>aopalliance</artifactId>
        <version>1.0</version>
    </dependency>

    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.6</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aop</artifactId>
        <version>5.3.8</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
        <version>5.3.7</version>
    </dependency>
    <!-- 4个核心模块JAR -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
        <version>5.3.8</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
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
    <!-- JDBC和事务的JAR -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.3.8</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-tx</artifactId>
        <version>5.3.8</version>
    </dependency>
 	<dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
    </dependency>
</dependencies>
```

### MyBatis框架所需的JAR包

```xml
<!--    2.MyBatis框架所需的JAR包   核心  -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.7</version>
</dependency>

<!--    2.MyBatis框架所需的JAR包   -->
<dependency>
    <groupId>ant</groupId>
    <artifactId>ant</artifactId>
    <version>1.6.5</version>
</dependency>
<dependency>
    <groupId>ant</groupId>
    <artifactId>ant-launcher</artifactId>
    <version>1.6.5</version>
</dependency>
<dependency>
    <groupId>net.minidev</groupId>
    <artifactId>asm</artifactId>
    <version>1.0.2</version>
</dependency>
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.1</version>
</dependency>
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
</dependency>
<dependency>
    <groupId>org.javassist</groupId>
    <artifactId>javassist</artifactId>
    <version>3.27.0-GA</version>
</dependency>

<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.14.1</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.13.3</version>
</dependency>
<dependency>
    <groupId>ognl</groupId>
    <artifactId>ognl</artifactId>
    <version>3.1.26</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.31</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.30</version>
</dependency>
```

### MyBatis与Spring整合的中间JAR

```xml
<!-- MyBatis与Spring整合的中间JAR   -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>
```

### 数据库驱动JAR（MySQL）

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
```

### 数据源所需JAR（DBCP）
```xml
<dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>1.4</version>
</dependency>
<dependency>
    <groupId>commons-pool</groupId>
    <artifactId>commons-pool</artifactId>
    <version>1.6</version>
</dependency>
```

### 编写配置文件

1. 创建项目`116-mybatis-spring`，引入maven包

   ![](https://gitee.com/nateshao/images/raw/master/img/20211026152616.png)

2. 编写db.properties 

3. 编写Spring配置文件applicationContext.xml

4. 编写MyBatis配置文件mybatis-config.xml

5. 引入log4j.properties

   **db.properties **

   ```properties
   jdbc.driver=com.mysql.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/mybatis?useSSL=false
   jdbc.username=root
   jdbc.password=123456
   jdbc.maxTotal=30
   jdbc.maxIdle=10
   jdbc.initialSize=5
   ```

   **applicationContext.xml**

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
       <!--读取db.properties -->
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
       <!--开启事务注解 -->
      <tx:annotation-driven transaction-manager="transactionManager"/>
       <!--配置MyBatis工厂 -->
       <bean id="sqlSessionFactory" 
               class="org.mybatis.spring.SqlSessionFactoryBean">
            <!--注入数据源 -->
            <property name="dataSource" ref="dataSource" />
            <!--指定核心配置文件位置 -->
             <property name="configLocation" value="classpath:mybatis-config.xml"/>
      </bean>
      
      <!--实例化Dao -->
      <bean id="customerDao" class="com.nateshao.dao.impl.CustomerDaoImpl">
      <!-- 注入SqlSessionFactory对象实例-->
           <property name="sqlSessionFactory" ref="sqlSessionFactory" />
      </bean>
      <!-- Mapper代理开发（基于MapperFactoryBean） -->
      <!-- <bean id="customerMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
          <property name="mapperInterface" value="com.nateshao.mapper.CustomerMapper" />
          <property name="sqlSessionFactory" ref="sqlSessionFactory" />  
      </bean> -->
      <!-- Mapper代理开发（基于MapperScannerConfigurer） -->
      <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
           <property name="basePackage" value="com.nateshao.mapper" />
      </bean>
      
      <!-- 开启扫描 --> 
      <context:component-scan base-package="com.nateshao.service" />
      
   </beans>
   ```

   **mybatis-config.xml**

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <!--配置别名 -->
       <typeAliases>
           <package name="com.nateshao.po" />
       </typeAliases>
       <!--配置Mapper的位置 -->
   	<mappers> 
          <mapper resource="mapper/CustomerMapper.xml" />
          <!-- Mapper接口开发方式 -->
   	   <mapper resource="mapper/CustomerMapperInterface.xml" />
          
   	</mappers>
   </configuration>
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

这样一来，环境加载文件就全了

## 2. 传统DAO方式的开发整合 

> 采用传统DAO开发方式进行MyBatis与Spring框架的整合时，可以使用mybatis-spring包中所提供的SqlSessionTemplate类或SqlSessionDaoSupport类来实现。

- `SqlSessionTemplate`：是mybatis-spring的核心类，它负责管理MyBatis的SqlSession，调用MyBatis的SQL方法。当调用SQL方法时，SqlSessionTemplate将会保证使用的SqlSession和当前Spring的事务是相关的。它还管理SqlSession的生命周期，包含必要的关闭、提交和回滚操作。

- `SqlSessionDaoSupport`：是一个抽象支持类，它继承了DaoSupport类，主要是作为DAO的基类来使用。可以通过SqlSessionDaoSupport类的getSqlSession()方法来获取所需的SqlSession。

**代码实现**

**CustomerDao.java**

```java
public interface CustomerDao {
    // 通过id查询客户
    public Customer findCustomerById(Integer id);
}
```

**CustomerDaoImpl.java**

```java
public class CustomerDaoImpl extends SqlSessionDaoSupport implements CustomerDao {
    // 通过id查询客户
    public Customer findCustomerById(Integer id) {
        return this.getSqlSession().selectOne("com.nateshao.po"
                + ".CustomerMapper.findCustomerById", id);
    }
}
```

**CustomerMapperInterface.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nateshao.po.CustomerMapper">
   <!--根据id查询客户信息 -->
   <select id="findCustomerById" parameterType="Integer"
           resultType="customer">
      select * from t_customer where id = #{id}
   </select>
</mapper>
```

**测试类 DaoTest.java**

```java
package com.nateshao.test;

import com.nateshao.dao.CustomerDao;
import com.nateshao.mapper.CustomerMapper;
import com.nateshao.po.Customer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/26 15:12
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class DaoTest {
    @Test
    public void findCustomerByIdDaoTest() {
        ApplicationContext act =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 根据容器中Bean的id来获取指定的Bean
        CustomerDao customerDao =
                (CustomerDao) act.getBean("customerDao");
//      CustomerDao customerDao = act.getBean(CustomerDao.class);
        Customer customer = customerDao.findCustomerById(1);
        System.out.println(customer);
    }

    @Test
    public void findCustomerByIdMapperTest() {
        ApplicationContext act =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomerMapper customerMapper = act.getBean(CustomerMapper.class);
        Customer customer = customerMapper.findCustomerByIdOne(1);
        System.out.println(customer);
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211026162154.png)

## 3. Mapper接口方式的开发整合 

>    在MyBatis+Spring的项目中，虽然使用传统的DAO开发方式可以实现所需功能，但是采用这种方式在实现类中会出现大量的重复代码，在方法中也需要指定映射文件中执行语句的id，并且不能保证编写时id的正确性（运行时才能知道）。
>
>    为此，我们可以使用MyBatis提供的另外一种编程方式，即使用Mapper接口编程。

### 基于MapperFactoryBean的整合

> MapperFactoryBean是MyBatis-Spring团队提供的一个用于根据Mapper接口生成Mapper对象的类，该类在Spring配置文件中使用时可以配置以下参数：

- `mapperInterface`：用于指定接口；
- `SqlSessionFactory`：用于指定SqlSessionFactory；
- `SqlSessionTemplate`：用于指定SqlSessionTemplate。如果与SqlSessionFactory同时设定，则只会启用SqlSessionTemplate。

**注意！！！**

> 虽然使用Mapper接口编程的方式很简单，但是在具体使用时还是需要遵循一些规范。

1. Mapper接口的名称和对应的Mapper.xml映射文件的名称必须一致。
2. Mapper.xml文件中的namespace与Mapper接口的类路径相同。
3. Mapper接口中的方法名和Mapper.xml中定义的每个执行语句的id相同。
4. Mapper接口中方法的输入参数类型要和Mapper.xml中定义的每个sql的parameterType的类型相同。
5. Mapper接口方法的输出参数类型要和Mapper.xml中定义的每个sql的resultType的类型相同。

>    在实际的项目中，DAO层会包含很多接口，如果每一个接口都在Spring配置文件中配置，不但会增加工作量，还会使得Spring配置文件非常臃肿。为此，可以采用自动扫描的形式来配置MyBatis中的映射器——采用MapperScannerConfigurer类。

**MapperScannerConfigurer类在Spring配置文件中可以配置以下属性：**

- `basePackage`：指定映射接口文件所在的包路径，当需要扫描多个包时可以使用分号或逗号作为分隔符。指定包路径后，会扫描该包及其子包中的所有文件。

- `annotationClass`：指定了要扫描的注解名称，只有被注解标识的类才会被配置为映射器。

- `sqlSessionFactoryBeanName`：指定在Spring中定义的SqlSessionFactory的Bean名称。

- `sqlSessionTemplateBeanName`：指定在Spring中定义的SqlSessionTemplate的Bean名称。如果定义此属性，则sqlSessionFactoryBeanName将不起作用。

- `markerInterface`：指定创建映射器的接口。

**MapperScannerConfigurer的使用非常简单，只需要在Spring的配置文件中编写如下代码：**

```xml
<!-- Mapper代理开发（基于MapperScannerConfigurer） -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     <property name="basePackage" value="com.nateshao.mapper" />
</bean>
```

​    通常情况下，MapperScannerConfigurer在使用时只需通过basePackage属性指定需要扫描的包即可，Spring会自动的通过包中的接口来生成映射器。这使得开发人员可以在编写很少代码的情况下，完成对映射器的配置，从而提高开发效率。

## 4. 测试事务

**如何进行事务测试？**

​    在项目中，Service层既是处理业务的地方，又是管理数据库事务的地方。要对事务进行测试，首先需要创建Service层，并在Service层编写添加客户操作的代码；然后在添加操作的代码后，有意的添加一段异常代码（如int i = 1/0;）来模拟现实中的意外情况；最后编写测试方法，调用业务层的添加方法。这样，程序在执行到错误代码时就会出现异常。

```java
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    //注解注入CustomerMapper
    @Autowired
    private CustomerMapper customerMapper;
    //添加客户
    public void addCustomer(Customer customer) {
        this.customerMapper.addCustomer(customer);
        int i=1/0; //模拟添加操作后系统突然出现的异常问题
    }
}
```

  

![](https://gitee.com/nateshao/images/raw/master/img/20211026164600.png)



![](https://gitee.com/nateshao/images/raw/master/img/20211026164607.png)

  在没有事务管理的情况下，即使出现了异常，数据也会被存储到数据表中；如果添加了事务管理，并且事务管理的配置正确，那么在执行上述操作时，所添加的数据将不能够插入到数据表中。

## 总结

这篇文章首先对MyBatis与Spring框架整合的环境搭建进行了讲解，然后讲解了使用传统DAO方式的开发整合，以及基于Mapper接口方式的开发整合。

跟着配套代码的学习，我相信你们都能够熟练的掌握MyBatis与Spring框架的几种整合方式，这将为后面项目的学习打下坚实的基础。

