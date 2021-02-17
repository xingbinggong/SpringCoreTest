# Spring

## IOC

**IOC（控制反转）是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制翻转的是IOC容器，其实现方法是依赖注入（Dependency Injection）**
**DI（依赖注入）只是IOC的一种实现方式**

## Bean元素的常用属性

| 属性名称        | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| id              | 是一个 Bean 的唯一标识符，Spring 容器对 Bean 的配置和管理都通过该属性完成 |
| name            | Spring 容器同样可以通过此属性对容器中的 Bean 进行配置和管理，name 属性中可以为 Bean 指定多个名称，每个名称之间用逗号或分号隔开 |
| class           | 该属性指定了 Bean 的具体实现类，它必须是一个完整的类名，使用类的全限定名 |
| scope           | 用于设定 Bean 实例的作用域，其属性值有 singleton（单例）、prototype（原型）、request、session 和 global Session。其默认值是 singleton |
| constructor-arg | <bean>元素的子元素，可以使用此元素传入构造参数进行实例化。该元素的 index 属性指定构造参数的序号（从 0 开始），type 属性指定构造参数的类型 |
| property        | <bean>元素的子元素，用于调用 Bean 实例中的 Set 方法完成属性赋值，从而完成依赖注入。该元素的 name 属性指定 Bean 实例中的相应属性名 |
| ref             | <property> 和 <constructor-arg> 等元素的子元索，该元素中的 bean 属性用于指定对 Bean 工厂中某个 Bean 实例的引用 |
| value           | <property> 和 <constractor-arg> 等元素的子元素，用于直接指定一个常量值 |
| list            | 用于封装 List 或数组类型的依赖注入                           |
| set             | 用于封装 Set 类型属性的依赖注入                              |
| map             | 用于封装 Map 类型属性的依赖注入                              |
| entry           | <map> 元素的子元素，用于设置一个键值对。其 key 属性指定字符串类型的键值，ref 或 value 子元素指定其值 |

Spring Bean的实例化分为：

- 构造器实例化

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <bean id="person1" class="com.mengma.instance.constructor.Person1" />
</beans>
```



- 静态工厂方法实例化

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <bean id="person2" class="com.mengma.instance.static_factory.MyBeanFactory"
        factory-method="createBean" />
</beans>
```



- 实例工厂方法实例化

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.2.xsd">
    <!-- 配置实例工厂 -->
    <bean id="myBeanFactory" class="com.mengma.instance.factory.MyBeanFactory" />
    <!-- factory-bean属性指定一个实例工厂，factory-method属性确定使用工厂中的哪个方法 -->
    <bean id="person3" factory-bean="myBeanFactory" factory-method="createBean" />
</beans>
```



构造器注入

set注入 1.4.2章节

```xml
<bean id="moreComplexObject" class="example.ComplexObject">
    <!-- results in a setAdminEmails(java.util.Properties) call -->
    <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@example.org</prop>
            <prop key="support">support@example.org</prop>
            <prop key="development">development@example.org</prop>
        </props>
    </property>
    <!-- results in a setSomeList(java.util.List) call -->
    <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
    </property>
    <!-- results in a setSomeMap(java.util.Map) call -->
    <property name="someMap">
        <map>
            <entry key="an entry" value="just some string"/>
            <entry key ="a ref" value-ref="myDataSource"/>
        </map>
    </property>
    <!-- results in a setSomeSet(java.util.Set) call -->
    <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
    </property>
</bean>
```



## bean的作用域

| Scope                                                        | Description                                                  |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [singleton](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-singleton) | (Default) Scopes a single bean definition to a single object instance for each Spring IoC container. |
| [prototype](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-prototype) | Scopes a single bean definition to any number of object instances. |
| [request](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-request) | Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring `ApplicationContext`. |
| [session](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-session) | Scopes a single bean definition to the lifecycle of an HTTP `Session`. Only valid in the context of a web-aware Spring `ApplicationContext`. |
| [application](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-application) | Scopes a single bean definition to the lifecycle of a `ServletContext`. Only valid in the context of a web-aware Spring `ApplicationContext`. |
| [websocket](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-websocket-scope) | Scopes a single bean definition to the lifecycle of a `WebSocket`. Only valid in the context of a web-aware Spring `ApplicationContext`. |

1、单例模式

2、原型模式

345只能在web开发中才能使用。



## Spring Bean的生命周期

![Bean的生命周期](http://c.biancheng.net/uploads/allimg/190701/5-1ZF1100325116.png)

Bean 生命周期的整个执行过程描述如下。

1）根据配置情况调用 Bean 构造方法或工厂方法实例化 Bean。

2）利用依赖注入完成 Bean 中所有属性值的配置注入。

3）如果 Bean 实现了 BeanNameAware 接口，则 Spring 调用 Bean 的 setBeanName() 方法传入当前 Bean 的 id 值。

4）如果 Bean 实现了 BeanFactoryAware 接口，则 Spring 调用 setBeanFactory() 方法传入当前工厂实例的引用。

5）如果 Bean 实现了 ApplicationContextAware 接口，则 Spring 调用 setApplicationContext() 方法传入当前 ApplicationContext 实例的引用。

6）如果 BeanPostProcessor 和 Bean 关联，则 Spring 将调用该接口的预初始化方法 postProcessBeforeInitialzation() 对 Bean 进行加工操作，此处非常重要，Spring 的 AOP 就是利用它实现的。

7）如果 Bean 实现了 InitializingBean 接口，则 Spring 将调用 afterPropertiesSet() 方法。

8）如果在配置文件中通过 init-method 属性指定了初始化方法，则调用该初始化方法。

9）如果 BeanPostProcessor 和 Bean 关联，则 Spring 将调用该接口的初始化方法 postProcessAfterInitialization()。此时，Bean 已经可以被应用系统使用了。

10）如果在 <bean> 中指定了该 Bean 的作用范围为 scope="singleton"，则将该 Bean 放入 Spring IoC 的缓存池中，将触发 Spring 对该 Bean 的生命周期管理；如果在 <bean> 中指定了该 Bean 的作用范围为 scope="prototype"，则将该 Bean 交给调用者，调用者管理该 Bean 的生命周期，Spring 不再管理该 Bean。

11）如果 Bean 实现了 DisposableBean 接口，则 Spring 会调用 destory() 方法将 Spring 中的 Bean 销毁；如果在配置文件中通过 destory-method 属性指定了 Bean 的销毁方法，则 Spring 将调用该方法对 Bean 进行销毁。

Spring 为 Bean 提供了细致全面的生命周期过程，通过实现特定的接口或 <bean> 的属性设置，都可以对 Bean 的生命周期过程产生影响。虽然可以随意配置 <bean> 的属性，但是建议不要过多地使用 Bean 实现接口，因为这样会导致代码和 Spring 的聚合过于紧密。





## bean的自动装配 1.9

**定义：spring会在上下文中自动寻找并且自动给属性设置值**

在spring中有三种装配的方式

1. 在xml中显式的配置
2. 在java中显式配置
3. 隐式的自动装配bean

### xml自动装配

```xml
<bean id="pp1" class="xx.xxx1"/>
<bean id="hello" class="xxxxx.xxx" autowire="byName">
    <!--<property name="pp1" ref="pp1"/> 自动装配就不需要这个属性赋值设置了-->
</bean>
```

当前hello这个对象的属性在context上下文中去寻找自动装配

- byName :会自动在容器上下文中查找，和自己对象set方法后面的值对应的beanid
- byType: 会自动在容器上下文查找，和自己对象属性类型相同的bean

### 注解的自动装配

要使用注解须知：

1. 注意导入context
2. 配置注解的支持：context:annotation-config

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--开启注解的支持-->
    <context:annotation-config/>

</beans>
```

也可以指定要扫描档包

```xml
<!--指定要扫描的包，这个包下的注解会有效-->
    <context:component-scan base-package="com.xingbg.spring.annotation.pojo"/>
```



### @**Autowired**

- 直接在属性上使用即可，也可以在set方法上使用。
- 使用Autowired我们可以不用编写set方法了，前提是这个自动装配属性在IOC容器中存在，且符合名字byName.

科普：

```java
@Nullable 字段标记了这个注解，说明这个字段可以为null
```

```java
public @interface Autowired {
    boolean required() default true;
}
```

```java
@Autowired(required = false)
public void setStudent(@Nullable Student student) {
     this.student = student;
}
```

### @**Qualifier**

指定bean的id

```java
@Qualifier("xxx")
@Qualifier(value = "xxx")
```

### @**Resource**

会先通过名字来找，名字找不到就通过类型来找。

```java
@Resource
@Resource(name="xxx")
```

Resource功能强大一些，但是效率会差一点点，不过可以忽略

### **@Resource和@Autowired的比较**

- 都是用来自动装配的，都可以放在属性字段上
- @Autowired通过byType的方式实现，而且必须要求这个对象存在
- @Resource默认通过byName的方式实现，如果找不到名字则通过byType实现，如果2个都找不到就报错

### @Component

装配的注解

可以使用这个注解和xml结合使用。就不用写<bean>了。

```java
@Component
public class User {
    
    @Value("xingbg")
    public String name;
}
```

@Component有几个衍生的注解 如下几个注解的功能都是一样的

- dao: @Respository
- service: @Service
- controller: @Controller

### @Scop

作用域

```java
@Scop(“singleton”)
```



### @Configuration

```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

The preceding `AppConfig` class is equivalent to the following Spring `<beans/>` XML:

上下两种方式是完全等价的

```xml
<beans>
    <bean id="myService" class="com.acme.services.MyServiceImpl"/>
</beans>
```

这种方式可以完全不适用xml来配置，全权交给java来做。



```java
@Configuration
@ComponentScan("com.xingbg.spring.annotation.pojo")
@Import(config2.class)
public class MyConfig {

    @Bean
    public User getUser() {
        return new User();
    }
}
```

**最佳实践**

- xml用来管理bean;
- 注解只负责完成属性的注入

## 动态代理

代理角色：抽象角色、真实角色、代理角色

动态代理和静态代理

- 代理角色一样
- 动态代理类是动态生成的不是写好的
- 动态代理分为2大类：基于接口的动态代理、基于类的动态代理
  - 基于接口----JDK动态代理
  - 基于类----cglib
  - java字节码实现 ----javasist

**需要了解2个类**

- Proxy
- InvocationHandler

**代理的好处**

- 可以使真实角色操作更加纯粹
- 公共业务交给代理角色，实现了业务分工
- 公共业务发生扩展的时候方便集中管理

## AOP

### 概念

Aspect Orented Programming

  ![img](https://img-blog.csdn.net/20160113113029764?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

- 横切关注点：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，如日志，安全，缓存，事务等等。

- 切面（Aspect）：横切关注点 被模块化的特殊对象。即，它是一个类。
- 通知（Advice）: 切面必须要完成的工作。即，它是类中的一个方法
- 目标（Target）：被通知的对象。
- 代理（Proxy）：向目标对象应用通知之后创建的对象。
- 切入点（PointCut）：切面通知 执行 的“地点”的定义。
- 连接点（JoinPoint）：与切入点匹配的执行点。

或参考如下

| 名称                | 说明                                                         |
| ------------------- | ------------------------------------------------------------ |
| Joinpoint（连接点） | 指那些被拦截到的点，在 Spring 中，可以被动态代理拦截目标类的方法。 |
| Pointcut（切入点）  | 指要对哪些 Joinpoint 进行拦截，即被拦截的连接点。            |
| Advice（通知）      | 指拦截到 Joinpoint 之后要做的事情，即对切入点增强的内容。    |
| Target（目标）      | 指代理的目标对象。                                           |
| Weaving（植入）     | 指把增强代码应用到目标上，生成代理对象的过程。               |
| Proxy（代理）       | 指生成的代理对象。                                           |
| Aspect（切面）      | 切入点和通知的结合。                                         |

Spring AOP中，通过Advice定义横切逻辑，Spring中支持5种类型的Advice

| 通知类型     | 连接点               | 实现接口                                        |
| ------------ | -------------------- | ----------------------------------------------- |
| 前置通知     | 方法前               | org.springframework.aop.MethodBeforeAdvice      |
| 后置通知     | 方法后               | org.springframework.aop.AfterReturningAdvice    |
| 环绕通知     | 方法前后             | org.aopalliance.intercept.MethodInterceptor     |
| 异常抛出通知 | 方法抛出异常         | org.springframework.aop.ThrowsAdvice            |
| 引介通知     | 类中增加新的方法属性 | org.springframework.aop.IntroductionInterceptor |

### AspectJ引用包

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.5</version>
</dependency>
```



### **实现方式**

- 使用Spring的api接口

- 自定义实现

- 使用注解实现

  

  单纯Spring Aop配置如下

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
   
      <!--扫描指定包-->
      <context:component-scan base-package="com.lnjecit.chapter4.user"/>
   
      <!--配置代理-->
      <bean id="userServiceProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
          <!--需要代理的接口-->
          <property name="interfaces" value="com.lnjecit.chapter4.user.UserService"/>
          <!--接口实现类-->
          <property name="target" ref="userServiceImpl"/>
          <!--拦截器名称（即增强类名称）-->
          <property name="interceptorNames">
              <list>
                  <!--注入的bean-->
                  <value>userAroundAdvice</value>
              </list>
          </property>
   
      </bean>
   
  </beans>
  ```

  

  

  **Spring+AspectJ配置如下：**

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         
         xmlns:aop="http://www.springframework.org/schema/aop"
         
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          https://www.springframework.org/schema/beans/spring-beans.xsd
  
          http://www.springframework.org/schema/aop 
          https://www.springframework.org/schema/aop/spring-aop.xsd">
  
      <bean id="calculateAop" class="com.xingbg.spring.aop.CalculateAopImpl"/>
      <bean id="beforeAop" class="com.xingbg.spring.aop.BeforeLogAspect"/>
      <bean id="afterAop" class="com.xingbg.spring.aop.AfterLogAspect"/>
  
      <bean id="diyPointCut" class="com.xingbg.spring.aop.DiyPointCut"/>
  
      <bean id="annotationAop" class="com.xingbg.spring.aop.AnnotationAspect"/>
  
      <!--开启注解支持 proxy-target-class="false"(默认)是使用jdk的代理方式去实现，proxy-target-class="true"使用cglib-->
      <aop:aspectj-autoproxy />
  
      <!--配置aop-->
      <aop:config>
          <!--实现方式1-->
          <aop:pointcut id="cut1" expression="execution(* com.xingbg.spring.aop.CalculateAopImpl.*(..))"/>
          <aop:advisor advice-ref="beforeAop" pointcut-ref="cut1"/>
          <aop:advisor advice-ref="afterAop" pointcut-ref="cut1"/>
  
          <!--实现方式2：自定义切面 相对简单，没有第一种方法强大-->
          <aop:aspect ref="diyPointCut">
              <!--切入点-->
              <aop:pointcut id="c2" expression="execution(* com.xingbg.spring.aop.CalculateAopImpl.*(..))"/>
              <!--通知-->
              <aop:before method="before" pointcut-ref="c2"/>
              <aop:after method="after" pointcut-ref="c2"/>
              <aop:after-returning method="afterReturning" pointcut-ref="c2" returning="re"/>
          </aop:aspect>
      </aop:config>
  </beans>
  ```

  ### execution表达式

  ```java
  execution(* com.loongshawn.method.ces..*.*(..))
  ```

  

  | 标识符                    | 含义                                                 |
  | ------------------------- | ---------------------------------------------------- |
  | execution()               | 表达式的主体                                         |
  | 第一个“*”符号             | 表示返回值的类型任意                                 |
  | com.loongshawn.method.ces | AOP所切的服务的包名，即，需要进行横切的业务类        |
  | 包名后面的“..”            | 表示当前包及子包                                     |
  | 第二个“*”                 | 表示类名，*即所有类                                  |
  | .*(..)                    | 表示任何方法名，括号表示参数，两个点表示任何参数类型 |

官方文档说明：

```java
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
throws-pattern?)
```

对照翻译如下：

```java
execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
```

匹配所有的public修饰符的方法:

> execution(public * *(..))

匹配所有”set”开头的方法:

> execution(* set*(..))

匹配AccountService 接口的所有方法:

> execution(* com.xyz.service.AccountService.*(..))

匹配service包中的所有方法:

> execution(* com.xyz.service.*.*(..))

匹配service包及其子包中的所有方法:

> execution(* com.xyz.service..*.*(..))

可以使用”*”和“..”通配符，其中“*”表示任意类型的参数，而“..”表示任意类型参数且参数个数不限。

  匹 配joke(String,int)方法，且joke()方法的第一个入参是String，第二个入参是int。如果方法中的入参类型是Java.lang包下的类，可以直接使用类名，否则必须使用全限定类名，如joke(java.util.List,int)；