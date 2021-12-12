---
create by 千羽 2021-10-12
---

[TOC]



![](http://mmbiz.qpic.cn/sz_mmbiz_jpg/icHiblwB4HdHxEjOyzTC2IbN95Dc7fTIHBSHIVWAKgiamKGVfOe9aIjwmGibrJWwy9BYocKMt8htYJCkwnicsJjQY4Q/0?wx_fmt=jpeg)


> GitHub：https://github.com/nateshao/ssm/tree/master/115-mybatis-associated-one-many

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

## 1. 关联关系概述 

**为什么学习MyBatis关联关系？**

> 实际的开发中，对数据库的操作常常会涉及到多张表，这在面向对象中就涉及到了**对象与对象之间的关联关系**。针对多表之间的操作，MyBatis提供了关联映射，通过关联映射就可以很好的处理对象与对象之间的关联关系。
>
> 所以，，这里将对MyBatis的关联关系映射进行详细的讲解。

在关系型数据库中，多表之间存在着三种关联关系，分别为**一对一、一对多和多对多**，如下图所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211024173006.png)

**一对一**：在任意一方引入对方主键作为外键；

**一对多**：在“多”的一方，添加“一”的一方的主键作为外键；

**多对多**：产生中间关系表，引入两张表的主键作为外键，两个主键成为联合主键或使用新的字段作为主键。

在Java中，通过对象也可以进行关联关系描述，如图下图所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211024173900.png)

## 2. 一对一 

​    在现实生活中，一对一关联关系是十分常见的。例如，一个人只能有一个身份证，同时一个身份证也只会对应一个人。

![](https://gitee.com/nateshao/images/raw/master/img/20211024184851.png)



那么使用MyBatis是怎么处理图中的这种一对一关联关系的呢？

在前面所讲解的< resultMap >元素中，包含了一个< association >子元素，MyBatis就是通过该元素来处理一对一关联关系的。

**在< association >元素中，通常可以配置以下属性:**

`property`：指定映射到的实体类对象属性，与表字段一 一对应

`column`：指定表中对应的字段

`javaType`：指定映射到实体对象属性的类型

`select`：指定引入嵌套查询的子SQL语句，该属性用于关联映射中的嵌套查询

`fetchType`：指定在关联查询时是否启用延迟加载。该属性有lazy和eager两个属性值，默认值为lazy（即默认关联映射延迟加载）

MyBatis加载关联关系对象主要通过两种方式:嵌套查询和嵌套结果。

1. **第一种**:   嵌套查询是通过执行另外一条SQL映射语句来返回预期的复杂类型。
   - 嵌套查询是在查询SQL中嵌入一个子查询SQL；
   - 嵌套查询会执行多条SQL语句；
   - 嵌套查询SQL语句编写较为简单；

2. **第二种**:    嵌套结果是使用嵌套结果映射来处理重复的联合结果的子集。
   - 嵌套结果是一个嵌套的多表查询SQL；
   - 嵌套结果只会执行一条复杂的SQL语句；
   - 嵌套结果SQL语句编写比较复杂；

> 虽然使用嵌套查询的方式比较简单，但是嵌套查询的方式要执行多条SQL语句，这对于大型数据集合和列表展示不是很好，因为这样可能会导致成百上千条关联的SQL语句被执行，从而极大的消耗数据库性能并且会降低查询效率。

**多学一招**：MyBatis延迟加载的配置

使用MyBatis的延迟加载在一定程度上可以降低运行消耗并提高查询效率。MyBatis默认没有开启延迟加载，需要在核心配置文件中的< settings >元素内进行配置，具体配置方式如下：

```sql
    <settings>
            <setting name="lazyLoadingEnabled" value="true" />  
            <setting name="aggressiveLazyLoading" value="false"/>  
    </settings>
```

在映射文件中，< association > 元素和< collection > 元素中都已默认配置了延迟加载属性，即默认属性fetchType="lazy"（属性fetchType="eager"表示立即加载），所以在配置文件中开启延迟加载后，无需在映射文件中再做配置。

使用< association >元素进行一对一关联映射非常简单，只需要参考如下两种示例配置即可。

![](https://gitee.com/nateshao/images/raw/master/img/20211024201949.png)

代码实现：

```sql
第一种：
<!-- 嵌套查询：通过执行另外一条SQL映射语句来返回预期的特殊类型 -->
    <select id="findPersonById" parameterType="Integer"
            resultMap="IdCardWithPersonResult">
		SELECT * from tb_person where id=#{id}
	</select>
    <resultMap type="Person" id="IdCardWithPersonResult">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="age" column="age"/>
        <result property="sex" column="sex"/>
        <!-- 一对一：association使用select属性引入另外一条SQL语句 -->
        <association property="card" column="card_id" javaType="IdCard"
                     select="com.nateshao.mapper.IdCardMapper.findCodeById"/>
    </resultMap>
第二种：
    <!-- 嵌套结果：使用嵌套结果映射来处理重复的联合结果的子集 -->
    <select id="findPersonById2" parameterType="Integer"
            resultMap="IdCardWithPersonResult2">
	    SELECT p.*,idcard.code
	    from tb_person p,tb_idcard idcard
	    where p.card_id=idcard.id 
	    and p.id= #{id}
	</select>
    <resultMap type="Person" id="IdCardWithPersonResult2">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="age" column="age"/>
        <result property="sex" column="sex"/>
        <association property="card" javaType="IdCard">
            <id property="id" column="card_id"/>
            <result property="code" column="code"/>
        </association>
    </resultMap>
```

**Person.java**

```java
@Data
public class Person {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private IdCard card;  //个人关联的证件
}
```

**IdCard.java**

```java
@Data
public class IdCard {
    private Integer id;
    private String code;
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211025120834.png)

## 3. 一对多 

开发人员接触更多的关联关系是一对多（或多对一）。例如，一个用户可以有多个订单，同时多个订单归一个用户所有。

![](https://gitee.com/nateshao/images/raw/master/img/20211024201637.png)

​    那么使用MyBatis是怎么处理这种一对多关联关系的呢？

​    在前面所讲解的< resultMap >元素中，包含了一个< collection >子元素，MyBatis就是通过该元素来处理一对多关联关系的。

< collection >子元素的属性大部分与< association>元素相同，但其还包含一个特殊属性--ofType 。

**ofType**：ofType属性与javaType属性对应，它用于指定实体对象中集合类属性所包含的元素类型。

![](https://gitee.com/nateshao/images/raw/master/img/20211024202032.png)

代码实现：

**MybatisAssociatedTest.java**

```java
/**
 * 一对多
 */
@Test
public void findUserTest() {
    // 1、通过工具类生成SqlSession对象
    SqlSession session = MybatisUtils.getSession();
    // 2、查询id为1的用户信息
    User user = session.selectOne("com.nateshao.mapper."
            + "UserMapper.findUserWithOrders", 1);
    // 3、输出查询结果信息
    System.out.println(user);
    // 4、关闭SqlSession
    session.close();
}
```

**UserMapper.xml**

```sql
<!-- 一对多：查看某一用户及其关联的订单信息 
      注意：当关联查询出的列名相同，则需要使用别名区分 -->
<select id="findUserWithOrders" parameterType="Integer" 
                  resultMap="UserWithOrdersResult">
   SELECT u.*,o.id as orders_id,o.number 
   from tb_user u,tb_orders o 
   WHERE u.id=o.user_id 
        and u.id=#{id}
</select>
<resultMap type="User" id="UserWithOrdersResult">
   <id property="id" column="id"/>
   <result property="username" column="username"/>
   <result property="address" column="address"/>
   <!-- 一对多关联映射：collection 
      ofType表示属性集合中元素的类型，List<Orders>属性即Orders类 -->
   <collection property="ordersList" ofType="Orders">
      <id property="id" column="orders_id"/>
      <result property="number" column="number"/>
   </collection>
</resultMap>
```

**User.java**

```java
@Data
public class User {
    private Integer id;                 // 用户编号
    private String username;           // 用户姓名
    private String address;            // 用户地址
    private List<Orders> ordersList; //用户关联的订单
}
```

**Orders.java**

```java
@Data
public class Orders {
    private Integer id;    //订单id
    private String number;//订单编号
    //关联商品集合信息
    private List<Product> productList;

}
```

运行结果：

```java
User(id=1, username=詹姆斯, address=克利夫兰, ordersList=[Orders(id=1, number=1000011, productList=null), Orders(id=2, number=1000012, productList=null)])
```

## 4. 多对多

​    在实际项目开发中，多对多的关联关系也是非常常见的。以订单和商品为例，一个订单可以包含多种商品，而一种商品又可以属于多个订单。

![](https://gitee.com/nateshao/images/raw/master/img/20211024202108.png)



​    在数据库中，多对多的关联关系通常使用一个中间表来维护，中间表中的订单id作为外键参照订单表的id，商品id作为外键参照商品表的id。

![](https://gitee.com/nateshao/images/raw/master/img/20211024202113.png)

​    在MyBatis中，多对多的关联关系查询，同样可以使用前面介绍的< collection >元素进行处理（其用法和一对多关联关系查询语句用法基本相同）。

**MybatisAssociatedTest.java**

```java
/**
 * 多对多
 */
@Test
public void findOrdersTest() {
    // 1、通过工具类生成SqlSession对象
    SqlSession session = MybatisUtils.getSession();
    // 2、查询id为1的订单中的商品信息
    Orders orders = session.selectOne("com.nateshao.mapper."
            + "OrdersMapper.findOrdersWithPorduct", 1);
    // 3、输出查询结果信息
    System.out.println(orders);
    // 4、关闭SqlSession
    session.close();
}
```

**OrdersMapper.xml**

```sql
<!-- 多对多嵌套结果查询：查询某订单及其关联的商品详情 -->
<select id="findOrdersWithPorduct2" parameterType="Integer"
           resultMap="OrdersWithPorductResult2">
    select o.*,p.id as pid,p.name,p.price from tb_orders o,tb_product p,tb_ordersitem  oi WHERE oi.orders_id=o.id and oi.product_id=p.id and o.id=#{id}
</select>
   <!-- 自定义手动映射类型 -->
   <resultMap type="Orders" id="OrdersWithPorductResult2">
       <id property="id" column="id"/>
       <result property="number" column="number"/>
       <!-- 多对多关联映射：collection -->
       <collection property="productList" ofType="Product">
           <id property="id" column="pid"/>
           <result property="name" column="name"/>
           <result property="price" column="price"/>
       </collection>
   </resultMap>
```

**Orders.java**

```java
@Data
public class Orders {
    private Integer id;    //订单id
    private String number;//订单编号
    //关联商品集合信息
    private List<Product> productList;
}
```

**Product.java**

```java
@Data
public class Product {
    private Integer id;  //商品id
    private String name; //商品名称
    private Double price;//商品单价
    private List<Orders> orders; //与订单的关联属性
}
```

## 总结：   

 本章首先对开发中涉及到的数据表之间以及对象之间的关联关系作了简要介绍，并由此引出了MyBatis框架中对关联关系的处理；然后通过案例对MyBatis框架处理实体对象之间的三种关联关系进行了详细讲解。

​    通过本章的学习，读者可以了解数据表以及对象中所涉及到的三种关联关系，并能够使用MyBatis框架对三种关联关系的查询进行处理。MyBatis中的关联查询操作在实际开发中非常普遍，熟练掌握这三种关联查询方式有助于提高项目的开发效率，读者一定要多加练习。

