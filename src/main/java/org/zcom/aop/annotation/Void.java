package org.zcom.aop.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Void {

    // 基于路径AOP, 匹配最小单位为包
    String jointPath() default "";

    // 基于注解aop
    Class<? extends Annotation> joinAnnotationClass() default Void.class;
}
