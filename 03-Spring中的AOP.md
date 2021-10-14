---
create by 千羽 2021-10-12
---

[TOC]

![](https://gitee.com/nateshao/images/raw/master/img/20211014104629.png)

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014104904.png" style="zoom:80%;" />

> GitHub：https://github.com/nateshao/ssm/tree/master/102-spring-bean
>
> Gitee：https://gitee.com/nateshao/ssm/tree/master/102-spring-bean

## 1. Bean的配置

**什么是Spring中的Bean？**

> 如果把Spring看做一个大型工厂，则Spring容器中的Bean就是该工厂的产品。要想使用这个工厂生产和管理Bean，就需要在配置文件中告诉它需要哪些Bean，以及需要使用何种方式将这些Bean装配到一起。

**小提示：** Bean的本质就是Java中的类，而Spring中的Bean其实就是对实体类的引用，来生产Java类对象，从而实现生产和管理Bean 。

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013222640.png" style="zoom: 50%;" />

  *在实际开发中，最常使用的是XML文件格式的配置方式，这种配置方式是通过XML文件来注册并管理Bean之间的依赖关系。*

​    XML配置文件的根元素是< beans >，< beans >中包含了多个< bean >子元素，每一个< bean >子元素定义了一个Bean，并描述了该Bean如何被装配到Spring容器中。关于< beans >元素的常用属性如下表所示：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211013222816.png" style="zoom:80%;" />

​    在配置文件中，通常一个普通的Bean只需要定义id（或name）和class 两个属性即可，定义Bean的方式如下所示：

```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
	http://www.springframework.org/schema/beans/spring-beans.xsd">
            <bean id="bean1" class="com.nateshao.Bean1" />
            <bean name="bean2" class="com.nateshao.Bean2" />
    </beans>
```

**小提示：**如果在Bean中未指定id和name，则Spring会将class值当作id使用。

## 2. Bean的实例化

**Bean的实例化有哪些方式？**

>   在面向对象的程序中，想要使用某个对象，就需要先实例化这个对象。同样，在Spring中，要想使用容器中的Bean，也需要实例化Bean。实例化Bean有三种方式，分别为**构造器实例化、静态工厂方式实例化和实例工厂方式实例化（其中最常用的是构造器实例化）。** 

### 构造器实例化

> 构造器实例化是指Spring容器通过Bean对应的类中默认的构造函数来实例化Bean。接下来演示构造器实例化的使用:

1. 创建Web项目，导入相关maven包;
2. 创建名为Beanl的Java类;
3. 创建Spring配置文件beans 1.xml，并配置Beanl实体类Bean;

Bean1.java

```java
public class Bean1 {
}
```

   InstanceTest1.java

```java
public class InstanceTest1 {
       public static void main(String[] args) {
     	String xmlPath = "beans1.xml";
               ApplicationContext applicationContext = 
			    new ClassPathXmlApplicationContext(xmlPath);
	Bean1 bean = (Bean1) applicationContext.getBean("bean1");
                System.out.println(bean);
      }
}
```

beans1.xml


```java
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
 	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 	xsi:schemaLocation="http://www.springframework.org/schema/beans 
  	http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
           <bean id="bean1" class="com.nateshao.instance.constructor.Bean1" />
    </beans>

```

![](https://gitee.com/nateshao/images/raw/master/img/20211013225106.png)

### 静态工厂方式实例化

>   静态工厂是实例化Bean的另一种方式。该方式要求自己创建一个静态工厂的方法来创建Bean的实例。接下来演示静态工厂实例化的使用：

1. 创建名为Bean2的Java类;
2. 创建一个Java工厂类，在类中使用静态方法获取Bean2实例;
3. 创建Spring配置文件beans2.xml,并配置工厂类Bean;

Bean2.java

```java
public class Bean2 {

}
```

MyBean2Factory.java

```java
package com.nateshao.instance.static_factory;

/**
 * @date Created by 邵桐杰 on 2021/10/13 22:53
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class MyBean2Factory {
    //使用自己的工厂创建Bean2实例
    public static Bean2 createBean(){
        return new Bean2();
    }
}
```

beans2.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
    <bean id="bean2" 
            class="com.nateshao.instance.static_factory.MyBean2Factory"
		   factory-method="createBean" />
</beans>
```

![](https://gitee.com/nateshao/images/raw/master/img/20211013225804.png)

### 实例工厂方式实例化

> ​     实例工厂是采用直接创建Bean实例的方式，在配置文件中，通过factory-bean属性配置一个实例工厂，然后使用factory-method属性确定使用工厂中的哪个方法。接下来演示实例工厂实例化的使用：

1. 创建名为Bean3的Java类;
2. 创建一个Java工厂类，在类中使用非静态方法获取Bean3实例; 
3. 创建Spring配置文件beans3.xml，并配置工厂类Bean;
4. 创建测试类，测试程序。

Bean3.java

```java
public class Bean3 {
}
```

MyBean3Factory.java

```java
package com.nateshao.instance.factory;

/**
 * @date Created by 邵桐杰 on 2021/10/13 23:00
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class MyBean3Factory {
    public MyBean3Factory() {
        System.out.println("bean3工厂实例化中");
    }
    //创建Bean3实例的方法
    public Bean3 createBean(){
        return new Bean3();
    }
}
```

beans3.java

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
    <!-- 配置工厂 -->
    <bean id="myBean3Factory" 
            class="com.nateshao.instance.factory.MyBean3Factory" />
    <!-- 使用factory-bean属性指向配置的实例工厂，
          使用factory-method属性确定使用工厂中的哪个方法-->
	<bean id="bean3" factory-bean="myBean3Factory" 
		   factory-method="createBean" />
</beans>
```

InstanceTest3.java

```java
package com.nateshao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/13 23:05
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class InstanceTest3 {
    public static void main(String[] args) {
        // 定义配置文件路径
        String xmlPath = "beans3.xml";
        // ApplicationContext在加载配置文件时，对Bean进行实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        System.out.println(applicationContext.getBean("bean3"));
    }
}
```

运行结果![](https://gitee.com/nateshao/images/raw/master/img/20211013232058.png)

## 3. Bean的作用域

**作用域的种类**

​     Spring 4.3中为Bean的实例定义了7种作用域，如下表所示：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014080458.png" style="zoom:80%;" />

**注意：**在上表7种作用域中，`singleton`和`prototype`是最常用的两种作用域。

### singleton作用域

​    `singleton`是`Spring`容器默认的作用域，当`Bean`的作用域为`singleton`时，`Spring`容器就只会存在一个共享的`Bean`实例。`singleton`作用域对于无会话状态的`Bean`（如Dao 组件、Service组件）来说，是最理想的选择。

​    在`Spring`配置文件中，可以使用< bean >元素的`scope`属性，将`Bean`的作用域定义成`singleton`。

 **在Spring中如何配置singleton作用域?**

比如

```xml
<bean id="scope" class="com.nateshao.scope.Scope" scope="singleton"/>
```

### prototype作用域

​    对需要保持会话状态的Bean（如Struts 2的Action类）应该使用prototype作用域。在使用prototype作用域时，Spring容器会为每个对该Bean的请求都创建一个新的实例。

​    在Spring配置文件中，同样使用< bean>元素的scope属性，将Bean的作用域定义成prototype 。

**在Spring中如何配置prototype作用域?**

比如

```xml
<bean id="scope" class="com.nateshao.scope.Scope" scope=" prototype "/>
```

代码实例4：ScopeTest

![](https://gitee.com/nateshao/images/raw/master/img/20211014094608.png)

## 4. Bean的生命周期

**了解Spring中Bean的生命周期有何意义？**

>    了解Spring中Bean的生命周期的意义就在于，可以利用Bean在其存活期间的特定时刻完成一些相关操作。这种时刻可能有很多，但一般情况下，常会在Bean的postinitiation(初始化后)和predestruction（销毁前）执行一些相关操作。

​    Spring容器可以管理Bean部分作用域的生命周期。有关说明具体如下：

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014082154.png" style="zoom:80%;" />

Spring容器中Bean的生命周期流程如下图所示；

![](https://gitee.com/nateshao/images/raw/master/img/20211014082404.png)

## 5. Bean的装配方式

**什么是Bean的装配？**

> Bean的装配可以理解为依赖关系注入，Bean的装配方式即Bean依赖注入的方式。Spring容器支持多种形式的Bean的装配方式，如基于XML的装配、基于注解（Annotation）的装配和自动装配（其中**最常用的是基于注解的装配**），本节将主要讲解这三种装配方式的使用。

### 基于XML的装配

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014082618.png" style="zoom:80%;" />

基于XML的装配，使用方式如下：

1. 创建Java类，提供有参、无参构造以及属性setter方法;
2. 创建Spring配置文件beans5.xml，使用2种方式配置Bean; .
3. 创建测试类，测试程序。

User.java

```java
package com.nateshao.assemble;

import java.util.List;

/**
 * @date Created by 邵桐杰 on 2021/10/14 9:24
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class User {
    private String username;
    private int password;
    private List<String> list;
    /**
     * 1.使用构造注入
     * 1.1提供带所有参数的有参构造方法。
     */
    public User(String username, Integer password, List<String> list) {
        super();
        this.username = username;
        this.password = password;
        this.list = list;
    }
    /**
     * 2.使用设值注入
     * 2.1提供默认空参构造方法 ;
     * 2.2为所有属性提供setter方法。
     */
    public User() {
        super();
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(Integer password) {
        this.password = password;
    }
    public void setList(List<String> list) {
        this.list = list;
    }
    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password +
                ", list=" + list + "]";
    }
}
```

beans5.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
 	http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
	<!--1.使用构造注入方式装配User实例 -->
	<bean id="user1" class="com.nateshao.assemble.User">
		<constructor-arg index="0" value="tom" />
		<constructor-arg index="1" value="123456" />
		<constructor-arg index="2">
			<list>
				<value>"constructorvalue1"</value>
				<value>"constructorvalue2"</value>
			</list>
		</constructor-arg>
	</bean>
	<!--2.使用设值注入方式装配User实例 -->
	<bean id="user2" class="com.nateshao.assemble.User">
		<property name="username" value="张三"></property>
		<property name="password" value="654321"></property>
		<!-- 注入list集合 -->
		<property name="list">
			<list>
				<value>"setlistvalue1"</value>
				<value>"setlistvalue2"</value>
			</list>
		</property>
	</bean>
</beans>
```

XmlBeanAssembleTest.java

```java
package com.nateshao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/14 9:27
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class XmlBeanAssembleTest {
    public static void main(String[] args) {
        // 定义配置文件路径
        String xmlPath = "beans5.xml";
        // ApplicationContext在加载配置文件时，对Bean进行实例化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        System.out.println(applicationContext.getBean("user1"));
        System.out.println(applicationContext.getBean("user2"));

    }
}
```

运行结果：

![](https://gitee.com/nateshao/images/raw/master/img/20211014094655.png)



### 基于Annotation的装配

> 基于XML的装配可能会导致XML配置文件过于臃肿，给后续的维护和升级带来一定的困难。为此，Spring提供了对Annotation（注解）技术的全面支持。

**主要注解**

1. **@Component**：用于描述Spring中的Bean，它是一个泛化的概念，仅仅表示一个组件。
2. **@Repository**：用于将数据访问层（DAO）的类标识为Spring中的Bean 。
3. **@Service**：用于将业务层（Service）的类标识为Spring中的Bean。
4. **@Controller**：用于将控制层（Controller）的类标识为Spring中的Bean 。
5. **@Autowired**：用于对Bean的属性变量、属性的setter方法及构造方法进行标注，配合对应的注解处理器完成Bean的自动配置工作。
6. **@Resource**：其作用与Autowired一样。@Resource中有两个重要属性：name和type。Spring将name属性解析为Bean实例名称，type属性解析为Bean实例类型。
7. **@Qualifier**：与@Autowired注解配合使用，会将默认的按Bean类型装配修改为按Bean的实例名称装配，Bean的实例名称由@Qualifier注解的参数指定。

**基于Annotation装配的使用方式如下：**

1. 创建接口UserDao,并定义方法；
2. 创建接口实现类UserDaoImpl,用@Repository 声明类; 
3. 创建接口UserService,并定义方法;
4. 创建接口实现类UserServiceImpl,用@Service声明类，
5. 并使用@Resource注入UserDao属性;
6. 创建控制器类，用@Controller声明，并使用@Resource
7. 注入UserService属性;
8. 创建Spring配置文件，配置Bean; 
9. 创建测试类，测试程序。

**UserDao.java**

```java
public interface UserDao {
    public void save();
}
```

**UserDaoImpl.java**

```java
package com.nateshao.annotation;

import org.springframework.stereotype.Repository;

/**
 * @date Created by 邵桐杰 on 2021/10/14 10:12
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Repository
public class UserDaoImpl implements UserDao{
    @Override
    public void save() {
        System.out.println("userdao...save...");
    }
}
```

**UserService.java**

```java
public interface UserService {
    public void save();
}
```

**UserServiceImpl.java**

```java
package com.nateshao.annotation;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
/**
 * @date Created by 邵桐杰 on 2021/10/14 10:14
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    @Resource(name="userDao")
    private UserDao userDao;

    @Override
    public void save() {
        //调用userDao中的save方法
        this.userDao.save();
        System.out.println("userservice....save...");
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

**UserController.java**

```java
package com.nateshao.annotation;

import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
/**
 * @date Created by 邵桐杰 on 2021/10/14 10:17
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
@Controller("userController")
public class UserController {
    
    @Resource(name="userService")
    private UserService userService;
    
    public void save(){
        this.userService.save();
        System.out.println("userController...save...");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
```

**beans.xml**

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
      xmlns:context="http://www.springframework.org/schema/context"
      xsi:schemaLocation="http://www.springframework.org/schema/beans 
      http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
      http://www.springframework.org/schema/context 
  http://www.springframework.org/schema/context/spring-context-4.3.xsd">
 <!-- 使用 context 命名空间 ,在配置文件中开启相应的注解处理器 -->
	  <context:annotation-config />
	 <!--分别定义3个Bean实例  -->
       <bean id="userDao" class="com.nateshao.annotation.UserDaoImpl" />
      <bean id="userService" class="com.nateshao.annotation.UserServiceImpl" />
      <bean id="userController" class="com.nateshao.annotation.UserController" />
</beans>
```

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014103241.png" style="zoom:80%;" />

**AnnotationAssembleTest.java**

```java
package com.nateshao.test;

import com.nateshao.annotation.UserController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/10/14 10:20
 * @微信公众号 程序员千羽
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class AnnotationAssembleTest {
    public static void main(String[] args) {
        // 定义配置文件路径
        String xmlPath = "beans6.xml";
        // 加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 获取UserController实例
        UserController userController = (UserController) applicationContext.getBean("userController");
        // 调用UserController中的save()方法
        userController.save();
    }
}
```

![](https://gitee.com/nateshao/images/raw/master/img/20211014102456.png)

**小提示：** 除了可以像示例中通过< bean >元素来配置Bean外，还可以通过包扫描的形式来配置一个包下的所有Bean：

```xml
<context:component-scan base-package="com.nateshao.annotation" />
```

###      自动装配

>  所谓自动装配，就是将一个Bean自动的注入到到其他Bean的Property中。 Spring的<bean>元素中包含一个autowire属性，我们可以通过设置autowire的属性值来自动装配Bean。autowire属性有5个值，其值及说明下表所示：

![](https://gitee.com/nateshao/images/raw/master/img/20211014102707.png)

自动装配，使用方式如下：

1. 修改上一节UserServiceImple和UserController,分别增加类属性的setter方法;
2. 修改Spring配置文件，使用autowire属性配置Bean;
3. 重新测试程序。

```xml
<bean id="userDao" class="com.nateshao.annotation.UserDaoImpl" />
<bean id="userService" class="com.nateshao.annotation.UserServiceImpl" autowire="byName" />
<bean id="userController" class="com.nateshao.annotation.UserController" autowire="byName" />
```

<img src="https://gitee.com/nateshao/images/raw/master/img/20211014103354.png" style="zoom:80%;" />



































