package com.xingbg.spring.aop;

import org.aspectj.lang.JoinPoint;

public class DiyPointCut {

    public void before(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println(String.format("====================%s方法执行前========================)",methodName));
    }

    public void after(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println(String.format("====================%s方法执行后========================",methodName));
    }

    public void afterReturning(JoinPoint jp,Object re) {
        String methodName = jp.getSignature().getName();
        System.out.println(String.format("====================%s方法执行返回后%s========================",methodName,re));
    }
}
