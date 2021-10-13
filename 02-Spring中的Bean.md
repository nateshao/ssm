---
create by 千羽 2021-10-12
---

[TOC]

![](https://gitee.com/nateshao/images/raw/master/img/20211013221818.png)

> https://gitee.com/nateshao/ssm/tree/master/100-spring-hello

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



## 4. Bean的生命周期



## 5. Bean的装配方式







