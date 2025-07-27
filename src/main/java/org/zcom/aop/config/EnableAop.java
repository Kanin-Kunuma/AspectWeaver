package org.zcom.aop.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PointBeanPostProcess.class)
public @interface EnableAop {
}
