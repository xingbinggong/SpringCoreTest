# Spring注解

## @ComponentScan

1.创建一个配置类，在配置类上添加 @ComponentScan 注解。该注解默认会扫描该类所在的包下所有的配置类，相当于之前的 <context:component-scan>。



```css
package io.mieux.config;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class BeanConfig {

}
```

2.使用 ApplicationContext 的 getBeanDefinitionNames() 方法获取已经注册到容器中的 bean 的名称。



```swift
import io.mieux.config.BeanConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App02 {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(BeanConfig.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println("beanName: " + beanName);
        }
    }
}
```

具体用法参考：https://www.jianshu.com/p/64aac6461d5b

## @Import

提供与 xml 中 <import/> 等效的功能, 允许去导入@Configuration类, ImportSelector 和 ImportBeanDefinitionRegistrar 的具体实现, 以及常规组件类 (这一句划重点)。
 类似于 AnnotationConfigApplicationContext.register(java.lang.Class<?>...) 这种操作。

可以在类级别声明或作为元注释声明。

如果需要导入XML或其他非@Configuration bean定义资源，请改用@ImportResource注释。
 **以上是官方的意思**

https://www.jianshu.com/p/56d4cadbe5c9
