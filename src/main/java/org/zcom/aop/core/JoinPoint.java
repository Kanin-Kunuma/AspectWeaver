package org.zcom.aop.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class JoinPoint implements IJoinPoint{

    protected final Method method;

    // 目标方法入参
    private Object[] args;

    public JoinPoint(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }
}
