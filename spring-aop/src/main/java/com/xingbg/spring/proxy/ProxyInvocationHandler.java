package com.xingbg.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用jdk的InvocationHandler来实现动态代理，target需要接口
 * @param <T>
 */
public class ProxyInvocationHandler<T> implements InvocationHandler {

    //被代理的对象
    private T target;

    public void setTarget(T target) {
        this.target = target;
    }

    public T getProxy() {
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logMethod(method.getName());
        return method.invoke(target,args);
    }

    private void logMethod(String message) {
        System.out.println(String.format("执行了%s方法",message));
    }
}
