package org.zcom.aop.core;

import org.springframework.cglib.proxy.MethodProxy;
import org.zcom.aop.core.JoinPoint;

import java.lang.reflect.Method;


public class ProceedingJoinPoint extends JoinPoint {


    private final Object targetObject;

    private final MethodProxy methodProxy;

    public ProceedingJoinPoint(Object targetObject, MethodProxy methodProxy,Method method, Object[] args ) {
        super(method,args);
        this.targetObject = targetObject;
        this.methodProxy = methodProxy;
    }

    @Override
    public Object invoke() throws Throwable {
        return methodProxy.invokeSuper(targetObject, getArgs());
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return methodProxy.invokeSuper(targetObject, args);
    }
}

