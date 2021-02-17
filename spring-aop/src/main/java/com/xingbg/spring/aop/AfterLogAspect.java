package com.xingbg.spring.aop;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterLogAspect implements AfterReturningAdvice {

    /**
     *
     * @param o
     * @param method
     * @param objects
     * @param o1
     * @throws Throwable
     */
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println(o1.getClass().getName()+"的"+method.getName()+"已经被执行，执行结果是："+o);
    }
}
