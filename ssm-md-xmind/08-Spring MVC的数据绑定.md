---
create by 千羽 2021-10-12
---

[TOC]

![](http://mmbiz.qpic.cn/sz_mmbiz_jpg/icHiblwB4HdHyCZQw1PfUuyYBtc0V0CyhKrDB5O3xzhNKb9UBVQL2eZ2rJwLAv7Uqb9lxicMZojCu7DK60gTQg0HQ/0?wx_fmt=jpeg)

> GitHub：https://github.com/nateshao/ssm/tree/master/108-springmvc-databind
>
> Gitee：https://gitee.com/nateshao/ssm/tree/master/108-springmvc-databind

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)

![](https://gitee.com/nateshao/images/raw/master/img/20211017104238.png)

![](https://gitee.com/nateshao/images/raw/master/img/20211017104309.png)

## 1. 数据绑定介绍 

**什么是数据绑定？**

> 在执行程序时，Spring MVC会根据客户端请求参数的不同，将请求消息中的信息以一定的方式转换并绑定到控制器类的方法参数中。这种将请求消息数据与后台方法参数建立连接的过程就是Spring MVC中的数据绑定。

**Spring MVC是怎样完成的数据绑定？**

> 在数据绑定过程中，Spring MVC框架会通过数据绑定组件（DataBinder）将请求参数串的内容进行类型转换，然后将转换后的值赋给控制器类中方法的形参，这样后台方法就可以正确绑定并获取客户端请求携带的参数了。接下来，将通过一张数据流程图来介绍数据绑定的过程。

![](https://gitee.com/nateshao/images/raw/master/img/20211017122449.png)



1. Spring MVC将ServletRequest对象传递给DataBinder;
2. 将处理方法的入参对象传递给DataBinder;
3. DataBinder调用ConversionService组件进行数据类型转换、数据格式化等工作，并将ServletRequest对象中的消息填充到参数对象中;
4. 调用Validator组件对已经绑定了请求消息数据的参数对象进行数据合法性校验;
5. 校验完成后会生成数据绑定结果BindingResult对象，Spring MVC会将BindingResult对象中的内容赋给处理方法的相应参数。

## 2.简单数据绑定 

**数据绑定分类：**

​     根据客户端请求参数类型和个数的不同，我们将Spring MVC中的数据绑定主要分为**简单数据绑定和复杂数据绑定**，下面对这两种类型数据绑定进行详细讲解。

**常用默认参数类型**

- HttpServletRequest：通过request对象获取请求信息；
- HttpServletResponse：通过response处理响应信息；
- HttpSession：通过session对象得到session中存放的对象；
- Model/ModelMap：Model是一个接口，ModelMap是一个接口实现，作用是将model数据填充到request域。

**演示默认数据类型绑定的使用：**

1. 创建108-springmvc-databind项目，并导入相关maven包;
2. 在web.xm中配置Spring MVC的前端控制器等信息;
3. 创建Spring MVC配置文件，并配置组件扫描器和视图解析器;
4. 创建处理器类。
5. 创建访问成功后的响应页面。

![项目结构图](https://gitee.com/nateshao/images/raw/master/img/20211017170420.png)

具体可以访问：https://github.com/nateshao/ssm/tree/master/108-springmvc-databind

**绑定简单数据类型**

> 简单数据类型的绑定，就是指Java中几种基本数据类型的绑定，例如int、String、Double等类型。这里仍然以上一小节中的参数id为1的请求为例，来讲解简单数据类型的绑定。

将控制器类UserController中的selectUser()方法进行修改：

```java
@RequestMapping("/selectUser")
public String selectUser(Integer id) { // 将默认参数类型HttpServletRequest改用基本类型Integer绑定接收
    System.out.println("id="+id);
    return "success";
}
```

这里需要注意的是，有时候前端请求中参数名和后台控制器类方法中的形参名不一样，这就会导致后台无法正确绑定并接收到前端请求的参数。

如何处理这种情况的请求呢？

> 针对上述提到的前端请求中参数名和后台控制器类方法中的形参名不一样的情况，**可以考虑使用Spring MVC提供的@RequestParam注解类型来进行间接数据绑定。**

**@RequestParam注解的属性声明如下：**

![](https://gitee.com/nateshao/images/raw/master/img/20211017193603.png)

​    假设请求地址为http://localhost:8080/108_springmvc_databind_war_exploded/selectUser?user_id=1，那么在后台selectUser()方法中的使用方式如下：

```java
@RequestMapping("/selectUser")
// 先用@RequestParam接收同名参数，后间接绑定到方法形参上
public String selectUser(@RequestParam(value="user_id")Integer id) {
     System.out.println("id="+id);
     return "success";
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211017195909.png)



**绑定POJO类型**

> 在使用简单数据类型绑定时，可以很容易的根据具体需求来定义方法中的形参类型和个数，然而在实际应用中，客户端请求可能会传递多个不同类型的参数数据，如果还使用简单数据类型进行绑定，那么就需要手动编写多个不同类型的参数，这种操作显然比较繁琐。

针对多类型、多参数的请求，可以使用POJO类型进行数据绑定。

POJO类型的数据绑定就是将所有关联的请求参数封装在一个POJO中，然后在方法中直接使用该POJO作为形参来完成数据绑定。

**通过一个用户注册案例，来演示POJO类型数据的绑定的使用：**

1. 创建用户类POJO,来封装用户注册信息;
2. 在控制器中编写注册方法;
3. 创建用户注册页面;
4. 启动Web项目，访问http://localhost:8080/108_springmvc_databind_war_exploded/toRegister;
5. 注册页面填写信息，并单击“注册”按钮。

**User.java**

```java
public class User {
    private Integer id;       //用户id
    private String username; //用户
    private Integer password;//用户密码
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
    public Integer getPassword() {
        return password;
    }
    public void setPassword(Integer password) {
        this.password = password;
    }

}
```

**UserController.java**

```java
	/**
     * 接收用户注册信息
     */
    @RequestMapping("/registerUser")
    public String registerUser(User user) {
        String username = user.getUsername();
        Integer password = user.getPassword();
        System.out.println("username=" + username);
        System.out.println("password=" + password);
        return "success";
    }
```

**register.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/registerUser" 
                                                                    method="post">
		用户名：<input type="text" name="username" /><br />
		密&nbsp;&nbsp;&nbsp;码：<input type="text" name="password" /><br />
		<input type="submit" value="注册"/>
	</form>
</body>
</html>
```

![](https://gitee.com/nateshao/images/raw/master/img/20211017201829.png)

​    *为了防止前端传入的中文数据出现乱码问题，我们可以在web.xml中配置Spring提供的编码过滤器来统一编码。*

**解决请求参数中的中文乱码问题**

```xml
<filter>
        <filter-name>CharacterEncodingFilter</filter-name>		
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
                   <param-name>encoding</param-name>
           		 	<!-- 设置为统一UTF-8格式编码 -->
                   <param-value>UTF-8</param-value>
        </init-param>
</filter>
<filter-mapping>
       <filter-name>CharacterEncodingFilter</filter-name>
    	<!-- 拦截所有URL请求，交由编码过滤器 -->
       <url-pattern>/*</url-pattern>
</filter-mapping>
```

假设如下需求：

​     在用户查询订单时，页面传递的参数可能包括：订单编号、用户名称等信息，这就包含了订单和用户两个对象的信息，此时后台方法如何绑定请求信息呢？

1. 使用POJO类型绑定 ( 方法可用，但订单和用户信息混合封装，显得比较混乱 )
2. 还可以考虑使用包装POJO类型绑定

​     所谓的包装POJO，就是在一个POJO中包含另一个简单POJO。例如，在订单对象中包含用户对象。这样在使用时，就可以通过订单查询到用户信息。





下面通过一个订单查询的案例，来演示包装POJO数据绑定的使用:

1. 创建订单包装POJO,来封装订单和用户信息;
2. 创建订单控制器类，在控制器中编写查询订单信息方法;
3. 创建订单查询页面;
4. 启动Web项目，访问http://localhost:8080/108_springmvc_databind_war_exploded/tofindOrdersWithUser;
5. 查询页面填写查询信息。

**Orders.java**

```java
public class Orders {
    private Integer ordersId; // 订单编号
    private User user;          // 用户POJO，所属用户
    public Integer getOrdersId() {
        return ordersId;
    }
    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
```

**OrdersController.java**

```java
	/**
     * 查询订单和用户信息
     *
     * @param orders
     * @return
     */
    @RequestMapping("/findOrdersWithUser")
    public String findOrdersWithUser(Orders orders) {
//        Orders orders1 = new Orders();
//        orders1.setOrdersId(1);
//        orders1.setUser(new User());
        Integer ordersId = orders.getOrdersId();
        User user = orders.getUser();
        String username = user.getUsername();
        System.out.println("orderId=" + ordersId);
        System.out.println("username=" + username);
        return "success";
    }
}
```

**orders.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单查询</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/findOrdersWithUser"
		                                                           method="post">
				<%--  参数是包装类基本属性，则直接用属性名  --%>
		订单编号：<input type="text" name="ordersId" /><br />
				<%-- 参数是包装类中POJO类的子属性，则必须用【对象.属性】--%>
		所属用户：<input type="text" name="user.username" /><br /> 
		          <input type="submit" value="查询" />
	</form>
</body>
</html>
```

![](https://gitee.com/nateshao/images/raw/master/img/20211017204510.png)

## 自定义数据绑定

> 一般情况下，使用基本数据类型和POJO类型的参数数据已经能够满足需求，然而有些特殊类型的参数是无法在后台进行直接转换的，但也有特殊数据类型无法直接进行数据绑定，必须先经过数据转换，例如日期数据。

  如何处理这种数据类型的请求呢？

![](https://gitee.com/nateshao/images/raw/master/img/20211017204759.png)

## 3.复杂数据绑定

**复杂数据绑定情形**

数组的绑定、集合的绑定，这在实际开发中也是十分常见的。

### 绑定数组

  在实际开发时，可能会遇到前端请求需要传递到后台一个或多个相同名称参数的情况（如批量删除），此种情况采用前面讲解的简单数据绑定的方式显然是不合适的。

**如何处理这种数据类型的请求呢？**

​    针对上述这种情况，如果将所有同种类型的请求参数封装到一个数组中，后台就可以进行绑定接收了。

**以一个批量删除用户的例子来详细讲解绑定数组的操作使用:**

1. 创建一个用户信息列表页面;
2. 在处理器类中编写批量删除用户的方法;
3. 启动项目，访问：http://ocalhost:8080/108_springmvc_databind_war_exploded/toUser;
4. 勾选全部复选框，批量删除用户。

**user.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/deleteUsers"
		   method="post">
		<table width="20%" border=1>
			<tr>
				<td>选择</td>
				<td>用户名</td>
			</tr>
			<tr>
				<td><input name="ids" value="1" type="checkbox"></td>
				<td>tom</td>
			</tr>
			<tr>
				<td><input name="ids" value="2" type="checkbox"></td>
				<td>jack</td>
			</tr>
			<tr>
				<td><input name="ids" value="3" type="checkbox"></td>
				<td>lucy</td>
			</tr>
		</table>
		<input type="submit" value="删除"/>
	</form>
</body>
</html>
```

**UserController.java**

```java
	/**
     * 接收批量删除用户的方法
     */
    @RequestMapping("/deleteUsers")
    public String deleteUsers(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                // 使用输出语句模拟已经删除了用户
                System.out.println("删除了id为" + id + "的用户！");
            }
        } else {
            System.out.println("ids = null");
        }
        return "success";
    }
```

![](https://gitee.com/nateshao/images/raw/master/img/20211017205542.png)

![](https://gitee.com/nateshao/images/raw/master/img/20211017205647.png)



​     在批量删除用户的操作中，前端请求传递的都是同名参数的用户id，只要在后台使用同一种数组类型的参数绑定接收，就可以在方法中通过循环数组参数的方式来完成删除操作。

​     但如果是批量修改用户操作的话，前端请求传递过来的数据可能就会批量包含各种类型的数据，如Integer，String等。

**如何处理这种数据类型的请求呢？**

​    针对上述这种情况，就可以**使用集合数据绑定**。即**在包装类中定义一个包含用户信息类的集合**，然后在接收方法中将参数类型定义为该包装类的集合。

**绑定集合**

1. 创建包装类POJO,
2. 封装用户集合信息;
3. 在处理器类中编写批量修改用户的方法;
4. 编写用户批量修改页面;
5. 启动项目，访问：http://localhost:8080/108_springmvc_databind_war_exploded/toUserEdit;
6. 勾选全部复选框，批量修改用户

**UserVO.java**

```java
public class UserVO {
    private List<User> users;
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
```

**UserController.java**

```java
	/**
     * 接收批量修改用户的方法
     */
    @RequestMapping("/editUsers")
// 在使用集合数据绑定时，后台方法中不支持直接使用集合形参进行数据绑定，所以市需要使用包装POJO作为形参
    public String editUsers(UserVO userList) {
        // 将所有用户数据封装到集合中
        List<User> users = userList.getUsers();
        // 循环输出所有用户信息
        for (User user : users) {
            // 如果接收的用户id不为空，则表示对该用户进行了修改
            if (user.getId() != null) {
                System.out.println("修改了id为" + user.getId() +
                        "的用户名为：" + user.getUsername());
            }
        }
        return "success";
    }
```

**user_edit.jsp**

```jsp
<%--
  Created by IntelliJ IDEA.
  User: 邵桐杰
  Date: 2021/10/17
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改用户</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/editUsers"
      method="post" id='formid'>
    <table width="30%" border=1>
        <tr>
            <td>选择</td>
            <td>用户名</td>
        </tr>
        <tr>
            <td>
                <input name="users[0].id" value="1" type="checkbox" />
            </td>
            <td>
                <input name="users[0].username" value="tome" type="text" />
            </td>
        </tr>
        <tr>
            <td>
                <input name="users[1].id" value="2" type="checkbox" />
            </td>
            <td>
                <input name="users[1].username" value="jack" type="text" />
            </td>
        </tr>
    </table>
    <input type="submit" value="修改" />
</form>
</body>
</html>
```

![](https://gitee.com/nateshao/images/raw/master/img/20211017211237.png)

![](https://gitee.com/nateshao/images/raw/master/img/20211017211241.png)

## 小结

主要对Spring MVC中的数据绑定进行了详细讲解。

1. 讲解了简单的数据绑定，包括默认数据类型、简单数据类型、POJO类型、包装POJO类型以及自定义参数类型绑定；
2. 讲解了复杂数据绑定，包括数组类型、集合类型绑定。











