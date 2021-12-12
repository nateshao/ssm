---
create by 千羽 2021-10-12
---

[TOC]

![img](http://mmbiz.qpic.cn/sz_mmbiz_jpg/icHiblwB4HdHxS62f3k7y9icWKTGx1qrr4D5qRtBlHFSo2Jfxj6mibj6PmiaqN9icmF9gMo1O2wNE9R7jnkdkWNJ8y9A/0?wx_fmt=jpeg)

> GitHub：https://github.com/nateshao/ssm/tree/master/104-spring-jdbc
>
> Gitee：https://gitee.com/nateshao/ssm/tree/master/104-spring-jdbc

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015153203.png" style="zoom:80%;" />



## 1. Spring JDBC

**Spring JDBC模块有什么作用？**

> ​     Spring的JDBC模块负责数据库资源管理和错误处理，大大简化了开发人员对数据库的操作，使得开发人员可以从繁琐的数据库操作中解脱出来，从而将更多的精力投入到编写业务逻辑当中。

**Spring JdbcTemplate的解析**

> 针对数据库的操作，Spring框架提供了JdbcTemplate类，该类是Spring框架数据抽象层的基础。可以说，JdbcTemplate类是Spring JDBC的核心类。  

​    JdbcTemplate类的继承结构具体如下图所示：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015153452.png" style="zoom:50%;" />

> ​    从JdbcTemplate的继承关系图可以看出，JdbcTemplate类的直接父类是JdbcAccessor，该类为子类提供了一些访问数据库时使用的公共属性。

**DataSource**： 其主要功能是**获取数据库连接**，还可以引入对数据库连接的缓冲池和分布式事务的支持，它可以作为访问数据库资源的标准接口。

**SQLExceptionTranslator**：该接口负责对SQLException进行转译工作。通过必要的设置获取SQLExceptionTranslator中的方法，可以使JdbcTemplate在需要处理SQLException时，委托SQLExceptionTranslator的实现类来完成相关的转译工作。

​    而JdbcOperations接口定义了在JdbcTemplate类中可以使用的操作集合，包括添加、修改、查询和删除等操作。

## Spring JDBC的配置

​    Spring JDBC模块主要由4个包组成，分别是core（核心包）、dataSource（数据源包）、object（对象包）和support（支持包）。

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015153807.png" style="zoom:80%;" />

​    从上表可以看出，Spring对数据库的操作都封装在了这几个包中，而想要使用Spring JDBC，就需要对其进行配置。

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015160707.png" style="zoom:80%;" />

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
 	http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
	<!-- 1配置数据源 -->
	<bean id="dataSource" class=
     "org.springframework.jdbc.datasource.DriverManagerDataSource">
		<!--数据库驱动 -->
		<property name="driverClassName" value="com.mysql.jdbc.Driver" />
		<!--连接数据库的url -->
		<property name="url" value="jdbc:mysql://localhost:3306/spring" />
		<!--连接数据库的用户名 -->
		<property name="username" value="root" />
		<!--连接数据库的密码 -->
		<property name="password" value="123456" />
	</bean>
	<!-- 2配置JDBC模板 -->
	<bean id="jdbcTemplate" 
		   class="org.springframework.jdbc.core.JdbcTemplate">
		<!-- 默认必须使用数据源 -->
		<property name="dataSource" ref="dataSource" />
	</bean>
	
	<!--定义id为accountDao的Bean-->
	<bean id="accountDao" class="com.nateshao.jdbc.AccountDaoImpl">
		<!-- 将jdbcTemplate注入到accountDao实例中 -->
		<property name="jdbcTemplate" ref="jdbcTemplate" />
	</bean>
	
</beans>
```

​    关于上述示例dataSource配置中的4个属性说明，如下表所示：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015161500.png" style="zoom:80%;" />

**注意**：上表中的属性值在实际配置时，需要根据数据库类型和设置进行相应配置。

## 2. Spring JdbcTemplate的常用方法

>    在JdbcTemplate核心类中，提供了大量的更新和查询数据库的方法，我们就是使用的这些方法来操作数据库的。

**execute( )**：execute(String sql)方法可用于执行sql语句
**update()**：update())用于执行插入、更新和删除操作
**query()**：query()用于执行数据查询操作



### execute()

使用execute(String sql)方法执行建表的案例实现步骤如下：

1. 在MySQL中创建一个名为spring的数据库;
2. 创建Web项目，导入相关maven包;
3. 创建Spring配置文件，配置数据源和JDBC模板;
4. 创建测试类，
5. 测试程序。

**Spring.sql**

```sql
CREATE DATABASE  IF NOT EXISTS `spring` ;

USE `spring`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `account` */

insert  into `account`(`id`,`username`,`balance`) values (2,'shaotongjie',2222),(3,'1',2222),(4,'a',2022),(5,'b',2322);
```

**Account.java**

```java
package com.nateshao.jdbc;

/**
 * @date Created by 邵桐杰 on 2021/10/15 15:50
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Data
public class Account {
    private Integer id;       // 账户id
    private String username; // 用户名
    private Double balance;  // 账户余额
}
```

**applicationContext.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://www.springframework.org/schema/beans 
   http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
   <!-- 1配置数据源 -->
   <bean id="dataSource" class=
     "org.springframework.jdbc.datasource.DriverManagerDataSource">
      <!--数据库驱动 -->
      <property name="driverClassName" value="com.mysql.jdbc.Driver" />
      <!--连接数据库的url -->
      <property name="url" value="jdbc:mysql://localhost:3306/spring?useSSL=false" />
      <!--连接数据库的用户名 -->
      <property name="username" value="root" />
      <!--连接数据库的密码 -->
      <property name="password" value="123456" />
   </bean>
   <!-- 2配置JDBC模板 -->
   <bean id="jdbcTemplate" 
         class="org.springframework.jdbc.core.JdbcTemplate">
      <!-- 默认必须使用数据源 -->
      <property name="dataSource" ref="dataSource" />
   </bean>
   
   <!--定义id为accountDao的Bean-->
   <bean id="accountDao" class="com.nateshao.jdbc.AccountDaoImpl">
      <!-- 将jdbcTemplate注入到accountDao实例中 -->
      <property name="jdbcTemplate" ref="jdbcTemplate" />
   </bean>
   
</beans>
```

**AccountDao.java**

```java
package com.nateshao.jdbc;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/15 15:50
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface AccountDao {
    // 添加
    public int addAccount(Account account);

    // 更新
    public int updateAccount(Account account);

    // 删除
    public int deleteAccount(int id);

    // 通过id查询
    public int queryAccountById(int id);
    // 查询所有账户
    public List<Account> findAllAccount();

    Account findAccountById(int i);
}
```

**AccountDaoImpl.java**

```java
package com.nateshao.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/15 15:55
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class AccountDaoImpl implements AccountDao {
    // 声明JdbcTemplate属性及其setter方法
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 添加账户
     * @param account
     * @return
     */
    public int addAccount(Account account) {
        // 定义SQL
        String sql = "insert into account(username,balance) value(?,?)";
        // 定义数组来存放SQL语句中的参数
        Object[] obj = new Object[]{
                account.getUsername(),
                account.getBalance()
        };
        // 执行添加操作，返回的是受SQL语句影响的记录条数
        int num = this.jdbcTemplate.update(sql, obj);
        return num;
    }

    /**
     * 更新账户
     * @param account
     * @return
     */
    public int updateAccount(Account account) {
        // 定义SQL
        String sql = "update account set username=?,balance=? where id = ?";
        // 定义数组来存放SQL语句中的参数
        Object[] params = new Object[]{
                account.getUsername(),
                account.getBalance(),
                account.getId()
        };
        // 执行添加操作，返回的是受SQL语句影响的记录条数
        int num = this.jdbcTemplate.update(sql, params);
        return num;
    }

    /**
     * 删除账户
     * @param id
     * @return
     */
    public int deleteAccount(int id) {
        // 定义SQL
        String sql = "delete  from account where id = ? ";
        // 执行添加操作，返回的是受SQL语句影响的记录条数
        int num = this.jdbcTemplate.update(sql, id);
        return num;
    }

    @Override
    public int queryAccountById(int id) {
        return 0;
    }

    /**
     * 通过id查询账户数据信息
     * @param id
     * @return
     */
    public Account findAccountById(int id) {
        //定义SQL语句
        String sql = "select * from account where id = ?";
        // 创建一个新的BeanPropertyRowMapper对象
        RowMapper<Account> rowMapper =
                new BeanPropertyRowMapper<Account>(Account.class);
        // 将id绑定到SQL语句中，并通过RowMapper返回一个Object类型的单行记录
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    /**
     * 查询所有账户信息
     * @return
     */
    public List<Account> findAllAccount() {
        // 定义SQL语句
        String sql = "select * from account";
        // 创建一个新的BeanPropertyRowMapper对象
        RowMapper<Account> rowMapper =
                new BeanPropertyRowMapper<Account>(Account.class);
        // 执行静态的SQL查询，并通过RowMapper返回结果
        return this.jdbcTemplate.query(sql, rowMapper);
    }

}
```

**测试类JdbcTemplateTest.java**

```java
package com.nateshao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/15 15:57
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class JdbcTemplateTest {
    /**
     * 使用execute()方法建表
     */
// public static void main(String[] args) {
//    // 加载配置文件
//    ApplicationContext applicationContext =
//       new ClassPathXmlApplicationContext("applicationContext.xml");
//    // 获取JdbcTemplate实例
//    JdbcTemplate jdTemplate =
//          (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
//    // 使用execute()方法执行SQL语句，创建用户账户管理表account
//    jdTemplate.execute("create table account(" +
//                      "id int primary key auto_increment," +
//                      "username varchar(50)," +
//                      "balance double)");
//    System.out.println("账户表account创建成功！");
// }
    @Test
    public void mainTest() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取JdbcTemplate实例
        JdbcTemplate jdTemplate =
                (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
        // 使用execute()方法执行SQL语句，创建用户账户管理表account
        jdTemplate.execute("create table account(" +
                "id int primary key auto_increment," +
                "username varchar(50)," +
                "balance double)");
        System.out.println("账户表account创建成功！");
    }

    @Test
    public void addAccountTest() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 创建Account对象，并向Account对象中添加数据
        Account account = new Account();
        account.setUsername("千羽");
        account.setBalance(1000.00);
        // 执行addAccount()方法，并获取返回结果
        int num = accountDao.addAccount(account);
        if (num > 0) {
            System.out.println("成功插入了" + num + "条数据！");
        } else {
            System.out.println("插入操作执行失败！");
        }
    }

    @Test
    public void updateAccountTest() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 创建Account对象，并向Account对象中添加数据
        Account account = new Account();
        account.setId(1);
        account.setUsername("tom");
        account.setBalance(2000.00);
        // 执行updateAccount()方法，并获取返回结果
        int num = accountDao.updateAccount(account);
        if (num > 0) {
            System.out.println("成功修改了" + num + "条数据！");
        } else {
            System.out.println("修改操作执行失败！");
        }
    }

    @Test
    public void deleteAccountTest() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 执行deleteAccount()方法，并获取返回结果
        int num = accountDao.deleteAccount(1);
        if (num > 0) {
            System.out.println("成功删除了" + num + "条数据！");
        } else {
            System.out.println("删除操作执行失败！");
        }
    }

    @Test
    public void findAccountByIdTest() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 执行findAccountById()方法
        Account account = accountDao.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void findAllAccountTest() {
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取AccountDao实例
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");
        // 执行findAllAccount()方法,获取Account对象的集合
        List<Account> account = accountDao.findAllAccount();
        // 循环输出集合中的对象
        for (Account act : account) {
            System.out.println(act);
        }
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211015184111.png)

![](https://gitee.com/nateshao/images/raw/master/img/20211015184125.png)

**多学一招**：使用JUnit单元测试

> 在软件开发过程中，需要有相应的测试工作。依据测试目的不同，可以将软件测试分为单元测试、集成测试、确认测试和系统测试等。其中单元测试在软件开发阶段是最底层的测试，它易于及时发现并解决问题。JUnit就是一个进行单元测试的开源框架，下面以上个示例，来学习单元测试框架JUnit4的使用。

**update()**

> update()方法可以完成插入、更新和删除数据的操作。在JdbcTemplate类中，提供了一系列的update()方法，其常用方法下表所示：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015165028.png" style="zoom:80%;" />

**query()**

>    JdbcTemplate类中还提供了大量的query()方法来处理各种对数据库表的查询操作。其中，常用的几个query()方法如下表所示：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211015165535.png" style="zoom:80%;" />





























