## SpringIOC初始化

### see：

```properties
org.springframework.context.annotation.AnnotationConfigApplicationContext#AnnotationConfigApplicationContext(java.lang.Class<?>...)
```

### prepareRefresh()

- 定义：刷新前的预处理
- `initPropertySources()`：初始化一些属性设置，子类自定义个性化的属性设置方法
- `getEnvironment().validateRequiredProperties()`：检验属性的合法等
- `earlyApplicationEvents = new LinkedHashSet<ApplicationEvent>()`：保存容器中的一些早期的事件

### obtainFreshBeanFactory()

- 定义：获取BeanFactory

### prepareBeanFactory(beanFactory);

- 定义：BeanFactory的预准备工作
- 设置BeanFactory的类加载器、支持表达式解析器...
- 添加部分BeanPostProcessor：`ApplicationContextAwareProcessor`
- 设置忽略的自动装配的接口EnvironmentAware、EmbeddedValueResolverAware、xxx
- 注册可以解析的自动装配，我们能直接在任何组件中自动注入：BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext
- 添加BeanPostProcessor：ApplicationListenerDetector
- 添加编译时的AspectJ
- 给BeanFactory中注册一些能用的组件

> environment【ConfigurableEnvironment】
>
> systemProperties【Map<String, Object>】
>
> systemEnvironment【Map<String, Object>】

### postProcessBeanFactory(beanFactory)

- 定义：BeanFactory准备工作完成后进行的后置处理工作
- 子类通过重写这个方法来在BeanFactory创建并预准备完成以后做进一步的设置

### invokeBeanFactoryPostProcessors(beanFactory)

#### 执行BeanFactoryPostProcessor的方法

   BeanFactoryPostProcessor：BeanFactory的后置处理器

  两个接口：BeanFactoryPostProcessor、BeanDefinitionRegistryPostProcessor

#### 一、执行BeanDefinitionRegistryPostProcessor

1. 获取所有的BeanDefinitionRegistryPostProcessor
2. 执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor、postProcessor.postProcessBeanDefinitionRegistry(registry)
3. 执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessorpostProcessor.postProcessBeanDefinitionRegistry(registry)
4. 执行没有实现任何优先级或者是顺序接口的BeanDefinitionRegistryPostProcessorspostProcessor.postProcessBeanDefinitionRegistry(registry)

####   二、执行BeanFactoryPostProcessor的方法

1. 获取所有的BeanFactoryPostProcessor
2. 执行实现了PriorityOrdered优先级接口的BeanFactoryPostProcessor、postProcessor.postProcessBeanFactory()
3. 执行实现了Ordered顺序接口的BeanFactoryPostProcessor
4. postProcessor.postProcessBeanFactory()
5. 执行没有实现任何优先级或者是顺序接口的BeanFactoryPostProcessor
   postProcessor.postProcessBeanFactory()     

### 6、registerBeanPostProcessors(beanFactory)

- 定义：注册BeanPostProcessor
- 不同接口类型的BeanPostProcessor，在Bean创建前后的执行时机是不一样的

> DestructionAwareBeanPostProcessor
>
> InstantiationAwareBeanPostProcessor
>
> SmartInstantiationAwareBeanPostProcessor
>
> MergedBeanDefinitionPostProcessor
>
> internalPostProcessors

1. 获取所有的 BeanPostProcessor，后置处理器都默认可以通过PriorityOrdered、Ordered接口来执行优先级
2. 注册PriorityOrdered优先级接口的BeanPostProcessor，把每一个BeanPostProcessor；添加到BeanFactory中
3. 注册Ordered接口的
4. 注册没有实现任何优先级接口的
5. 注册MergedBeanDefinitionPostProcessor
6. 注册一个ApplicationListenerDetector用于Bean创建完成后检查是否是ApplicationListener，如果是就执行
   applicationContext.addApplicationListener((ApplicationListener<?>) bean);

### initMessageSource()

1. 定义：初始化MessageSource组件（做国际化功能，消息绑定，消息解析）
2. 获取BeanFactory
3. 看容器中是否有id为messageSource的，类型是MessageSource的组件，如果有赋值给messageSource，如果没有自己创建一个DelegatingMessageSource
4. MessageSource：取出国际化配置文件中的某个key的值，可以按照区域信息获取
5. 把创建好的MessageSource注册在容器中，以后获取国际化配置文件的值的时候，可以自动注入

### initApplicationEventMulticaster()

定义：初始化事件派发器

1. 获取BeanFactory
2. 从BeanFactory中获取applicationEventMulticaster的ApplicationEventMulticaster
3. 如果上一步没有配置；创建一个SimpleApplicationEventMulticaster
4. 将创建的ApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入

### onRefresh()

 定义：子类重写这个方法，在容器刷新的时候可以自定义逻辑

### registerListeners()

定义：给容器中将所有项目里面的ApplicationListener注册进来

1. 从容器中拿到所有的ApplicationListener
2. 将每个监听器添加到事件派发器中：getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName)
3. 派发之前步骤产生的事件

### finishBeanFactoryInitialization(beanFactory)

定义：初始化所有剩下的单实例bean；

### finishRefresh()

定义：完成BeanFactory的初始化创建工作；IOC容器就创建完成

### 总结
#### Spring容器在启动的时候，先会保存所有注册进来的Bean的定义信息

- xml注册bean
- 注解注册Bean；@Service、@Component、@Bean、xxx

#### Spring容器会合适的时机创建这些Bean

- 用到某个bean的时候，利用getBean创建bean，创建好以后保存在容器中
- 统一创建剩下所有的bean的时候：finishBeanFactoryInitialization()

#### 后置处理器：BeanPostProcessor

- 每一个bean创建完成，都会使用各种后置处理器进行处理，来增强bean的功能：
- 处理自动注入：AutowiredAnnotationBeanPostProcessor
- AOP：AnnotationAwareAspectJAutoProxyCreator
- 增强的功能注解：AsyncAnnotationBeanPostProcessor
           

#### 事件驱动模型；

-   ApplicationListener：事件监听
-   ApplicationEventMulticaster：事件派发