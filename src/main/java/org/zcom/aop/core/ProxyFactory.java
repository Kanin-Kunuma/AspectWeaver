package org.zcom.aop.core;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cglib.proxy.Enhancer;
import org.zcom.aop.annotation.Aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class ProxyFactory {
    public static <T> T get(Object target, AopProxy aop) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(aop);
        return (T) enhancer.create();
    }


    public static Object tryBuild(Object targetObject, Object aopObject){
        boolean isRetAopProxy = false;
        final AopProxy aopProxy = new AopProxy();
        final Aop aop = aopObject.getClass().getAnnotation(Aop.class);
        aopProxy.aopObject = aopObject;
        // 设置 aopObject 中增强方法
        // 如果是以路径进行拦截，因路径最小单元为类级别，因此直接将整个类中的方法进行增强
        aopProxy.setEnhancerMethods(aopObject);
        if (!Strings.isBlank(aop.jointPath())) {
            aopProxy.isInterceptorAll = true;
            isRetAopProxy = true;
        }else {
            final Class<? extends Annotation> aopAnnotation = aop.joinAnnotationClass();
            final Class<?> targetObjectClass = targetObject.getClass();
            // 如果是类级别
            if (targetObjectClass.isAnnotationPresent(aopAnnotation)){
                aopProxy.isInterceptorAll = true;
                isRetAopProxy = true;
            }else {
                // 方法级别，遍历方法
                for (Method method : targetObjectClass.getMethods()) {
                    if (method.isAnnotationPresent(aopAnnotation)) {
                        isRetAopProxy = true;
                        aopProxy.interceptorMethods.put(method.hashCode(),method);
                    }
                }
            }
        }
        if (isRetAopProxy){
            return get(targetObject,aopProxy);
        }else {
            return targetObject;
        }
    }


}
