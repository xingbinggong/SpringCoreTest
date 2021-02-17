package com.xingbg.spring.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 使用spring的cglib来实现动态代理，target不需要接口
 * @param <T>
 */
public class ProxyMethodInterceptor<T> implements MethodInterceptor {

    //被代理的对象
    private T target;

    public void setTarget(T target) {
        this.target = target;
    }

    public T getProxy() {
        // 生成代理类，CGLIB在运行时，生成指定对象的子类，增强
        Enhancer enhancer = new Enhancer();
        // 确定需要增强的类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T)enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        logMethod(method.getName());
        return method.invoke(target,objects);
    }

    private void logMethod(String message) {
        System.out.println(String.format("执行了%s方法",message));
    }
}
