---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/111-springmvc-file-upload

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

## 1. MyBatis的核心对象 

**什么是SqlSessionFactory?**

- `SqlSessionFactory`是`MyBatis`框架中十分重要的对象，它是单个数据库映射关系经过编译后的内存镜像，其主要作用是创建SqlSession。
- `SqlSessionFactory`对象的实例可以通过`SqlSessionFactoryBuilder`对象来构建，而`SqlSessionFactoryBuilder`则可以通过XML配置文件或一个预先定义好的`Configuration`实例构建出`SqlSessionFactory`的实例。

**构建SqlSessionFactory**

- 通过XML配置文件构建出的SqlSessionFactory实例现代码如下：

```java
InputStream inputStream = Resources.getResourceAsStream("配置文件位置");

SqlSessionFactory sqlSessionFactory = 
								new SqlSessionFactoryBuilder().build(inputStream);
```

- `SqlSessionFactory`对象是线程安全的，它一旦被创建，在整个应用执行期间都会存在。如果我们多次的创建同一个数据库的`SqlSessionFactory`，那么此数据库的资源将很容易被耗尽。为此，通常每一个数据库都会只对应一个`SqlSessionFactory`，所以在构建`SqlSessionFactory`实例时，建议使用单例模式。
- **在mybatis整合Spring之后，最好的方式是把sqlsessionfactory交给spring来做单例管理。**SqlSessionFactory是创建SqlSession的工厂，但是创建过程中需要反复加载全局配置文件，这一点是十分耗时的，为了优化项目，最好通过单例模式来管理它，使它只能创建一个对象，配置文件加载一次就可以了。（在mybatis整合Spring之后，最好的方式是把sqlsessionfactory交给spring来做单例管理）
  **背景：由于创建sqlsessionfactory的过程是重复性的，所以干脆做了一个工具类专门生成sqlsessionfactory**

**什么是SqlSession?**

>1. `SqlSession`是`MyBatis`框架中另一个重要的对象，它是应用程序与持久层之间执行交互操作的一个单线程对象，其主要作用是执行持久化操作。
>2. 每一个线程都应该有一个自己的`SqlSession` 实例，并且该实例是不能被共享的。同时，`SqlSession`实例也是线程不安全的，因此其使用范围最好在一次请求或一个方法中，绝不能将其放在一个类的静态字段、实例字段或任何类型的管理范围（如`Servlet`的`HttpSession`）中使用。

```java
使用完SqlSession对象后要及时关闭，通常可以将其放在finally块中关闭。
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
           // 此处执行持久化操作
    } finally {
          sqlSession.close();
    }
```

**SqlSession中的方法**

**查询方法：**

- < T > T selectOne(String statement);
- < T> T selectOne(String statement, Object parameter);
- < E> List< E > selectList(String statement);
- < E > List< E > selectList(String statement, Object parameter);
- < E > List< E > selectList(String statement, Object parameter, RowBounds rowBounds);
- void select(String statement, Object parameter, ResultHandler handler);

**插入、更新和删除方法：**

- int insert(String statement);
- int insert(String statement, Object parameter);
- int update(String statement);
- int update(String statement, Object parameter);
- int delete(String statement);
- int delete(String statement, Object parameter);

**其他方法：**

|           void commit();            |        提交事务的方法。        |
| :---------------------------------: | :----------------------------: |
|          void rollback();           |        回滚事务的方法。        |
|            void close();            |      关闭SqlSession对象。      |
| < T > T getMapper(Class< T > type); |   返回Mapper接口的代理对象。   |
|     Connection getConnection();     | 获取JDBC数据库连接对象的方法。 |

**多学一招**：使用工具类创建`SqlSession`

为了简化开发，通常在实际项目中都会使用工具类来创建`SqlSession`。

```java
     public class MybatisUtils {
          private static SqlSessionFactory sqlSessionFactory = null;
          static {
              try {
	    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
	    sqlSessionFactory =  new SqlSessionFactoryBuilder().build(reader);
              } catch (Exception e) {
	    e.printStackTrace();
              }
          }
          public static SqlSession getSession() {
               return sqlSessionFactory.openSession();
          }
    }
```



## 2. 配置文件 

> 在MyBatis框架的核心配置文件中，< configuration >元素是配置文件的根元素，其他元素都要在< configuration >元素内配置。 

**注意：这些子元素必须按照由上到下的顺序进行配置，否则MyBatis在解析XML配置文件的时候会报错。**

![](https://gitee.com/nateshao/images/raw/master/img/20211023122806.png)

### < properties>元素

> ​    < properties >是一个配置属性的元素，该元素通常用来将内部的配置外在化，即通过外部的配置来动态的替换内部定义的属性。例如，**数据库的连接**等属性，就可以通过典型的Java属性文件中的配置来替换，具体方式如下： 

  **1. 编写db.properties**

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis
jdbc.username=root
jdbc.password=123456
```

  **2. 配置<properties... />属性**

```properties
<properties resource="db.properties" />
```

  **3. 修改配置文件中数据库连接的信息**

```properties
<dataSource type="POOLED">
    <!-- 数据库驱动 -->
    <property name="driver" value="${jdbc.driver}" />
    <!-- 连接数据库的url -->
    <property name="url" value="${jdbc.url}" />
    <!-- 连接数据库的用户名 -->
    <property name="username" value="${jdbc.username}" />
    <!-- 连接数据库的密码 -->
    <property name="password" value="${jdbc.password}" />
</dataSource>
```



###  < settings>元素

>    < settings >元素主要用于改变MyBatis运行时的行为，例如开启二级缓存、开启延迟加载等。 

   < settings>元素中的常见配置，这些配置在配置文件中的使用方式如下：

```xml
     <!-- 设置 -->
     <settings>
         <setting name="cacheEnabled" value="true" />
         <setting name="lazyLoadingEnabled" value="true" />
         <setting name="multipleResultSetsEnabled" value="true" />
         <setting name="useColumnLabel" value="true" />
         <setting name="useGeneratedKeys" value="false" />
         <setting name="autoMappingBehavior" value="PARTIAL" />
         ...
     </settings>
```

**小提示：上述配置通常不需要开发人员去配置，一般作为了解即可。**

### < typeAliases >元素

>    < typeAliases >元素用于为配置文件中的Java类型设置一个简短的名字，即设置别名。别名的设置与XML配置相关，其使用的意义在于减少全限定类名的冗余。 

1. 使用< typeAliases >元素配置别名的方法如下：

```xml
     <typeAliases>
             <typeAlias alias="user" type="com.nateshao.po.User"/>
     </typeAliases>
```

2. 当POJO类过多时，可以通过自动扫描包的形式自定义别名，具体如下：

```xml
     <typeAliases>
             <package name="com.nateshao.po"/>
     </typeAliases>
```

**注意**：**如果在程序中使用了注解，则别名为其注解的值**

MyBatis框架默认为许多常见的Java类型提供了相应的类型别名，如下表所示。

![](https://gitee.com/nateshao/images/raw/master/img/20211023123612.png)







## 3. 映射文件

