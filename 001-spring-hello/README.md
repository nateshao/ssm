---
create by 千羽 2021-09-23
---

[TOC]

# 任务1：创建Spring项目

![任务一 ioc实现](https://gitee.com/nateshao/images/raw/master/img/20210923191859.png)

# 任务2：实现Spring的IoC

## User.java

```java
package com.nateshao.model;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:12
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class User {
    private String name;
    private int age;
    private char sex;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
}
```

## applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="nateshao" class="com.nateshao.model.User">
        <property name="name" value="千羽"/>
        <property name="age" value="18"/>
        <property name="sex" value="男"/>
    </bean>
</beans>
```

## App.java  启动类

```java
package com.nateshao;

import com.nateshao.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        ApplicationContext applicationContext = new 			ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) applicationContext.getBean("nateshao");
        System.out.println(user.toString());
    }
}
```

## UserTest.java 测试类

![](https://gitee.com/nateshao/images/raw/master/img/20210923192328.png)

## UserTest.java

```java
package com.nateshao.model;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:23
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class UserTest {
    @Test
    public void testUser() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) applicationContext.getBean("nateshao");
        System.out.println(user.toString());
        System.out.println("------------------------------------------------");
        user.setAge(19);
        user.setName("千羽的编程时光");
        System.out.println(user.toString());
    }

}
```

![](https://gitee.com/nateshao/images/raw/master/img/20210923192742.png)

测试结果

**小结：**

这里我们并没有手动创建`User`的实例（对象），是Spring通过`ApplicationContext`帮我们创建的放在`IoC`容器里。`ApplicationContext`是一个`IoC`容器接口，它所创建的对象都称作是`bean`，也就是xml文件里的<bean id=" " class=" "></bean>这行配置信息。`getBean`方法就是从`IoC`容器里取得这个对象（根据标识`id `和类名`class`），然后我们就可以调用该类的方法。

# 任务3：Spring注入

![](https://gitee.com/nateshao/images/raw/master/img/20210923200142.png)

## InjectionDao.java

```java
package com.nateshao.dao;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:37
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface InjectionDao {
    public void save(String arg);
}
```

## InjectionDaoImpl.java

```java
package com.nateshao.dao;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:37
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class InjectionDaoImpl implements InjectionDao {
    @Override
    public void save(String arg) {
        System.out.println("Save data to database:" + arg);
    }
}
```

## InjectionService.java

```java
package com.nateshao.service;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:39
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public interface InjectionService {
    public void save(String arg);
}
```

## InjectionServiceImpl.java

```java
package com.nateshao.service;

import com.nateshao.dao.InjectionDao;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:39
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class InjectionServiceImpl implements InjectionService {
    private InjectionDao injectionDao;

    public void setInjectionDao(InjectionDao injectionDao) {
        this.injectionDao = injectionDao;
    }

    @Override
    public void save(String arg) {
        System.out.println("Service accepts a parameter");
        arg = arg + ":" + this.hashCode();
        injectionDao.save(arg);
    }
}
```

## spring-inject.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--  设值注入  -->
    <bean id="injectionService01" class="com.nateshao.service.InjectionServiceImpl">
        <property name="InjectionDao" ref="InjectionDao"/>
    </bean>

    <bean name="InjectionDao" class="com.nateshao.dao.InjectionDaoImpl"></bean>

</beans>
```

## TestInjection.java 测试类

```java
package com.nateshao;

import com.nateshao.dao.InjectionDaoImpl;
import com.nateshao.service.InjectionServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date Created by 邵桐杰 on 2021/9/23 19:48
 * @微信公众号 千羽的编程时光
 * @个人网站 www.nateshao.cn
 * @博客 https://nateshao.gitee.io
 * @GitHub https://github.com/nateshao
 * @Gitee https://gitee.com/nateshao
 * Description:
 */
public class TestInjection {
    @Test
    public void testSetter() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-inject.xml");
        InjectionServiceImpl service = applicationContext.getBean("injectionService01", InjectionServiceImpl.class);
        service.save("this is save data");
    }
}
```

## 构造注入

![](https://gitee.com/nateshao/images/raw/master/img/20210923210917.png)























![](https://gitee.com/nateshao/images/raw/master/img/20210923185753.png)

