package com.xingbg.spring.aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BeforeLogAspect implements MethodBeforeAdvice {

    /**
     * 执行前
     * @param method
     * @param objects 方法参数
     * @param o 目标对象
     * @throws Throwable
     */
    public void before(Method method, Object[] objects, Object o) throws Throwable {
          System.out.println(o.getClass().getName()+"的"+method.getName()+"即将被执行");
    }
}
