 

## 一、Spring 特性总览

### 核心特性（core）

- IoC 容器（IoC Container） 
- Spring 事件（Events） 
- 资源管理（Resources） 
- 国际化（i18n） 
- 校验（Validation） 
- 数据绑定（Data Binding） 
- 类型装换（Type Conversion） 
- Spring 表达式（Spring Express Language） 
- 面向切面编程（AOP）

### 数据存储（Data Access）

- JDBC
- 事务抽象（Transactions） 
- DAO 支持（DAO Support） 
- O/R映射（O/R Mapping） 
- XML 编列（XML Marshalling）

### Web 技术（Web）

- Web Servlet 技术栈
  - Spring MVC
  - WebSocket
  - SockJS

- Web Reactive 技术栈
  - Spring WebFlux
  - WebClient
  - WebSocket

### 技术整合（Integration）

- 远程调用（Remoting） 
- Java 消息服务（JMS） 
- Java 连接架构（ JCA） 
- Java 管理扩展（JMX） 
- Java 邮件客户端（Email） 
- 本地任务（Tasks） 
- 本地调度（Scheduling） 
- 缓存抽象（Caching） 
- Spring 测试（Testing）

### 测试（Testing）

- 模拟对象（Mock Objects） 
- TestContext 框架（TestContext Framework） 
- Spring MVC 测试（Spring MVC Test） 
- Web 测试客户端（WebTestClient）

## Spring 模块化设计（Modular）

- spring-aop
- spring-aspects
- spring-context-indexer
- spring-context-support
- spring-context
- spring-core
- spring-expression
- spring-instrument
- spring-jcl
- spring-jdbc
- spring-jms
- spring-messaging
- spring-orm
- spring-oxm
- spring-test
- spring-tx
- spring-web
- spring-webflux
- spring-webmvc
- spring-websocket

## 二、重新认识IOC

### 概念

In software engineering, inversion of control (IoC) is a programming principle. IoC inverts the flow of control as compared to traditional control flow. In IoC, custom-written portions of a computer program receive the flow of control from a generic framework. A software architecture with this design inverts control as compared to traditional procedural programming: in traditional programming, the custom code that expresses the purpose of the program calls into reusable libraries to take care of generic tasks, but with inversion of control, it is the framework that calls into the custom, or task-specific, code.

来源：https://en.wikipedia.org/wiki/Inversion_of_control

### IoC 的简史

- 1983年，Richard E. Sweet 在《The Mesa Programming Environment》中提出“Hollywood Principle”（好莱坞原则）
- 1988年，Ralph E. Johnson & Brian Foote 在《Designing Reusable Classes》中提出“Inversion of control”（控制反转）
- 1996年，Michael Mattsson 在《Object-Oriented Frameworks, A survey of methodological issues》中将“Inversion of control”命名为 “Hollywood principle”
- 2004年，Martin Fowler 在《Inversion of Control Containers and the Dependency Injection pattern》中提出了自己对 IoC 以及 DI 的理解
- 2005年，Martin Fowler 在 《InversionOfControl》对 IoC 做出进一步的说明

### IoC 主要实现策略

- *Using a service locator pattern*
- *Using dependency injection, for example*
  - *Constructor injection*
  - *Parameter injection*
  - *Setter injection*
  - *Interface injection*
- *Using a contextualized lookup*
- *Using template method design pattern*
- *Using strategy design pattern*

### IoC 容器的职责

- 通用职责 
- 依赖处理
  - 依赖查找
  - 依赖注入
- 生命周期管理
  - 容器
  - 托管的资源（Java Beans 或其他资源）
- 配置
  - 容器
  - 外部化配置
  - 托管的资源（Java Beans 或其他资源）

### IoC 容器的实现

- 主要实现
- Java SE
  - Java Beans
  - Java ServiceLoader SPI
  - JNDI（Java Naming and Directory Interface） 
- Java EE
  - EJB（Enterprise Java Beans） 
  - Servlet 
- 开源
  - Apache Avalon（http://avalon.apache.org/closed.html） 
  - PicoContainer（http://picocontainer.com/） 
  - Google Guice（https://github.com/google/guice） 
  - Spring Framework（https://spring.io/projects/spring-framework）

### 传统 IoC 容器的实现

- Java Beans 作为 IoC 容器
- 特性
  - 依赖查找
  - 生命周期管理
  - 配置元信息
  - 事件
  - 自定义
  - 资源管理
  - 持久化
- 规范
  - JavaBeans：https://www.oracle.com/technetwork/java/javase/tech/index-jsp-138795.html
  - BeanContext：https://docs.oracle.com/javase/8/docs/technotes/guides/beans/spec/beancontext.html

## 三、Spring IoC 容器概述

### Spring IoC 依赖查找

- 根据 Bean 名称查找 
  - 实时查找 
  - 延迟查找 
- 根据 Bean 类型查找 
  - 单个 Bean 对象 
  - 集合 Bean 对象 
- 根据 Bean 名称 + 类型查找
- 根据 Java 注解查找 
  - 单个 Bean 对象
  - 集合 Bean 对象

### Spring IoC 依赖注入

- 根据 Bean 名称注入
- 根据 Bean 类型注入
  - 单个 Bean 对象
  - 集合 Bean 对象
- 注入容器內建 Bean 对象
- 注入非 Bean 对象
- 注入类型 
  - 实时注入
  - 延迟注入

### Spring IoC 依赖来源

- 自定义 Bean
- 容器內建 Bean 对象
- 容器內建依赖

### Spring IoC 配置元信息

- Bean 定义配置
  - 基于 XML 文件
  - 基于 Properties 文件
  - 基于 Java 注解
  - 基于 Java API（专题讨论）
- IoC 容器配置
  - 基于 XML 文件
  - 基于 Java 注解
  - 基于 Java API （专题讨论）
- 外部化属性配置
  - 基于 Java 注解

### Spring IoC 容器

BeanFactory 和 ApplicationContext 谁才是 Spring IoC 容器？

- BeanFactory 是 Spring 底层 IoC 容器
- ApplicationContext 是具备应用特性的 BeanFactory 超集

### Spring 应用上下文

- ApplicationContext 除了 IoC 容器角色，还有提供：
  - 面向切面（AOP） 
  - 配置元信息（Configuration Metadata） 
  - 资源管理（Resources） 
  - 事件（Events） 
  - 国际化（i18n） 
  - 注解（Annotations） 
  - Environment 抽象（Environment Abstraction）

## 四、Spring Bean 基础

### 定义 Spring Bean

- 什么是 BeanDefinition？  

- BeanDefinition 是 Spring Framework 中定义 Bean 的配置元信息接口，包含
  - Bean 的类名
  - Bean 行为配置元素，如作用域、自动绑定的模式，生命周期回调等
  - 其他 Bean 引用，又可称作合作者（collaborators）或者依赖（dependencies） 
  - 配置设置，比如 Bean 属性（Properties）

### BeanDefinition 元信息

| **属性（Property**）     | **说明**                                      |
| ------------------------ | --------------------------------------------- |
| Class                    | Bean 全类名，必须是具体类，不能用抽象类或接口 |
| Name                     | Bean 的名称或者 ID                            |
| Scope                    | Bean 的作用域（如：singleton、prototype 等）  |
| Constructor arguments    | Bean 构造器参数（用于依赖注入）               |
| Properties               | Bean 属性设置（用于依赖注入）                 |
| Autowiring mode          | Bean 自动绑定模式（如：通过名称 byName）      |
| Lazy initialization mode | Bean 延迟初始化模式（延迟和非延迟）           |
| Initialization method    | Bean 初始化回调方法名称                       |
| Destruction method       | Bean 销毁回调方法名称                         |

### BeanDefinition 构建

- 通过 BeanDefinitionBuilder
- 通过 AbstractBeanDefinition 以及派生类

### 命名 Spring Bean

- Bean 的名称
  - 每个 Bean 拥有一个或多个标识符（identifiers），这些标识符在 Bean 所在的容器必须是唯一的。通常，一个 Bean 仅有一个标识符，如果需要额外的，可考虑使用别名（Alias）来扩充。
  - 在基于 XML 的配置元信息中，开发人员可用 id 或者 name 属性来规定 Bean 的 标识符。通常Bean 的 标识符由字母组成，允许出现特殊字符。如果要想引入 Bean 的别名的话，可在name 属性使用半角逗号（“,”）或分号（“;”) 来间隔。
  - Bean 的 id 或 name 属性并非必须制定，如果留空的话，容器会为 Bean 自动生成一个唯一的名称。Bean 的命名尽管没有限制，不过官方建议采用驼峰的方式，更符合 Java 的命名约定。
- Bean 名称生成器（BeanNameGenerator）由 Spring Framework 2.0.3 引入，框架內建两种实现：
  - DefaultBeanNameGenerator：默认通用 BeanNameGenerator 实现
  - AnnotationBeanNameGenerator：基于注解扫描的 BeanNameGenerator 实现，起始于Spring Framework 2.5

### Spring Bean 的别名

- Bean 别名（Alias）的价值

  - 复用现有的 BeanDefinition

  - 更具有场景化的命名方法，比如

  - ```xml
    <alias name="myApp-dataSource" alias="subsystemA-dataSource"/>
    
    <alias name="myApp-dataSource" alias="subsystemB-dataSource"/>
    ```

### 注册 Spring Bean

- BeanDefinition 注册

  - XML 配置元信息

    - **<bean name=”...” ... />** 

  - Java 注解配置元信息

    - **@Bean**

    - **@Component**

    - **@Import**

  - Java API 配置元信息

    - 命名方式：**BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)**
    - 非命名方式：**BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition,BeanDefinitionRegistry)**
    - 配置类方式：**AnnotatedBeanDefinitionReader#register(Class...)**

-  外部单例对象注册 

  - Java API 配置元信息
    - **SingletonBeanRegistry#registerSingleton**

### 实例化 Spring Bean

- Bean 实例化（Instantiation） 
- 常规方式 
  - 通过构造器（配置元信息：XML、Java 注解和 Java API ） 
  - 通过静态工厂方法（配置元信息：XML 和 Java API ） 
  - 通过 Bean 工厂方法（配置元信息：XML和 Java API ） 
  - 通过 **FactoryBean**（配置元信息：XML、Java 注解和 Java API ）
- 特殊方式
  - 通过 **ServiceLoaderFactoryBean**（配置元信息：XML、Java 注解和 Java API ） 
  - 通过 **AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)**
  - 通过 **BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)**

### 初始化 Spring Bean

- Bean 初始化（Initialization） 
  - @PostConstruct 标注方法
  - 实现 InitializingBean 接口的 afterPropertiesSet() 方法
  - 自定义初始化方法
    - XML 配置：<bean init-method=”init” ... /> 
    - Java 注解：@Bean(initMethod=”init”)
    - Java API：AbstractBeanDefinition#setInitMethodName(String)

思考：假设以上三种方式均在同一 Bean 中定义，那么这些方法的执行顺序是怎样？

### 延迟初始化 Spring Bean

- Bean 延迟初始化（Lazy Initialization） 
  - XML 配置：<bean lazy-init=”true” ... />
  - Java 注解：@Lazy(true)

思考：当某个 Bean 定义为延迟初始化，那么，Spring 容器返回的对象与非延迟的对象存在怎样的差异？

### 销毁 Spring Bean

- Bean 销毁（Destroy） 
  - @PreDestroy 标注方法
  - 实现 DisposableBean 接口的 destroy() 方法
  - 自定义销毁方法
    - XML 配置：<bean destroy=”destroy” ... /> 
    - Java 注解：@Bean(destroy=”destroy”)
    - Java API：AbstractBeanDefinition#setDestroyMethodName(String)

思考：假设以上三种方式均在同一 Bean 中定义，那么这些方法的执行顺序是怎样？

### 垃圾回收 Spring Bean

- Bean 垃圾回收（GC）
  - 关闭 Spring 容器（应用上下文）
  - 执行 GC
  - Spring Bean 覆盖的 finalize() 方法被回调

## 五、Spring IoC 依赖查找

### 单一类型依赖查找

单一类型依赖查找接口 - BeanFactory

- 根据 Bean 名称查找
  - getBean(String)
  - Spring 2.5 覆盖默认参数：getBean(String,Object...)
- 根据 Bean 类型查找
  - Bean 实时查找
    - Spring 3.0 getBean(Class)
    - Spring 4.1 覆盖默认参数：getBean(Class,Object...)
  - Spring 5.1 Bean 延迟查找
    - getBeanProvider(Class)
    - getBeanProvider(ResolvableType)
- 根据 Bean 名称 + 类型查找：getBean(String,Class)

### 集合类型依赖查找

集合类型依赖查找接口 - ListableBeanFactory

- 根据 Bean 类型查找
  - 获取同类型 Bean 名称列表
    - getBeanNamesForType(Class)
    - Spring 4.2 getBeanNamesForType(ResolvableType)
  - 获取同类型 Bean 实例列表
    - getBeansOfType(Class) 以及重载方法
- 通过注解类型查找
  - Spring 3.0 获取标注类型 Bean 名称列表
    - getBeanNamesForAnnotation(Class<? extends Annotation>)
  - Spring 3.0 获取标注类型 Bean 实例列表
    - getBeansWithAnnotation(Class<? extends Annotation>)
  - Spring 3.0 获取指定名称 + 标注类型 Bean 实例
    - findAnnotationOnBean(String,Class<? extends Annotation>)

### 层次性依赖查找

层次性依赖查找接口 - HierarchicalBeanFactory

- 双亲 BeanFactory：getParentBeanFactory()
- 层次性查找
  - 根据 Bean 名称查找
    - 基于 containsLocalBean 方法实现
  - 根据 Bean 类型查找实例列表
    - 单一类型：BeanFactoryUtils#beanOfType
    - 集合类型：BeanFactoryUtils#beansOfTypeIncludingAncestors
  - 根据 Java 注解查找名称列表
    - BeanFactoryUtils#beanNamesForTypeIncludingAncestors

### 延迟依赖查找

Bean 延迟依赖查找接口

- org.springframework.beans.factory.ObjectFactory
- org.springframework.beans.factory.ObjectProvider
  - Spring 5 对 Java 8 特性扩展
    - 函数式接口
      - getIfAvailable(Supplier) 
      - ifAvailable(Consumer)
    - Stream 扩展 - stream()

### 安全依赖查找

依赖查找安全性对比

| **依赖查找类型** | **代表实现**                       | **是否安全** |
| ---------------- | ---------------------------------- | ------------ |
| 单一类型查找     | BeanFactory#getBean                | **否**       |
|                  | ObjectFactory#getObject            | **否**       |
|                  | ObjectProvider#getIfAvailable      | **是**       |
|                  |                                    |              |
| 集合类型查找     | ListableBeanFactory#getBeansOfType | **是**       |
|                  | ObjectProvider#stream              | **是**       |

>注意：层次性依赖查找的安全性取决于其扩展的单一或集合类型的 BeanFactory 接口

### 内建可查找的依赖

AbstractApplicationContext 内建可查找的依赖

| **Bean 名称**               | **Bean 实例**                    | **使用场景**            |
| --------------------------- | -------------------------------- | ----------------------- |
| environment                 | Environment 对象                 | 外部化配置以及 Profiles |
| systemProperties            | java.util.Properties 对象        | Java 系统属性           |
| systemEnvironment           | java.util.Map 对象               | 操作系统环境变量        |
| messageSource               | MessageSource 对象               | 国际化文案              |
| lifecycleProcessor          | LifecycleProcessor 对象          | Lifecycle Bean 处理器   |
| applicationEventMulticaster | ApplicationEventMulticaster 对象 | Spring 事件广播器       |

注解驱动 Spring 应用上下文内建可查找的依赖

| **Bean 名称**                                                | **Bean 实例**                               | **使用场景**                                          |
| ------------------------------------------------------------ | ------------------------------------------- | ----------------------------------------------------- |
| org.springframework.context.annotation.internalConfigurationAnnotationProcessor | ConfigurationClassPostProcessor 对象        | 处理 Spring 配置类                                    |
| org.springframework.context.annotation.internalAutowiredAnnotationProcessor | AutowiredAnnotationBeanPostProcessor 对象   | 处理 @Autowired 以及 @Value注解                       |
| org.springframework.context.annotation.internalCommonAnnotationProcessor | CommonAnnotationBeanPostProcessor 对象      | （条件激活）处理 JSR-250 注解，如 @PostConstruct 等   |
| org.springframework.context.event.internalEventListenerProcessor | EventListenerMethodProcessor 对象           | 处理标注 @EventListener 的Spring 事件监听方法         |
| org.springframework.context.event.internalEventListenerFactory | DefaultEventListenerFactory 对 象           | @EventListener 事件监听方法适配为 ApplicationListener |
| org.springframework.context.annotation.internalPersistenceAnnotationProcessor | PersistenceAnnotationBeanPostProcessor 对象 | （条件激活）处理 JPA 注解场景                         |

### 依赖查找中的经典异常

BeansException 子类型

| **异常类型**                    | **触发条件（举例）**                       | **场景举例**                               |
| ------------------------------- | ------------------------------------------ | ------------------------------------------ |
| NoSuchBeanDefinitionException   | 当查找 Bean 不存在于 IoC 容器时            | BeanFactory#getBeanObjectFactory#getObject |
| NoUniqueBeanDefinitionException | 类型依赖查找时，IoC 容器存在多个 Bean 实例 | BeanFactory#getBean(Class)                 |
| BeanInstantiationException      | 当 Bean 所对应的类型非具体类时             | BeanFactory#getBean                        |
| BeanCreationException           | 当 Bean 初始化过程中                       | Bean 初始化方法执行异常时                  |
| BeanDefinitionStoreException    | 当 BeanDefinition 配置元信息非法           | XML 配置资源无法打开时                     |

## 六、Spring IoC 注入

### 依赖注入的模式和类型

- 手动模式 - 配置或者编程的方式，提前安排注入规则
  - XML 资源配置元信息
  - Java 注解配置元信息
  - API 配置元信息
- 自动模式 - 实现方提供依赖自动关联的方式，按照內建的注入规则
  - Autowiring（自动绑定）

### 依赖注入的模式和类型

| **依赖注入类型** | **配置元数据举例**                               |
| ---------------- | ------------------------------------------------ |
| Setter 方法      | <proeprty name="user" ref="userBean"/>           |
| 构造器           | <constructor-arg name="user" ref="userBean" />   |
| 字段             | @Autowired User user;                            |
| 方法             | @Autowired public void user(User user) { ... }   |
| 接口回调         | class MyBean implements BeanFactoryAware { ... } |

### 自动绑定（Autowiring）

Autowiring modes

| **模式**    | **说明**                                                     |
| ----------- | ------------------------------------------------------------ |
| no          | 默认值，未激活 Autowiring，需要手动指定依赖注入对象。        |
| byName      | 根据被注入属性的名称作为 Bean 名称进行依赖查找，并将对象设置到该属性 |
| byType      | 根据被注入属性的类型作为依赖类型进行查找，并将对象设置到该属性 |
| constructor | 特殊 byType 类型，用于构造器参数                             |

> 参考枚举：org.springframework.beans.factory.annotation.Autowire

### Setter 方法注入

- 手动模式
  - XML 资源配置元信息
  - Java 注解配置元信息
  - API 配置元信息
- 自动模式
  - byName
  - byType

### 构造器注入

- 手动模式
  - XML 资源配置元信息
  - Java 注解配置元信息
  - API 配置元信息
- 自动模式
  - constructor

### 字段注入

- 手动模式
  - Java 注解配置元信息
    - @Autowired
    - @Resource
    - @Inject（可选）

### 方法注入

- 手动模式
  - Java 注解配置元信息
    - @Autowired
    - @Resource
    - @Inject（可选）
    - @Bean

### 接口回调注入

Aware 系列接口回调

| **內建接口**                   | **说明**                                                 |
| ------------------------------ | -------------------------------------------------------- |
| BeanFactoryAware               | 获取 IoC 容器 - BeanFactory                              |
| ApplicationContextAware        | 获取 Spring 应用上下文 - ApplicationContext 对象         |
| EnvironmentAware               | 获取 Environment 对象                                    |
| ResourceLoaderAware            | 获取资源加载器 对象 - ResourceLoader                     |
| BeanClassLoaderAware           | 获取加载当前 Bean Class 的 ClassLoader                   |
| BeanNameAware                  | 获取当前 Bean 的名称                                     |
| MessageSourceAware             | 获取 MessageSource 对象，用于 Spring 国际化              |
| ApplicationEventPublisherAware | 获取 ApplicationEventPublishAware 对象，用于 Spring 事件 |
| EmbeddedValueResolverAware     | 获取 StringValueResolver 对象，用于占位符处理            |

### 依赖注入类型选择

- 低依赖：构造器注入
- 多依赖：Setter 方法注入
- 便利性：字段注入
- 声明类：方法注入

### 基础类型注入

- 原生类型（Primitive）：boolean、byte、char、short、int、float、long、double
- 标量类型（Scalar）：Number、Character、Boolean、Enum、Locale、Charset、Currency、Properties、UUID
- 常规类型（General）：Object、String、TimeZone、Calendar、Optional 等 
- Spring 类型：Resource、InputSource、Formatter 等

### 集合类型注入

- 数组类型（Array）：原生类型、标量类型、常规类型、Spring 类型
- 集合类型（Collection） 
  - Collection：List、Set（SortedSet、NavigableSet、EnumSet） 
  - Map：Properties

### 限定注入

- 使用注解 @Qualifier 限定
  - 通过 Bean 名称限定
  - 通过分组限定
- 基于注解 @Qualifier 扩展限定
  - 自定义注解 - 如 Spring Cloud @LoadBalanced

### 延迟依赖注入

- 使用 API ObjectFactory 延迟注入
  - 单一类型 
  - 集合类型
- 使用 API ObjectProvider 延迟注入（推荐）
  - 单一类型
  - 集合类型

### 依赖处理过程

- 入口 - DefaultListableBeanFactory#resolveDependency
- 依赖描述符 - DependencyDescriptor
- 自定绑定候选对象处理器 - AutowireCandidateResolver

### @Autowired 注入

- @Autowired 注入规则
  - 非静态字段
  - 非静态方法
  - 构造器

- @Autowired 注入过程
  - 元信息解析
  - 依赖查找
  - 依赖注入（字段、方法）

### @Inject 注入

如果 JSR-330 存在于 ClassPath 中，复用 AutowiredAnnotationBeanPostProcessor 实现

### Java通用注解注入原理

CommonAnnotationBeanPostProcessor

- 注入注解
  - javax.xml.ws.WebServiceRef
  - javax.ejb.EJB
  - javax.annotation.Resource
- 生命周期注解
  - javax.annotation.PostConstruct
  - javax.annotation.PreDestroy

### 自定义依赖注入注解

基于 AutowiredAnnotationBeanPostProcessor 实现

- 自定义实现
  - 生命周期处理
    - InstantiationAwareBeanPostProcessor
    - MergedBeanDefinitionPostProcessor
  - 元数据
    - InjectedElement
    - InjectionMetadata

## 七、Spring IOC 依赖来源

### 依赖查找的来源

#### 查找来源

| 来源                  | 配置元数据                                   |
| --------------------- | -------------------------------------------- |
| Spring BeanDefinition | <bean id="user" class="org.geekbang...User"> |
|                       | @Bean public User user(){...}                |
|                       | BeanDefinitionBuilder                        |
| 单例对象              | API 实现                                     |

#### Spring 內建 BeanDefintion

| **Bean 名称**                                                | **Bean 实例**                               | **使用场景**                                          |
| ------------------------------------------------------------ | ------------------------------------------- | ----------------------------------------------------- |
| org.springframework.context.annotation.internalConfigurationAnnotationProcessor | ConfigurationClassPostProcessor 对象        | 处理 Spring 配置类                                    |
| org.springframework.context.annotation.internalAutowiredAnnotationProcessor | AutowiredAnnotationBeanPostProcessor 对象   | 处理 @Autowired 以及 @Value注解                       |
| org.springframework.context.annotation.internalCommonAnnotationProcessor | CommonAnnotationBeanPostProcessor 对象      | （条件激活）处理 JSR-250 注解，如 @PostConstruct 等   |
| org.springframework.context.event.internalEventListenerProcessor | EventListenerMethodProcessor 对象           | 处理标注 @EventListener 的Spring 事件监听方法         |
| org.springframework.context.event.internalEventListenerFactory | DefaultEventListenerFactory 对 象           | @EventListener 事件监听方法适配为 ApplicationListener |
| org.springframework.context.annotation.internalPersistenceAnnotationProcessor | PersistenceAnnotationBeanPostProcessor 对象 | （条件激活）处理 JPA 注解场景                         |

#### Spring 內建单例对象

| **Bean 名称**               | **Bean 实例**                    | **使用场景**            |
| --------------------------- | -------------------------------- | ----------------------- |
| environment                 | Environment 对象                 | 外部化配置以及 Profiles |
| systemProperties            | java.util.Properties 对象        | Java 系统属性           |
| systemEnvironment           | java.util.Map 对象               | 操作系统环境变量        |
| messageSource               | MessageSource 对象               | 国际化文案              |
| lifecycleProcessor          | LifecycleProcessor 对象          | Lifecycle Bean 处理器   |
| applicationEventMulticaster | ApplicationEventMulticaster 对象 | Spring 事件广播器       |

### Spring 容器管理和游离对象

| 来源                  | Spring Bean对象 | 生命周期管理 | 配置元信息 | 使用场景           |
| --------------------- | --------------- | ------------ | ---------- | ------------------ |
| Spring BeanDefinition | 是              | 是           | 有         | 依赖查找，依赖注入 |
| 单体对象              | 是              | 否           | 无         | 依赖查找，依赖注入 |
| Resolvable Dependency | 否              | 否           | 无         | 依赖注入           |

### Spring BeanDefinition 作为依赖来源

- 要素
  - 元数据：BeanDefinition
  - 注册：BeanDefinitionRegistry#registerBeanDefinition
  - 类型：延迟和非延迟
  - 顺序：Bean 生命周期顺序按照注册顺序

### 单例对象作为依赖来源

- 要素
  - 来源：外部普通 Java 对象（不一定是 POJO） 
  - 注册：SingletonBeanRegistry#registerSingleton
- 限制
  - 无生命周期管理
  - 无法实现延迟初始化 Bean

### 非Spring 容器管理对象作为依赖来源

- 要素
  - 注册：ConfigurableListableBeanFactory#registerResolvableDependency
- 限制
  - 无生命周期管理
  - 无法实现延迟初始化 Bean
  - 无法通过依赖查找

### 外部化配置作为依赖来源

- 要素 
  - 类型：非常规 Spring 对象依赖来源
- 限制
  - 无生命周期管理
  - 无法实现延迟初始化 Bean
  - 无法通过依赖查找

## 八、Spring Bean 作用域

### Spring Bean 作用域

| 来源        | 说明                                                       |
| ----------- | ---------------------------------------------------------- |
| singleton   | 默认 Spring Bean 作用域，一个 BeanFactory 有且仅有一个实例 |
| prototype   | 原型作用域，每次依赖查找和依赖注入生成新 Bean 对象         |
| request     | 将 Spring Bean 存储在 ServletRequest 上下文中              |
| session     | 将 Spring Bean 存储在 HttpSession 中                       |
| application | 将 Spring Bean 存储在 ServletContext 中                    |

### "prototype" Bean 作用域

- 注意事项
  - Spring 容器没有办法管理 prototype Bean 的完整生命周期，也没有办法记录示例的存在。销毁回调方法将不会执行，可以利用 BeanPostProcessor 进行清扫工作。

### "request" Bean 作用域

- 配置
  - XML - <bean class= "..." scope = "request" />
  - Java 注解 - @RequestScope 或 @Scope(WebApplicationContext.SCOPE_REQUEST)
- 实现
  - API - RequestScope

### "session" Bean 作用域

- 配置
  - XML - <bean class= "..." scope = "session" />
  - Java 注解 - @RequestScope 或 @Scope(WebApplicationContext.SCOPE_SESSION)
- 实现
  - API - SessionScope

### "application" Bean 作用域

- 配置
  - XML - <bean class= "..." scope = "application" />
  - Java 注解 - @ApplicationScope 或 @Scope(WebApplicationContext.SCOPE_APPLICATION)
- 实现
  - API - ApplicationScope

### 自定义 Bean 作用域

- 实现 Scope

  - org.springframework.beans.factory.config.Scope

- 注册 Scope

  - API - org.springframework.beans.factory.config.ConfigurableBeanFactory#registerScope

  - 配置

  - ```xml
    <bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
      <property name="scopes">
        <map>
          <entry key="...">
          </entry>
        </map>
      </property>
    </bean>
    ```

### 课外资料

Spring Cloud RefreshScope 是如何控制 Bean 的动态刷新？

## 九、Spring Bean 生命周期

### Spring Bean 元信息配置阶段

- BeanDefinition 配置
  - 面向资源
    - XML 配置
    - Properties 资源配置
  - 面向注解
  - 面向 API

### Spring Bean 元信息解析阶段

- 面向资源 BeanDefinition 解析
  - BeanDefinitionReader
  - XML 解析器 - BeanDefinitionParser
- 面向注解 BeanDefinition 解析
  - AnnotatedBeanDefinitionReader

### Spring Bean 注册阶段

- BeanDefinition 注册接口
  - BeanDefinitionRegistry

### Spring BeanDefinition 合并阶段

- BeanDefinition 合并
  - 父子 BeanDefinition 合并
    - 当前 BeanFactory 查找 
    - 层次性 BeanFactory 查找

### Spring Bean Class 加载阶段

- ClassLoader 类加载
- Java Security 安全控制
- ConfigurableBeanFactory 临时 ClassLoader

### Spring Bean 实例化前阶段

- 非主流生命周期 - Bean 实例化前阶段
  - InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation

### Spring Bean 实例化阶段

- 实例化方式
  - 传统实例化方式
    - 实例化策略 - InstantiationStrategy
  - 构造器依赖注入

### Spring Bean 实例化后阶段

- Bean 属性赋值（Populate）判断
  - InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation

### Spring Bean 属性赋值前阶段

- Bean 属性值元信息
  - PropertyValues
- Bean 属性赋值前回调
  - Spring 1.2 - 5.0：InstantiationAwareBeanPostProcessor#postProcessPropertyValues
  - Spring 5.1：InstantiationAwareBeanPostProcessor#postProcessProperties

### Spring Bean Aware 接口回调阶段

Spring Aware 接口

- BeanNameAware
- BeanClassLoaderAware
- BeanFactoryAware
- EnvironmentAware
- EmbeddedValueResolverAware
- ResourceLoaderAware
- ApplicationEventPublisherAware
- MessageSourceAware
- ApplicationContextAware

### Spring Bean 初始化前阶段

- 已完成
  - Bean 实例化
  - Bean 属性赋值
  - Bean Aware 接口回调
- 方法回调
  - BeanPostProcessor#postProcessBeforeInitialization

### Spring Bean 初始化阶段

Bean 初始化（Initialization） 

- @PostConstruct 标注方法
- 实现 InitializingBean 接口的 afterPropertiesSet() 方法
- 自定义初始化方法

### Spring Bean 初始化后阶段

方法回调

- BeanPostProcessor#postProcessAfterInitialization

### Spring Bean 初始化完成阶段

方法回调

- Spring 4.1 +：SmartInitializingSingleton#afterSingletonsInstantiated

### Spring Bean 销毁前阶段

方法回调

- DestructionAwareBeanPostProcessor#postProcessBeforeDestruction

### Spring Bean 销毁阶段

Bean 销毁（Destroy） 

- @PreDestroy 标注方法
- 实现 DisposableBean 接口的 destroy() 方法
- 自定义销毁方法

### Spring Bean 垃圾收集

Bean 垃圾回收（GC） 

- 关闭 Spring 容器（应用上下文）
- 执行 GC
- Spring Bean 覆盖的 finalize() 方法被回调

## 十、Spring 配置元信息

### 配置元信息

- Spring Bean 配置元信息 - BeanDefinition
- Spring Bean 属性元信息 - PropertyValues
- Spring 容器配置元信息
- Spring 外部化配置元信息 - PropertySource
- Spring Profile 元信息 - @Profile

### Spring Bean 配置元信息

- GenericBeanDefinition：通用型 BeanDefinition 
- RootBeanDefinition：无 Parent 的 BeanDefinition 或者合并后 BeanDefinition 
- AnntatedBeanDefinition：注解标注的 BeanDefinition

### Spring Bean 属性元信息

- Bean 属性元信息 - PropertyValues
  - 可修改实现 - MutablePropertyValues
  - 元素成员 - PropertyValue
- Bean 属性上下文存储 - AttributeAccessor 
- Bean 元信息元素 - BeanMetadataElement

### Spring 容器配置元信息

#### Spring XML 配置元信息 - beans 元素相关

| **beans 元素属性**          | **默认值**   | **使用场景**                                                 |
| --------------------------- | ------------ | ------------------------------------------------------------ |
| profile                     | null（留空） | Spring Profiles 配置值                                       |
| default-lazy-init           | default      | 当 outter beans “default-lazy-init” 属性存在时，继承该值，否则为“false” |
| default-merge               | default      | 当 outter beans “default-merge” 属性存在时，继承该值，否则为“false” |
| default-autowire            | default      | 当 outter beans “default-autowire” 属性存在时，继承该值，否则为“no” |
| default-autowire-candidates | null（留空） | 默认 Spring Beans 名称 pattern                               |
| default-init-method         | null（留空） | 默认 Spring Beans 自定义初始化方法                           |
| default-destroy-method      | null（留空） | 默认 Spring Beans 自定义销毁方法                             |

#### Spring XML 配置元信息 - 应用上下文相关

| **XML 元素**                     | **使用场景**                           |
| -------------------------------- | -------------------------------------- |
| <context:annotation-config />    | 激活 Spring 注解驱动                   |
| <context:component-scan />       | Spring @Component 以及自定义注解扫描   |
| <context:load-time-weaver />     | 激活 Spring LoadTimeWeaver             |
| <context:mbean-export />         | 暴露 Spring Beans 作为 JMX Beans       |
| <context:mbean-server />         | 将当前平台作为 MBeanServer             |
| <context:property-placeholder /> | 加载外部化配置资源作为 Spring 属性配置 |
| <context:property-override />    | 利用外部化配置资源覆盖 Spring 属性值   |

### 基于 XML 资源装载 Spring Bean 配置元信息

#### Spring Bean 配置元信息

| **XML 元素**     | **使用场景**                                  |
| ---------------- | --------------------------------------------- |
| <beans:beans />  | 单 XML 资源下的多个 Spring Beans 配置         |
| <beans:bean />   | 单个 Spring Bean 定义（BeanDefinition）配置   |
| <beans:alias />  | 为 Spring Bean 定义（BeanDefinition）映射别名 |
| <beans:import /> | 加载外部 Spring XML 配置资源                  |

> 底层实现 - XmlBeanDefinitionReader

### 基于 Properties 资源装载 Spring Bean 配置元信息

#### Spring Bean 配置元信息

| **Properties 属性名** | **使用场景**                    |
| --------------------- | ------------------------------- |
| (class)               | Bean 类全称限定名               |
| (abstract)            | 是否为抽象的 BeanDefinition     |
| (parent)              | 指定 parent BeanDefinition 名称 |
| (lazy-init)           | 是否为延迟初始化                |
| (ref)                 | 引用其他 Bean 的名称            |
| (scope)               | 设置 Bean 的 scope 属性         |
| ${n}                  | n 表示第 n+1 个构造器参数       |

> 底层实现 - PropertiesBeanDefinitionReader

### 基于 Java 注解装载 Spring Bean 配置元信息

#### Spring 模式注解

| **Spring 注解** | **场景说明**       | **起始版本** |
| --------------- | ------------------ | ------------ |
| @Repository     | 数据仓储模式注解   | 2.0          |
| @Component      | 通用组件模式注解   | 2.5          |
| @Service        | 服务模式注解       | 2.5          |
| @Controller     | Web 控制器模式注解 | 2.5          |
| @Configuration  | 配置类模式注解     | 3.0          |

#### Spring Bean 定义注解

| **Spring 注解** | **场景说明**                                    | **起始版本** |
| --------------- | ----------------------------------------------- | ------------ |
| @Bean           | 替换 XML 元素 <bean>                            | 3.0          |
| @DependsOn      | 替代 XML 属性 <bean depends-on="..."/>          | 3.0          |
| @Lazy           | 替代 XML 属性 <bean lazy-init="true\|falses" /> | 3.0          |
| @Primary        | 替换 XML 元素 <bean primary="true\|false" />    | 3.0          |
| @Role           | 替换 XML 元素 <bean role="..." />               | 3.1          |
| @Lookup         | 替代 XML 属性 <bean lookup-method="...">        | 4.1          |

#### Spring Bean 依赖注入注解

| **Spring 注解** | **场景说明**                        | **起始版本** |
| --------------- | ----------------------------------- | ------------ |
| @Autowired      | Bean 依赖注入，支持多种依赖查找方式 | 2.5          |
| @Qualifier      | 细粒度的 @Autowired 依赖查找        | 2.5          |

| **Java 注解** | **场景说明**      | **起始版本** |
| ------------- | ----------------- | ------------ |
| @Resource     | 类似于 @Autowired | 2.5          |
| @Inject       | 类似于 @Autowired | 2.5          |

#### Spring Bean 条件装配注解

| **Spring 注解** | **场景说明**   | **起始版本** |
| --------------- | -------------- | ------------ |
| @Profile        | 配置化条件装配 | 3.1          |
| @Conditional    | 编程条件装配   | 4.0          |

#### Spring Bean 生命周期回调注解

| **Spring 注解** | **场景说明**                                                 | **起始版本** |
| --------------- | ------------------------------------------------------------ | ------------ |
| @PostConstruct  | 替换 XML 元素 <bean init-method="..." /> 或InitializingBean  | 2.5          |
| @PreDestroy     | 替换 XML 元素 <bean destroy-method="..." /> 或DisposableBean | 2.5          |

### Spring Bean 配置元信息底层实现

#### Spring BeanDefinition 解析与注册

| **实现场景**    | **实现类**                     | **起始版本** |
| --------------- | ------------------------------ | ------------ |
| XML 资源        | XmlBeanDefinitionReader        | 1.0          |
| Properties 资源 | PropertiesBeanDefinitionReader | 1.0          |
| Java 注解       | AnnotatedBeanDefinitionReader  | 3.0          |

#### Spring XML 资源 BeanDefinition 解析与注册

- 核心 API - XmlBeanDefinitionReader
  - 资源 - Resource
  - 底层 - BeanDefinitionDocumentReader
    - XML 解析 - Java DOM Level 3 API
    - BeanDefinition 解析 - BeanDefinitionParserDelegate
    - BeanDefinition 注册 - BeanDefinitionRegistry

#### Spring Properties 资源 BeanDefinition 解析与注册

- 核心 API - PropertiesBeanDefinitionReader
- 资源
  - 字节流 - Resource
  - 字符流 - EncodedResouce
- 底层
  - 存储 - java.util.Properties
  - BeanDefinition 解析 - API 内部实现
  - BeanDefinition 注册 - BeanDefinitionRegistry

#### Spring Java 注册 BeanDefinition 解析与注册

- 核心 API - AnnotatedBeanDefinitionReader
- 资源
  - 类对象 - java.lang.Class
- 底层
  - 条件评估 - ConditionEvaluator
  - Bean 范围解析 - ScopeMetadataResolver
  - BeanDefinition 解析 - 内部 API 实现
  - BeanDefinition 处理 - AnnotationConfigUtils.processCommonDefinitionAnnotations
  - BeanDefinition 注册 - BeanDefinitionRegistry

### 基于 XML 资源装载 Spring IoC 容器配置元信息

#### Spring IoC 容器相关 XML 配置

| **命名空间** | **所属模块**   | **Schema 资源 URL**                                          |
| ------------ | -------------- | ------------------------------------------------------------ |
| beans        | spring-beans   | https://www.springframework.org/schema/beans/spring-beans.xsd |
| context      | spring-context | https://www.springframework.org/schema/context/spring-context.xsd |
| aop          | spring-aop     | https://www.springframework.org/schema/aop/spring-aop.xsd    |
| tx           | spring-tx      | https://www.springframework.org/schema/tx/spring-tx.xsd      |
| util         | spring-beans   | https://www.springframework.org/schema/util/spring-util.xsd  |
| tool         | spring-beans   | https://www.springframework.org/schema/tool/spring-tool.xsd  |

### 基于 Java 注解装载 Spring IoC 容器配置元信息

#### Spring IoC 容器装配注解

| **Spring 注解** | 场景说明                                    | 起始版本 |
| --------------- | ------------------------------------------- | -------- |
| @ImportResource | 替换 XML 元素 <import>                      | 3.0      |
| @Import         | 导入 Configuration Class                    | 3.0      |
| @ComponentScan  | 扫描指定 package 下标注 Spring 模式注解的类 | 3.1      |

#### Spring IoC 配属属性注解

| **Spring 注解**  | 场景说明                         | 起始版本 |
| ---------------- | -------------------------------- | -------- |
| @PropertySource  | 配置属性抽象 PropertySource 注解 | 3.1      |
| @PropertySources | @PropertySource 集合注解         | 4.0      |

### 基于 Extensible XML authoring 扩展 Spring XML 元素

Spring XML 扩展

- 编写 XML Schema 文件：定义 XML 结构
- 自定义 NamespaceHandler 实现：命名空间绑定
- 自定义 BeanDefinitionParser 实现：XML 元素与 BeanDefinition 解析
- 注册 XML 扩展：命名空间与 XML Schema 映射

### Extensible XML authoring 扩展原理

#### 触发时机

- AbstractApplicationContext#obtainFreshBeanFactory
  - AbstractRefreshableApplicationContext#refreshBeanFactory
    - AbstractXmlApplicationContext#loadBeanDefinitions
      - XmlBeanDefinitionReader#doLoadBeanDefinitions
        - BeanDefinitionParserDelegate#parseCustomElement

#### 核心流程

- BeanDefinitionParserDelegate#parseCustomElement(org.w3c.dom.Element, BeanDefinition)
  - 获取 namespace
  - 通过 namespace 解析 NamespaceHandler
  - 构造 ParserContext
  - 解析元素，获取 BeanDefinintion

### 基于 Properties 资源装载外部化配置

- 注解驱动
  - @org.springframework.context.annotation.PropertySource
  - @org.springframework.context.annotation.PropertySources
- API 编程
  - org.springframework.core.env.PropertySource
  - org.springframework.core.env.PropertySources

### 基于 YAML 资源装载外部化配置

- API 编程
  - org.springframework.beans.factory.config.YamlProcessor
  - org.springframework.beans.factory.config.YamlMapFactoryBean
  - org.springframework.beans.factory.config.YamlPropertiesFactoryBean









## 面试题

#### 什么是 Spring Framework？

Spring makes it easy to create Java enterprise applications. It provides everything you need to embrace the Java language in an enterprise environment, with support for Groovy and Kotlin as alternative languages on the JVM, and with the flexibility to create many kinds of architectures depending on an application’s needs.

#### Spring Framework 有哪些核心模块？

- spring-core：Spring 基础 API 模块，如资源管理，泛型处理
- spring-beans：Spring Bean 相关，如依赖查找，依赖注入
- spring-aop : Spring AOP 处理，如动态代理，AOP 字节码提升
- spring-context : 事件驱动、注解驱动，模块驱动等
- spring-expression：Spring 表达式语言模块

#### 什么是 IoC ？

简单地说，IoC 是反转控制，类似于好莱坞原则，主要有依赖查找和依赖注入实现

#### 依赖查找和依赖注入的区别？

依赖查找是主动或手动的依赖查找方式，通常需要依赖容器或标准 API 实现。而依赖注入则是手动或自动依赖绑定的方式，无需依赖特定的容器和API

#### Spring 作为 IoC 容器有什么优势？

- 典型的 IoC 管理，依赖查找和依赖注入
- AOP 抽象
- 事务抽象
- 事件机制
- SPI 扩展
- 强大的第三方整合
- 易测试性
- 更好的面向对象

#### 什么是 Spring IoC 容器？

Spring Framework implementation of the Inversion of Control (IoC) principle. IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies (that is, the other objects they work with) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean.

#### BeanFactory 与 FactoryBean？

- BeanFactory 是 IoC 底层容器
- FactoryBean 是 创建 Bean 的一种方式，帮助实现复杂的初始化逻辑

#### Spring IoC 容器启动时做了哪些准备？

- IoC 配置元信息读取和解析
- IoC 容器生命周期
- Spring 事件发布
- 国际化

#### 如何注册一个 Spring Bean？

通过 BeanDefinition 和外部单体对象来注册

#### 什么是 Spring BeanDefinition？

#### Spring 容器是怎样管理注册 Bean

#### ObjectFactory 与 BeanFactory 的区别？

- ObjectFactory 与 BeanFactory 均提供依赖查找的能力。
- 不过 ObjectFactory 仅关注一个或一种类型的 Bean 依赖查找，并且自身不具备依赖查找的能力，能力则由 BeanFactory 输出。
- BeanFactory 则提供了单一类型、集合类型以及层次性等多种依赖查找方式。

#### BeanFactory.getBean 操作是否线程安全？

BeanFactory.getBean 方法的执行是线程安全的，超过过程中会增加互斥锁

#### Spring 依赖查找与注入在来源上的区别?

#### 有多少种依赖注入的方式？

- 构造器注入
- Setter 注入
- 字段注入
- 方法注入
- 接口回调注入

#### 你偏好构造器注入还是 Setter 注入？

两种依赖注入的方式均可使用，如果是必须依赖的话，那么推荐使用构造器注入，Setter 注入用于可选依赖

#### Spring 依赖注入的来源有哪些？

- Spring BeanDefinition
- 单例对象
- Resolvable Dependency
- @Value 外部化配置

#### 注入和查找的依赖来源是否相同？

否，依赖查找的来源仅限于 Spring BeanDefinition 以及单例对象，而依赖注入的来源还包括 Resolvable Dependency 以及@Value 所标注的外部化配置

#### 单例对象能在 IoC 容器启动后注册吗？

可以的，单例对象的注册与 BeanDefinition 不同，BeanDefinition 会被 ConfigurableListableBeanFactory#freezeConfiguration() 方法影响，从而冻结注册，单例对象则没有这个限制。

#### Spring 內建的 Bean 作用域有几种？

singleton、prototype、request、session、application 以及websocket

#### singleton Bean 是否在一个应用是唯一的？

否，singleton bean 仅在当前 Spring IoC 容器（BeanFactory）中是单例对象

#### “application”Bean 是否被其他方案替代

可以的，实际上，“application” Bean 与“singleton” Bean 没有本质区别

#### BeanPostProcessor 的使用场景有哪些？

- BeanPostProcessor 提供 Spring Bean 初始化前和初始化后的生命周期回调，分别对应 postProcessBeforeInitialization 以及postProcessAfterInitialization 方法，允许对关心的 Bean 进行扩展，甚至是替换。
- 加分项：其中，ApplicationContext 相关的 Aware 回调也是基于BeanPostProcessor 实现，即 ApplicationContextAwareProcessor

#### BeanFactoryPostProcessor 与BeanPostProcessor 的区别

- BeanFactoryPostProcessor 是 Spring BeanFactory（实际为ConfigurableListableBeanFactory） 的后置处理器，用于扩展BeanFactory，或通过 BeanFactory 进行依赖查找和依赖注入。
- 加分项：BeanFactoryPostProcessor 必须有 Spring ApplicationContext 执行，BeanFactory 无法与其直接交互。而 BeanPostProcessor 则直接与BeanFactory 关联，属于 N 对 1 的关系。

#### BeanFactory 是怎样处理 Bean 生命周期？

BeanFactory 的默认实现为 DefaultListableBeanFactory，其中 Bean生命周期与方法映射如下：

- BeanDefinition 注册阶段 - registerBeanDefinition
- BeanDefinition 合并阶段 - getMergedBeanDefinition
- Bean 实例化前阶段 - resolveBeforeInstantiation
- Bean 实例化阶段 - createBeanInstance
- Bean 实例化后阶段 - populateBean
- Bean 属性赋值前阶段 - populateBean
- Bean 属性赋值阶段 - populateBean
- Bean Aware 接口回调阶段 - initializeBean
- Bean 初始化前阶段 - initializeBean
- Bean 初始化阶段 - initializeBean
- Bean 初始化后阶段 - initializeBean
- Bean 初始化完成阶段 - preInstantiateSingletons
- Bean 销毁前阶段 - destroyBean
- Bean 销毁阶段 - destroyBean

#### Spring 內建 XML Schema 常见有哪些？

#### Spring配置元信息具体有哪些？

- Bean 配置元信息：通过媒介（如 XML、Proeprties 等），解析 BeanDefinition
- IoC 容器配置元信息：通过媒介（如 XML、Proeprties 等），控制 IoC 容器行为，比如注解驱动、AOP 等 
- 外部化配置：通过资源抽象（如 Proeprties、YAML 等），控制 PropertySource
- Spring Profile：通过外部化配置，提供条件分支流程

#### Extensible XML authoring 的缺点？

- 高复杂度：开发人员需要熟悉 XML Schema，spring.handlers，spring.schemas 以及 Spring API 。 
- 嵌套元素支持较弱：通常需要使用方法递归或者其嵌套解析的方式处理嵌套（子）元素。 
- XML 处理性能较差：Spring XML 基于 DOM Level 3 API 实现，该 API 便于理解，然而性能较差。
- XML 框架移植性差：很难适配高性能和便利性的 XML 框架，如 JAXB。