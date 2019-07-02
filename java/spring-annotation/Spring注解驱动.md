## Spring组件注册

#### `AnnotationConfigApplicationContext`

#### `ClassPathXmlApplicationContext`

#### `ApplicationContext`

```java
//从配置文件读取Bean
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");
//按照id装配
Person bean = (Person) applicationContext.getBean("person");
```

```java
//从配置类读取Bean
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig1.class);
//按照类型装配
Person bean = applicationContext.getBean(Person.class);
```

### @ComponentScan

- 定义：指定扫描包的路径
- 作用范围：可标注在类上

```java
@Configuration
@ComponentScans(
		value = {
				@ComponentScan(value="com.github.zj.dreamly",includeFilters = {
						@Filter(type=FilterType.ANNOTATION,classes={Controller.class}),
						@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={BookService.class}),
						@Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
				},useDefaultFilters = false)	
		}
		)
public class MainConfig {
	
	//给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
	@Bean
	public Person person(){
		return Person.builder()
			.age(25)
			.name("zj")
			.build();
	}
```

- @`ComponentScan`  value:指定要扫描的包
- `excludeFilters` = Filter[] ：指定扫描的时候按照什么规则排除那些组件
- `includeFilters` = Filter[] ：指定扫描的时候只需要包含哪些组件
- `FilterType.ANNOTATION`：按照注解
- `FilterType.ASSIGNABLE_TYPE`：按照给定的类型
- `FilterType.ASPECTJ`：使用`ASPECTJ`表达式
- `FilterType.REGEX`：使用正则指定
- `FilterType.CUSTOM`：使用自定义规则，需要实现`TypeFilter`接口

### @Scope

- 定义：对象在spring容器（IOC容器）中的生命周期，也可以理解为对象在spring容器中的创建方式。
- 作用范围：可标注在方法上

- `singleton`：单例模式，全局有且仅有一个实例
- `prototype`：原型模式，每次获取Bean的时候会有一个新的实例
- `request`：表示针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效
- `session`：表示针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
- `globalsession`：类似于标准的HTTP Session作用域，不过它仅仅在基于portlet的web应用中才有意义

### @Lazy

- 定义：懒加载，单实例bean默认在容器启动的时候创建对象，适用`@lazy`，容器启动不创建对象。第一次使用(获取)Bean时，创建对象，并初始化
- 作用范围：可标注在方法上

### @Conditional

- 定义：根据一些自定义的条件动态的选择是否加载该bean到springIOC容器中
- 作用范围：可标注在类上或方法上，标注在类上代表满足当前条件，这个类中配置的所有bean注册才能生效
- 使用@Conditional注解需要实现Condition接口

### @Import

- 定义：通过导入的方式实现把实例加入springIOC容器中
- 作用范围：可标注在类上

#### 3种使用方式

1. 基于`Configuration`，也就是直接填对应的class数组，默认注册，bean的id为全类名
2. 基于自定义`ImportSelector`，返回需要导入的组件的全类名数组，需要实现`ImportSelector`接口
3. 基于`ImportBeanDefinitionRegistrar`，手动注册bean到容器中，需要实现`ImportBeanDefinitionRegistrar`接口

### FactoryBean

- 定义：Spring提供的 FactoryBean（工厂Bean）
- 默认获取到的是工厂bean调用getObject创建的对象
- 要获取工厂Bean本身，我们需要给id前面加一个&
- 使用FactoryBean需要实现`FactoryBean`接口，泛型为要注册的Bean

## Spring组件生命周期

### 指定初始化和销毁逻辑

#### 使用@Bean

- 在bean中指定`init`和`detroy`方法，并在注解上关联

#### 实现`spring`提供的接口

- 实现`InitializingBean`接口来自定义初始化逻辑
- 实现`DisposableBean`接口来自定义销毁逻辑

#### 使用`JSR250`标准

*      `@PostConstruct`：在bean创建完成并且属性赋值完成后执行
*     `@PreDestroy`：在容器销毁bean之前执行

#### 使用`BeanPostProcessor`后置处理器

实现`BeanPostProcessor`

- `postProcessBeforeInitialization`：初始化之前
- `postProcessAfterInitialization`：初始化之后

#### Spring底层对`BeanPostProcessor`的使用

- `AsyncAnnotationBeanPostProcessor`
- `AutowiredAnnotationBeanPostProcessor`
- `BeanValidationPostProcessor`
- `InitDestroyAnnotationBeanPostProcessor`

#### 如何在某个类中使用`IOC`容器

- 实现`ApplicationContextAware`接口
- 重写`setApplicationContext`，设置`applicationContext`

#### 总结

自定义组件想要使用Spring容器底层的一些组件：ApplicationContext，BeanFactory，xxx等，可以实现对应的xxxAware，在创建对象的时候，会调用接口规定的方法注入相关组件，它的底层实现就是使用了`BeanPostProcessor`
## Spring属性赋值

### @`value`

- 可以直接赋值（基本类型）
- 支持Spring el表达式，#{}
- 支持${}取值

### @`PropertySource`

- 读取外部配置文件中的k/v保存到运行的环境变量中
- 加载完外部的配置文件以后使用${}取出配置文件的值

## Spring自动装配

- 定义：Spring利用依赖注入（DI），完成对IOC容器中中各个组件的依赖关系赋值

### @Autowired

- @Autowired注解可适用于成员变量、方法和构造函数

- 默认优先按照类型去容器中找对应的组件：`applicationContext.getBean(BookDao.class)`
- 如果容器中存在多个相同类型的组件，再将属性的名称作为组件的id去容器中查找：`applicationContext.getBean("bookDao")`
- 自动装配默认一定要将属性赋值好，没有就会报错
- 使用@Autowired(required=false)来设置非必须装配

#### @Qualifier

- @Autowired与@Qualifier结合使用：明确指定需要装配的组件的id

#### @Primary

- 让Spring进行自动装配的时候，默认使用首选的bean
- 如果使用了@Qualifier指定需要装配的bean的id，则@Primary失效

### @Resource

- JSR250规范注解，需要导入javax.annotation实现注入
- 可以和@Autowired一样实现自动装配功能，默认按照组件名称进行装配，一般会指定一个name属性
- @Resource可以作用在变量、setter方法上
- 不支持@Primary功能
- 不支持@Autowired（reqiured=false）

### @Inject

- JSR330规范注解，需要导入javax.inject.Inject
- @Inject是根据**类型**进行自动装配的，如果需要按名称进行装配，则需要配合@Named
- @Inject可以作用在变量、setter方法、构造函数上
- 不支持@Autowired（reqiured=false）

## Spring外部化配置

### @Profile

- 定义：Spring提供的可以根据当前环境，动态的激活和切换一系列组件的功能
- 作用范围：类或方法上

* 加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中
* 默认是default环境
* 没有标注环境标识的bean在，任何环境下都是加载的

## Spring AOP

- `@EnableAspectJAutoProxy`：开启基于注解的aop模式
- 定义：指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
- 前置通知(@Before)：在目标方法运行之前运行
- 后置通知(@After)：在目标方法运行结束之后运行（无论方法正常结束还是异常结束）
- 返回通知(@AfterReturning)：在目标方法正常返回之后运行
- 异常通知(@AfterThrowing)：在目标方法出现异常以后运行
- 环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.procced()）

## Spring 声明式事务

- @EnableTransactionManagement
- @Transactional

## Spring扩展原理

- `BeanFactoryPostProcessor`
- `BeanDefinitionRegistryPostProcessor`
- `SmartInitializingSingleton`

