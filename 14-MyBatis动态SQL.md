---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/114-mybatis-dynamic-sql

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

## 1. 动态SQL中的元素

**动态SQL有什么作用？**

> 开发人员在使用JDBC或其他类似的框架进行数据库开发时，通常都要根据需求去手动拼装SQL，这是一个非常麻烦且痛苦的工作，而MyBatis提供的对SQL语句动态组装的功能，恰能很好的解决这一麻烦工作。

动态SQL是MyBatis的强大特性之一，MyBatis3采用了功能强大的基于OGNL的表达式来完成动态SQL。动态SQL主要元素如下表所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211024105847.png)

##  2. < if >元素 

> 在MyBatis中，< if >元素是最常用的判断语句，它类似于Java中的if语句，主要用于实现某些简单的条件选择。其基本使用示例如下：

```sql
select * from t_customer where 1=1 
     <if test="username !=null and username !=''">
		and username like concat('%',#{username}, '%')
     </if>
     <if test="jobs !=null and jobs !=''">
		and jobs= #{jobs}
     </if>
```

使用< if >元素对username和jobs进行非空判断，并动态组装SQL

> 在实际应用中，我们可能会通过多个条件来精确的查询某个数据。例如，要查找某个客户的信息，可以通过姓名和职业来查找客户，也可以不填写职业直接通过姓名来查找客户，还可以都不填写而查询出所有客户，此时姓名和职业就是非必须条件。



## 3. < choose >及其子元素

```xml
<!--<choose>(<when>、<otherwise>)元素使用 -->
    <select id="findCustomerByNameOrJobs" parameterType="com.nateshao.po.Customer"
            resultType="com.nateshao.po.Customer">
        select * from t_customer where 1=1
        <choose>
            <when test="username !=null and username !=''">
                and username like concat('%',#{username}, '%')
            </when>
            <when test="jobs !=null and jobs !=''">
                and jobs= #{jobs}
            </when>
            <otherwise>
                and phone is not null
            </otherwise>
        </choose>
    </select>
```

使用< choose >及其子元素依次对条件进行非空判断，并动态组装SQL

## 4. < when >、< trim >元素

​    在前面中，映射文件中编写的SQL后面都加入了“where 1=1”的条件，那么到底为什么要这么写呢？如果将where后“1=1”的条件去掉，那么MyBatis所拼接出来的SQL将会如下所示：

```sql
select * from t_customer where and username like concat('%',?, '%')
```

可以看出上面SQL语句明显存在SQL语法错误，**而加入了条件“1=1”后，既保证了where后面的条件成立，又避免了where后面第一个词是and或者or之类的关键词。不过“where 1=1”这种写法对于初学者来将不容易理解，并且也不够雅观。**

针对上述情况中“where 1=1”，在MyBatis的SQL中就可以使用< where >或< trim >元素进行动态处理。

**动态SQL处理**

```sql
select * from t_customer
      <where>
           <if test="username !=null and username !=''">
                 and username like concat('%',#{username}, '%')
           </if>
           <if test="jobs !=null and jobs !=''">
                 and jobs= #{jobs}
           </if>
      </where>

```

**< where >元素处理**

< where >会自动判断SQL语句，只有< where >内的条件成立时，才会在拼接SQL中加入where关键字，否则将不会添加；还会去除多余的“AND”或“OR”。

```sql
select * from t_customer
     <trim prefix="where" prefixOverrides="and">
            <if test="username !=null and username !=''">
                  and username like concat('%',#{username}, '%')
            </if>
            <if test="jobs !=null and jobs !=''">
                  and jobs= #{jobs}
            </if>
     </trim>
```

## 5. < set >元素  

> 在Hibernate中，想要更新某个对象，就需要发送所有的字段给持久化对象，这种想更新的每一条数据都要将其所有的属性都更新一遍的方法，其执行效率是非常差的。为此，在MyBatis中可以使用动态SQL中的< set >元素进行处理：

```sql
<!-- <set>元素 -->
    <update id="updateCustomer" parameterType="com.nateshao.po.Customer">
        update t_customer
        <set>
            <if test="username !=null and username !=''">
                username=#{username},
            </if>
            <if test="jobs !=null and jobs !=''">
                jobs=#{jobs},
            </if>
            <if test="phone !=null and phone !=''">
                phone=#{phone},
            </if>
        </set>
        where id=#{id}
    </update>
```

使用< set >和< if >元素对username和jobs进行更新判断，并动态组装SQL。这样就只需要传入想要更新的字段即可

代码实现：

![](https://gitee.com/nateshao/images/raw/master/img/20211025111315.png)



## 6. < foreach >元素 

假设如下需求：在一个客户表中有1000条数据，现在需要将id值小于100的客户信息全部查询出来，这要怎么做呢？

1. 一条一条的查询 ： 那如果要查询1000条数据呢,岂不是很累?
2. 在Java中用for循环查询 ： 考虑过N条查询语句时的查询效率了吗？

针对上述需求，理想的解决方法就是**使用MyBatis中动态SQL的< foreach >元素进行处理**。其基本使用示例如下所示：

```sql
<!--<foreach>元素使用 -->
    <select id="findCustomerByIds" parameterType="List"
            resultType="com.nateshao.po.Customer">
        select * from t_customer where id in
        <foreach item="id" index="index" collection="list" open="("
                 separator="," close=")">
            #{id}
        </foreach>
    </select>
```

   关于上述示例中< foreach >元素中使用的几种属性的描述具体如下：

- `item`：配置的是循环中当前的元素。
- `index`：配置的是当前元素在集合的位置下标。
- `collection`：配置的list是传递过来的参数类型（首字母小写），它可以是一个array、list（或collection）、Map集合的键、POJO包装类中数组或集合类型的属性名等。
- `open`和`close`：配置的是以什么符号将这些集合元素包装起来。
- `separator`：配置的是各个元素的间隔符。

在使用< foreach >时最关键也是最容易出错的就是collection属性，该属性是必须指定的，而且在不同情况下，该属性的值是不一样的。主要有以下3种情况：

1. 如果传入的是单参数且参数类型是一个数组或者List的时候，collection属性值分别为array和list（或collection）。
2. 如果传入的参数是多个的时候，就需要把它们封装成一个Map了，当然单参数也可以封装成Map集合，这时候collection属性值就为Map的键。
3. 如果传入的参数是POJO包装类的时候，collection属性值就为该包装类中需要进行遍历的数组或集合的属性名。



## 7. < bind >元素

在入门案例中模糊查询的SQL语句中？

```sql
select * from t_customer where username like '%${value}%'
```

上述SQL语句有什么不妥？

1. 如果使用“${}”进行字符串拼接，则无法防止SQL注入问题；
2. 如果改用concat函数进行拼接，则只针对MySQL数据库有效；
3. 如果改用“||”进行字符串拼接，则只针对Oracle数据库有效。

小提示：这样，映射文件中的SQL就要根据不同的情况提供不同形式的实现，这显然是比较麻烦的，且不利于项目的移植。为了减少这种麻烦，就可以使用MyBatis的< bind >元素来解决这一问题。

MyBatis的< bind >元素可以通过OGNL表达式来创建一个上下文变量，其使用方式如下：

```sql
<!--<bind>元素的使用：根据客户名模糊查询客户信息 -->
    <select id="findCustomerByName" parameterType="com.nateshao.po.Customer"
            resultType="com.nateshao.po.Customer">
        <!--_parameter.getUsername()也可直接写成传入的字段属性名，即username -->
        <bind name="pattern_username" value="'%'+_parameter.getUsername()+'%'"/>
        select * from t_customer
        where
        username like #{pattern_username}
    </select>
```

_parameter.getUsername()表示传递进来的参数（也可以直接写成对应的参数变量名，如username）

![](https://gitee.com/nateshao/images/raw/master/img/20211025111848.png)

## 总结

本章的学习，我们可以了解常用动态SQL元素的主要作用，并能够掌握这些元素在实际开发中如何使用。在MyBatis框架中，这些动态SQL元素的使用十分重要，熟练的掌握它们能够极大的提高开发效率。

