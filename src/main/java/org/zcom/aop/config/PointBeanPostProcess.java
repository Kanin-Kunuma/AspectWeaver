package org.zcom.aop.config;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.zcom.aop.annotation.Aop;
import org.zcom.aop.core.ProxyFactory;

import java.util.HashMap;
import java.util.Map;

@Component
public class PointBeanPostProcess implements BeanPostProcessor {

    // 切入点路径
    Map<String, Object> jointPointPathMap;

    // 切入点注解
    Map<Class, Object> jointPointAnnotationMap;

    public PointBeanPostProcess() {
        jointPointPathMap = new HashMap<>();
        jointPointAnnotationMap = new HashMap<>();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> beanClass = bean.getClass();
        final Aop aop = beanClass.getAnnotation(Aop.class);
        if (aop != null) {
            final String joinPath = aop.jointPath();
            // 两个中只能生成一个
            if (!joinPath.equals("")) {
                jointPointPathMap.put(joinPath, bean);
            }
            else {
                jointPointAnnotationMap.put(aop.joinAnnotationClass(), bean);
            }
        }
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> beanClass = bean.getClass();
        final String path = beanClass.getName();
        // 通过路径匹配
        for (String s : jointPointPathMap.keySet()) {
            if (path.startsWith(s)) {
                return ProxyFactory.tryBuild(bean, jointPointPathMap.get(s));
            }
        }
        // 通过注解匹配
        for (Object aopObject : jointPointAnnotationMap.values()) {
            return ProxyFactory.tryBuild(bean, aopObject);
        }
        return bean;
    }
}


