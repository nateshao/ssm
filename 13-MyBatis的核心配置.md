---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/113-mybatis-detail

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

### < typeHandler >元素

>    typeHandler的作用就是将预处理语句中传入的参数从javaType（Java类型）转换为jdbcType（JDBC类型），或者从数据库取出结果时将jdbcType转换为javaType。 

​    < typeHandler >元素可以在配置文件中注册自定义的类型处理器，它的使用方式有两种。

1. 注册一个类的类型处理器

   ```java
   <typeHandlers> 
       <typeHandler handler="com.nateshao.type.CustomtypeHandler" />
   </typeHandlers>
   ```

2. 注册一个包中所有的类型处理器

   ```java
   <typeHandlers> 
        <package name="com.nateshao.type" />
   </typeHandlers>
   ```

### < objectFactory >元素

> MyBatis中默认的ObjectFactory的作用是实例化目标类，它既可以通过默认构造方法实例化，也可以在参数映射存在的时候通过参数构造方法来实例化。通常使用默认的ObjectFactory即可。

​    大部分场景下都不用配置和修改默认的ObjectFactory ，如果想覆盖ObjectFactory的默认行为，可以通过自定义ObjectFactory来实现，具体如下：

1. 自定义一个对象工厂

   ```java
   public class MyObjectFactory extends DefaultObjectFactory {
               private static final long serialVersionUID = -4114845625429965832L;
               public <T> T create(Class<T> type) {
   	return super.create(type);
               }
               public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, 
                            List<Object> constructorArgs) {
   	return super.create(type, constructorArgTypes, constructorArgs);
               }
               public void setProperties(Properties properties) {
   	super.setProperties(properties);
               }
               public <T> boolean isCollection(Class<T> type) {
   	return Collection.class.isAssignableFrom(type);
               }
   }
   ```

2. 在配置文件中使用< objectFactory >元素配置自定义的ObjectFactory

   ```xml
   <objectFactory type="com.nateshao.factory.MyObjectFactory">
        <property name="name" value="MyObjectFactory"/>
   </objectFactory>
   ```

**注意：由于自定义ObjectFactory在实际开发时不经常使用，这里读者只需要了解即可。**

### < plugins >元素

> MyBatis允许在已映射语句执行过程中的某一点进行拦截调用，这种拦截调用是通过插件来实现的。< plugins >元素的作用就是配置用户所开发的插件。

​    如果用户想要进行插件开发，必须要先了解其内部运行原理，因为在试图修改或重写已有方法的行为时，很可能会破坏MyBatis原有的核心模块。

​    关于插件的使用，本书不做详细讲解，只需了解< plugins >元素的作用即可，有兴趣的小伙伴可以查找官方文档等资料自行学习。

### < environments >元素

> < environments >元素用于对环境进行配置。MyBatis的环境配置实际上就是数据源的配置，我们可以通过< environments >元素配置多种数据源，即配置多种数据库。

   使用< environments >元素进行环境配置的示例如下：

```xml
<environments default="development">
    <environment id="development">
      <transactionManager type="JDBC" />
            <dataSource type="POOLED">
                   <property name="driver" value="${jdbc.driver}" />
                   <property name="url" value="${jdbc.url}" />
                   <property name="username" value="${jdbc.username}" />
                   <property name="password" value="${jdbc.password}" />
         </dataSource>
     </environment>
            ...
</environments>
```

**事务管理器的配置**

在`MyBatis`中，可以配置两种类型的事务管理器，分别是`JDBC`和`MANAGED`。关于这两个事务管理器的描述如下：

- **JDBC**：此配置直接使用了JDBC的提交和回滚设置，它依赖于从数据源得到的连接来管理事务的作用域。
- **MANAGED**：此配置从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期。默认情况下，它会关闭连接，但一些容器并不希望这样，为此可以将`closeConnection`属性设置为`false`来阻止它默认的关闭行为。

**数据源的配置**

1. UNPOOLED

   配置此数据源类型后，在每次被请求时会打开和关闭连接。它对没有性能要求的简单应用程序是一个很好的选择。在使用时，需要配置5种属性。

   ![](https://gitee.com/nateshao/images/raw/master/img/20211023193614.png)

2. POOLED

   此数据源利用“池”的概念将JDBC连接对象组织起来，避免了在创建新的连接实例时所需要初始化和认证的时间。这种方式使得并发Web应用可以快速的响应请求，是当前流行的处理方式。在使用时，可以配置更多的属性。

   ![](https://gitee.com/nateshao/images/raw/master/img/20211023193658.png)

3. JNDI

   可以在EJB或应用服务器等容器中使用。容器可以集中或在外部配置数据源，然后放置一个JNDI上下文的引用。在使用时，需要配置2个属性。

   ![](https://gitee.com/nateshao/images/raw/master/img/20211023193850.png)

### 7.2.9 < mappers>元素

< mappers>元素用于指定MyBatis映射文件的位置，一般可以使用以下4种方法引入映射器文件，具体如下。

1. 使用类路径引入

   ```xml
   <mappers>
       <mapper resource="com/nateshao/mapper/UserMapper.xml"/>
   </mappers>
   ```

2. 使用本地文件路径引入

   ```xml
   <mappers>
       <mapper url="file:///D:/com/nateshao/mapper/UserMapper.xml"/>
   </mappers>
   ```

3. 使用接口类引入

   ```xml
   <mappers>
       <mapper class="com.nateshao.mapper.UserMapper"/>
   </mappers>
   ```

4. 使用包名引入

   ```xml
   <mappers>
       <package name="com.nateshao.mapper"/>
   </mappers>
   ```

## 3. 映射文件

**主要元素**

在映射文件中，< mapper >元素是映射文件的根元素，其他元素都是它的子元素。 

<img src="https://gitee.com/nateshao/images/raw/master/img/20211023194441.png" style="zoom:200%;" />

### < select >元素

> < select >元素用来映射查询语句，它可以帮助我们从数据库中读取出数据，并组装数据给业务开发人员。

使用< select >元素执行查询操作非常简单，其示例如下：

```xml
<select id="findCustomerById" parameterType="Integer"
            resultType="com.nateshao.po.Customer">
            select * from t_customer where id = #{id}
</select>
```

**< select >元素的常用属性**

![](https://gitee.com/nateshao/images/raw/master/img/20211023194652.png)

### < insert >元素

< insert >元素用于映射插入语句，在执行完元素中定义的SQL语句后，会返回一个表示插入记录数的整数。

< insert >元素的配置示例如下：

```xml
<insert
      id="addCustomer"
      parameterType="com.nateshao.po.Customer"
      flushCache="true"
      statementType="PREPARED"
      keyProperty=""
      keyColumn=""
      useGeneratedKeys=""
      timeout="20">
```

**< insert >元素的属性**

< insert >元素的属性与< select >元素的属性大部分相同，但还包含了3个特有属性，这3个属性的描述如下所示。

![](https://gitee.com/nateshao/images/raw/master/img/20211023194936.png)

执行插入操作后，很多时候需要返回插入成功的数据生成的主键值，此时就可以通过上面讲解的3个属性来实现。

1. 对于支持主键自助增长的数据库（如MySQL），可以通过如下配置实现：

   ```xml
   <insert id="addCustomer" parameterType="com.nateshao.po.Customer"
               keyProperty="id" useGeneratedKeys="true" >
         insert into t_customer(username,jobs,phone)
         values(#{username},#{jobs},#{phone})
   </insert>
   ```

2. 对于不支持主键自助增长的数据库(如Oracle)，可以通过如下配置实现：

   ```xml
   <insert id="insertCustomer" parameterType="com.nateshao.po.Customer">
         <selectKey keyProperty="id" resultType="Integer" order="BEFORE">
               select if(max(id) is null, 1, max(id) +1) as newId from t_customer
         </selectKey>	        
         insert into t_customer(id,username,jobs,phone)
         values(#{id},#{username},#{jobs},#{phone})
   </insert>
   ```

   

### < update >和< delete >元素

```xml
<update
      id="updateCustomer"
      parameterType="com.nateshao.po.Customer"
      flushCache="true"
      statementType="PREPARED"
      timeout="20">
<delete
      id="deleteCustomer"
      parameterType="com.nateshao.po.Customer"
      flushCache="true"
      statementType="PREPARED"
      timeout="20">
```

```xml
<update id="updateCustomer" parameterType="com.nateshao.po.Customer">
       update t_customer 
       set username=#{username},jobs=#{jobs},phone=#{phone}
       where id=#{id}
</update>

<delete id="deleteCustomer" parameterType="Integer">
        delete from t_customer where id=#{id}
</delete>
```

### < sql >元素

> 在一个映射文件中，通常需要定义多条SQL语句，这些SQL语句的组成可能有一部分是相同的（如多条select语句中都查询相同的id、username、jobs字段），如果每一个SQL语句都重写一遍相同的部分，势必会增加代码量，导致映射文件过于臃肿。那么有没有什么办法将这些SQL语句中相同的组成部分抽取出来，然后在需要的地方引用呢？

**< sql >元素的作用就是定义可重用的SQL代码片段，然后在其他语句中引用这一代码片段。**

定义一个包含id、username、jobs和phone字段的代码片段如下：

```xml
<sql id="customerColumns">id,username,jobs,phone</sql>
```

上述代码片段可以包含在其他语句中使用，具体如下：

```xml
<select id="findCustomerById" parameterType="Integer"
            resultType="com.nateshao.po.Customer">
    select <include refid="customerColumns"/>
    from t_customer 
    where id = #{id}
</select>
```

**定义sql片段**

```xml
<!--定义表的前缀名 -->
<sql id="tablename">
    ${prefix}customer
</sql>
<!--定义要查询的表 -->
<sql id="someinclude">
    from
    <include refid="${include_target}" />		
</sql>
<!--定义查询列 -->
<sql id="customerColumns">
   id,username,jobs,phone
</sql>
<select id="findCustomerById" parameterType="Integer" 
         resultType="com.nateshao.po.Customer">
    select 
    <include refid="customerColumns"/>
    <include refid="someinclude">
        <property name="prefix" value="t_" />
        <property name="include_target" value="tablename" />
    </include>
    where id = #{id}
</select>
```

通过< include >元素的refid属性引用id为someinclude的代码片段，先加入from,再通过获取< property >元素的值来组成表名

```sql
select id,username,jobs,phone from t_customer where id = ?
```

加入where子句

### < resultMap >元素

< resultMap >元素表示结果映射集，是MyBatis中最重要也是最强大的元素。它的主要作用是定义映射规则、级联的更新以及定义类型转化器等。

< resultMap >元素中包含了一些子元素，它的元素结构如下所示：

```xml
<resultMap type="" id="">
       <constructor>    <!-- 类在实例化时,用来注入结果到构造方法中-->
             <idArg/>      <!-- ID参数;标记结果作为ID-->
             <arg/>          <!-- 注入到构造方法的一个普通结果-->
       </constructor>  
       <id/>                 <!-- 用于表示哪个列是主键-->
       <result/>           <!-- 注入到字段或JavaBean属性的普通结果-->
       <association property="" />        <!-- 用于一对一关联 -->
       <collection property="" />          <!-- 用于一对多关联 -->
       <discriminator javaType="">      <!-- 使用结果值来决定使用哪个结果映射-->
            <case value="" />                   <!-- 基于某些值的结果映射 -->
       </discriminator>	
</resultMap>
```



## 代码实现

![](https://gitee.com/nateshao/images/raw/master/img/20211023221218.png)

**db.properties**

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis?useSSL=false
jdbc.username=root
jdbc.password=123456
```

**log4j.properties**

```properties
# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

**mybatis-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<properties resource="db.properties" />
	<!-- 定义别名 -->
	<typeAliases>
		<!-- <typeAlias alias="user" type="com.nateshao.po.User" /> -->
		<package name="com.nateshao.po" />
	</typeAliases>
	
	<!-- 配置自定义工厂 -->
	<objectFactory type="com.nateshao.factory.MyObjectFactory">
     <property name="name" value="MyObjectFactory"/>
	</objectFactory>

	<!--1.配置环境 ，默认的环境id为mysql -->
	<environments default="mysql">
		<!--1.2.配置id为mysql的数据库环境 -->
		<environment id="mysql">
			<!-- 使用JDBC的事务管理 -->
			<transactionManager type="JDBC" />
			<!--数据库连接池 -->
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
		</environment>
	</environments>

	<!--2.配置Mapper的位置 -->
	<mappers>
		<mapper resource="mapper/CustomerMapper.xml" />
		<mapper resource="mapper/UserMapper.xml" />
	</mappers>
</configuration>
```

**mapper/CustomerMapper.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace表示命名空间 -->
<mapper namespace="com.nateshao.mapper.CustomerMapper">
    <!--1.根据客户编号获取客户信息 -->
    <!--   <select id="findCustomerById" parameterType="Integer" -->
    <!--      resultType="com.nateshao.po.Customer"> -->
    <!--      select * from t_customer where id = #{id} -->
    <!--   </select> -->
    <!--2.根据客户名模糊查询客户信息列表 -->
    <select id="findCustomerByName" parameterType="String"
            resultType="com.nateshao.po.Customer">
      select * from t_customer where username like '%${value}%'
   </select>
    <!-- 3.添加客户信息 -->
    <insert id="addCustomer" parameterType="com.nateshao.po.Customer"
            keyProperty="id" useGeneratedKeys="true">
      insert into t_customer(username,jobs,phone)
      values(#{username},#{jobs},#{phone})
   </insert>
    <!-- 对于不支持自动生成主键的数据库，或取消自主增长规则的数据库可以自定义主键生成规则 -->
    <insert id="insertCustomer" parameterType="com.nateshao.po.Customer">
        <selectKey keyProperty="id" resultType="Integer" order="BEFORE">
            select if(max(id) is null, 1, max(id) +1) as newId from t_customer
        </selectKey>
        insert into t_customer(id,username,jobs,phone)
        values(#{id},#{username},#{jobs},#{phone})
    </insert>
    <!-- 4.更新客户信息 -->
    <update id="updateCustomer" parameterType="com.nateshao.po.Customer">
      update t_customer 
      set username=#{username},jobs=#{jobs},phone=#{phone}
      where id=#{id}
   </update>
    <!-- 5.删除客户信息 -->
    <delete id="deleteCustomer" parameterType="Integer">
      delete from t_customer where id=#{id}
   </delete>

    <!--定义代码片段 -->
    <!--   <sql id="customerColumns">id,username,jobs,phone</sql> -->
    <!--   <select id="findCustomerById" parameterType="Integer" -->
    <!--      resultType="com.nateshao.po.Customer"> -->
    <!--      select <include refid="customerColumns"/> -->
    <!--      from t_customer  -->
    <!--      where id = #{id} -->
    <!--   </select> -->

    <!--定义表的前缀名 -->
    <sql id="tablename">
      ${prefix}customer
   </sql>
    <!--定义要查询的表 -->
    <sql id="someinclude">
        from
        <include refid="${include_target}"/>
    </sql>
    <!--定义查询列 -->
    <sql id="customerColumns">
      id,username,jobs,phone
   </sql>
    <!--根据id查询客户信息 -->
    <select id="findCustomerById" parameterType="Integer"
            resultType="com.nateshao.po.Customer">
        select
        <include refid="customerColumns"/>
        <include refid="someinclude">
            <property name="prefix" value="t_"/>
            <property name="include_target" value="tablename"/>
        </include>
        where id = #{id}
    </select>

</mapper>
```

**mapper/UserMapper.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nateshao.mapper.UserMapper">
    <resultMap type="com.nateshao.po.User" id="resultMap">
        <id property="id" column="t_id"/>
        <result property="username" column="username"/>
        <result property="birthday" column="birthday"/>
        <result property="sex" column="sex"/>
        <result property="address" column="address"/>
    </resultMap>
    <select id="findAllUser" resultMap="resultMap">
      select * from user
   </select>
</mapper>
```

**Customer.java**

```java
package com.nateshao.po;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:17
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
/**
 * 客户持久化类
 */
public class Customer {
    private Integer id;       // 主键id
    private String username; // 客户名称
    private String jobs;      // 职业
    private String phone;     // 电话
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
    @Override
    public String toString() {
        return "Customer [id=" + id + ", username=" + username +
                ", jobs=" + jobs + ", phone=" + phone + "]";
    }
}
```

**User.java**

```java
@Data
public class User {
    private Integer id;
    private String username;
    private Date birthday;
    private char sex;
    private String address;
}
```

**MybatisUtils.java**

```java
package com.nateshao.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.Reader;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:20
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory = null;
    // 初始化SqlSessionFactory对象
    static {
        try {
            // 使用MyBatis提供的Resources类加载MyBatis的配置文件
            Reader reader =
                    Resources.getResourceAsReader("mybatis-config.xml");
            // 构建SqlSessionFactory工厂
            sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 获取SqlSession对象的静态方法
    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }
}
```

**MyObjectFactory.java**

```java
package com.nateshao.factory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @date Created by 邵桐杰 on 2021/10/22 23:24
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 自定义工厂类
 */
public class MyObjectFactory extends DefaultObjectFactory {
    private static final long serialVersionUID = -4114845625429965832L;

    public <T> T create(Class<T> type) {
        return super.create(type);
    }

    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes,
                        List<Object> constructorArgs) {
        return super.create(type, constructorArgTypes, constructorArgs);
    }

    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
```



**测试类：MybatisTest.java**

```java
package com.nateshao.test;

import com.nateshao.po.Customer;
import com.nateshao.po.User;
import com.nateshao.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;
/**
 * @date Created by 邵桐杰 on 2021/10/22 23:27
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description: 入门程序测试类
 */
public class MybatisTest {
    /**
     * 1.根据客户编号查询客户信息
     */
    @Test
    public void findCustomerByIdTest() {
        // 获取SqlSession
        SqlSession sqlSession = MybatisUtils.getSession();
        // SqlSession执行映射文件中定义的SQL，并返回映射结果
        Customer customer = sqlSession.selectOne("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerById", 1);
        // 打印输出结果
        System.out.println(customer.toString());
        // 关闭SqlSession
        sqlSession.close();
    }
    /**
     * 2.根据用户名称来模糊查询用户信息列表
     */
    @Test
    public void findCustomerByNameTest() {
        // 获取SqlSession
        SqlSession sqlSession = MybatisUtils.getSession();
        // SqlSession执行映射文件中定义的SQL，并返回映射结果
        List<Customer> customers = sqlSession.selectList("com.nateshao.mapper"
                + ".CustomerMapper.findCustomerByName", "j");
        for (Customer customer : customers) {
            //打印输出结果集
            System.out.println(customer);
        }
        // 关闭SqlSession
        sqlSession.close();
    }
    /**
     * 3.添加客户
     */
    @Test
    public void addCustomerTest(){
        // 获取SqlSession
        SqlSession sqlSession = MybatisUtils.getSession();
        Customer customer = new Customer();
        customer.setUsername("rose");
        customer.setJobs("student");
        customer.setPhone("13333533092");
        // 使用主键自助增长的添加方法
//		int rows = sqlSession.insert("com.nateshao.mapper."
//				+ "CustomerMapper.addCustomer", customer);
        // 使用自定义主键值的添加方法
        int rows = sqlSession.insert("com.nateshao.mapper."
                + "CustomerMapper.insertCustomer", customer);
        // 输出插入数据的主键id值
        System.out.println(customer.getId());
        if(rows > 0){
            System.out.println("您成功插入了"+rows+"条数据！");
        }else{
            System.out.println("执行插入操作失败！！！");
        }
        sqlSession.commit();
        sqlSession.close();
    }
    /**
     * 4.更新客户
     */
    @Test
    public void updateCustomerTest() throws Exception{
        // 获取SqlSession
        SqlSession sqlSession = MybatisUtils.getSession();
        // SqlSession执行更新操作
        // 创建Customer对象，并向对象中添加数据
        Customer customer = new Customer();
        customer.setId(4);
        customer.setUsername("rose");
        customer.setJobs("programmer");
        customer.setPhone("13311111111");
        // 执行sqlSession的更新方法，返回的是SQL语句影响的行数
        int rows = sqlSession.update("com.nateshao.mapper"
                + ".CustomerMapper.updateCustomer", customer);
        // 通过返回结果判断更新操作是否执行成功
        if(rows > 0){
            System.out.println("您成功修改了"+rows+"条数据！");
        }else{
            System.out.println("执行修改操作失败！！！");
        }
        // 提交事务
        sqlSession.commit();
        // 关闭SqlSession
        sqlSession.close();
    }
    /**
     * 5.删除客户
     */
    @Test
    public void deleteCustomerTest() {
        // 获取SqlSession
        SqlSession sqlSession = MybatisUtils.getSession();
        // SqlSession执行删除操作
        // 执行SqlSession的删除方法，返回的是SQL语句影响的行数
        int rows = sqlSession.delete("com.nateshao.mapper"
                + ".CustomerMapper.deleteCustomer", 4);
        // 通过返回结果判断删除操作是否执行成功
        if(rows > 0){
            System.out.println("您成功删除了"+rows+"条数据！");
        }else{
            System.out.println("执行删除操作失败！！！");
        }
        // 提交事务
        sqlSession.commit();
        // 关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void findAllUserTest() {
        // 获取SqlSession
        SqlSession sqlSession = MybatisUtils.getSession();
        // SqlSession执行映射文件中定义的SQL，并返回映射结果
        List<User> list = 		           sqlSession.selectList("com.nateshao.mapper.UserMapper.findAllUser");
        for (User user : list) {
            System.out.println(user);
        }
        // 关闭SqlSession
        sqlSession.close();
    }
}
```















