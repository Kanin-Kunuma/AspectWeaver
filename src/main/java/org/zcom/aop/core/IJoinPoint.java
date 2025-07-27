package org.zcom.aop.core;

import java.lang.annotation.Annotation;

public interface IJoinPoint {

    // 获取目标方法的入参
    Object[] getArgs();

    // 根据入参的类型返回对应返回值的类型
    <T extends Annotation> T getAnnotation(Class<T> annotationClass);

    default Object invoke() throws Throwable {
        return null;
    }

    default Object invoke(Object[] args) throws Throwable {
        return null;
    }
}
