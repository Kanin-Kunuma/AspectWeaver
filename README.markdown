# AspectWeaver

这是一个基于 Java 动态代理和反射机制实现的一个轻量级AOP框架，可用于中小型项目，进一步学习 Spring Aop 如何实现。

## 项目概述

- **技术栈**：Java、SpringBoo、Maven。
- **目标**：提供一个轻量级的 AOP 解决方案，支持日志记录、性能监控、事务管理等切面。
- **特点**：适合学习和快速集成。

## 功能特性

- 自定义切面（Aspect）支持。
- 方法执行前后拦截。
- 简单性能监控。
- 易于配置的切点表达式。

## 安装与运行

### 前置条件

- Java JDK (v17 或以上)
- Maven (v3.6 或以上)

### 安装步骤

1. **克隆仓库**：

   ```
   https://github.com/Kanin-Kunuma/AspectWeaver.git
   cd AspectWeaver
   ```

2. **安装依赖**：

   ```
   mvn install
   ```

3. **配置项目**：

   - 修改 `src/main/resources/application.properties`

     ```
     aop.enabled=true
     aop.aspect.package=com.example.aspect
     ```

4. **运行项目**：

   - 使用 Maven 运行：

     ```
     mvn spring-boot:run
     ```
   - 或者导入 IDE，运行主类。

5. **测试**：

   - 在代码中添加 `@EnableAspectJAutoProxy` 和自定义切面，观察拦截效果。

## 文件结构

```
[仓库名]/
├── src/main/java/        # Java 源代码
│   ├── com/example/aspect/  # 切面实现
│   └── com/example/config/  # 配置类
├── src/main/resources/   # 配置文件
├── pom.xml              # Maven 构建文件
├── README.md            # 项目说明
└── LICENSE              # 许可证（可选）
```

## 使用示例

```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before " + joinPoint.getSignature().getName());
    }
}
```

## 贡献指南

1. Fork 本仓库。
2. 创建功能分支 (`git checkout -b feature/xxx`)。
3. 提交代码 (`git commit -m ""`)。
4. 推送分支 (`git push`)。
5. 提交 Pull Request。

## 许可证

MIT License。