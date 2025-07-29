package com.zcom.demo;

import com.zcom.demo.Auth;
import org.springframework.beans.factory.annotation.Configurable;
import org.zcom.aop.annotation.After;
import org.zcom.aop.annotation.Aop;
import org.zcom.aop.annotation.Around;
import org.zcom.aop.annotation.Before;
import org.zcom.aop.annotation.Throwing;
import org.zcom.aop.core.JoinPoint;
import org.zcom.aop.core.ProceedingJoinPoint;

@Aop(joinAnnotationClass = Auth.class)
@Configurable
public class AuthAspect {

    @Before
    public void before(JoinPoint joinPoint){
        final Auth auth = joinPoint.getAnnotation(Auth.class);
        System.out.println("Auth注解值: " + auth.value());
        System.out.println("方法参数: " + java.util.Arrays.toString(joinPoint.getArgs()));
        System.out.println("前置拦截器");
    }

    @After
    public void after(JoinPoint joinPoint){
        System.out.println("后置拦截器");
    }

    @Around
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕通知-前");
        Object ret = joinPoint.invoke();
        System.out.println("环绕通知-后");
        return ret;
    }

    @Throwing
    public void throwing(){
        System.out.println("出现异常");
    }
}

