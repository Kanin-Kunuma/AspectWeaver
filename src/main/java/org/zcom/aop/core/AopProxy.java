package org.zcom.aop.core;



import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.zcom.aop.core.ProceedingJoinPoint;
import org.zcom.aop.annotation.After;
import org.zcom.aop.annotation.Around;
import org.zcom.aop.annotation.Before;
import org.zcom.aop.annotation.Throwing;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AopProxy implements MethodInterceptor {

    // 用户写的 AOP 切入类, 也就是写了before after 切入点的类
    Object aopObject;

    Method beforeMethod;
    Method afterMethod;
    Method throwingMethod;
    Method aroundMethod;

    // 增强的目标方法
    Map<Integer, Method> interceptorMethods = new HashMap<>();

    // 是否拦截所有方法
    boolean isInterceptorAll = false;

    public AopProxy() {

    }

    public void setEnhancerMethods(Object aopObject) {
        for (Method method : aopObject.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethod = method;
            }
            else if (method.isAnnotationPresent(After.class)) {
                afterMethod = method;
            }
            else if (method.isAnnotationPresent(Around.class)) {
                aroundMethod = method;
            }
            else if (method.isAnnotationPresent(Throwing.class)) {
                throwingMethod = method;
            }
        }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        final int hashCode = method.hashCode();
        // 前后置拦截器入参分别为 JointPoint
        // 环绕为 ProceedingJointPoint, 因为环绕有 invoke 行为
        if (isInterceptorAll || interceptorMethods.containsKey(hashCode)) {
            final JoinPoint joinPoint = new JoinPoint(method, objects);
            Object ret = null;
            // 执行顺序： 前 -> 环绕 -> 后 -> 异常
            try {
                invokeMethod(joinPoint, beforeMethod);
                // 如果有环绕通知, 则执行目标方法由环绕通知执行
                if (aroundMethod != null) {
                    ret = invokeMethod(new ProceedingJoinPoint(o, methodProxy, method, objects), aroundMethod);
                }
                else {
                    ret = methodProxy.invokeSuper(o, objects);
                }
                return ret;
            }

            catch (Throwable throwable) {
                throwable.printStackTrace();
                invokeMethod(throwable, throwingMethod);
                return ret;
            }

            finally {
                invokeMethod(joinPoint, afterMethod);
            }
        }
        return methodProxy.invokeSuper(o, objects);
    }

    private Object invokeMethod(Object joinPoint, Method method) throws Throwable {
        Object ret = null;
        if (method != null) {
            // 判断执行方法上面是否有目标参数
            if (method.getParameterTypes().length > 0) {
                if (!method.getParameterTypes()[0].equals(joinPoint.getClass())) {
                    throw new IllegalArgumentException("参数映射错误: 非 IJoinPoint 参数");
                }
                ret = method.invoke(aopObject, joinPoint);
                return ret;
            }
            else {
                ret = method.invoke(aopObject);
                return ret;
            }
        }
        return null;
    }
}
