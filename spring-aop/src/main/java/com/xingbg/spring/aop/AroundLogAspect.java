package com.xingbg.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AroundLogAspect implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();

        Object[] arguments = methodInvocation.getArguments();

        Object aThis = methodInvocation.getThis();

        return method.invoke(aThis,arguments);
    }
}
