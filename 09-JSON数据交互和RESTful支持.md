---
create by 千羽 2021-10-12
---

[TOC]

> GitHub：https://github.com/nateshao/ssm/tree/master/109-springmvc-json-restful
>

<center>欢迎关注千羽的公众号</center>

![程序员千羽](https://gitee.com/nateshao/images/raw/master/img/20211021102040.jpg)





## 1. 拦截器概述  

**什么是拦截器？**

> ​    Spring MVC中的拦截器（Interceptor）类似于Servlet中的过滤器（Filter），它主要用于拦截用户请求并作相应的处理。例如通过拦截器可以进行权限验证、记录请求信息的日志、判断用户是否登录等。

​    要使用Spring MVC中的拦截器，就需要对拦截器类进行定义和配置。通常拦截器类可以通过两种方式来定义。

1. 第一种：通过实现HandlerInterceptor接口，或继承HandlerInterceptor接口的实现类（如HandlerInterceptorAdapter）来定义。
2.  第二种：通过实现WebRequestInterceptor接口，或继承WebRequestInterceptor接口的实现类来定义。

**以实现HandlerInterceptor接口方式**为例，自定义拦截器类的代码如下：

```java
public class CustomInterceptor implements HandlerInterceptor{
    
    //该方法会在控制器方法前执行，其返回值表示是否中断后续操作。当其返回值为true时，表示继续向下执行；当其返回值为false时，会中断后续的所有操作。

            public boolean preHandle(HttpServletRequest request, 
	HttpServletResponse response, Object handler)throws Exception {
		return false;
	}
    //该方法会在控制器方法调用之后，且解析视图之前执行。可以通过此方法对请求域中的模型和视图做出进一步的修改。

	public void postHandle(HttpServletRequest request, 
		HttpServletResponse response, Object handler, 
		ModelAndView modelAndView) throws Exception {
	}
    //该方法会在整个请求完成，即视图渲染结束之后执行。可以通过此方法实现一些资源清理、记录日志信息等工作。

	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response, Object handler, 
			Exception ex) throws Exception {		
          }
}
```

   要使自定义的拦截器类生效，还需要在Spring MVC的配置文件中进行配置。

```xml
    <mvc:interceptors>
        <bean class="com.itheima.interceptor.CustomInterceptor"/>//全局拦截器，拦截所有请求

       <mvc:interceptor>
            <mvc:mapping path="/**"/>///**配置，表示拦截所有路径

            <mvc:exclude-mapping path=""/>//配置不需要拦截的路径

            <bean class="com.itheima.interceptor.Interceptor1" /> 
       </mvc:interceptor>
       <mvc:interceptor>
            <mvc:mapping path="/hello"/> ///hello表示拦截所有以“/hello”结尾的路径

            <bean class="com.itheima.interceptor.Interceptor2" />	   
        </mvc:interceptor>
        ...
     </mvc:interceptors>
```

**注意**：< mvc:interceptor >中的子元素必须按照上述代码的配置顺序进行编写，否则文件会报错。









## 2. 拦截器的执行流程 





## 3. 应用案例

