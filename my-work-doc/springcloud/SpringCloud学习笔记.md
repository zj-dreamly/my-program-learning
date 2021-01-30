### 微服务架构的基础框架/组件

- 服务注册发现
- 服务网关（Service Gateway）
- 后台通用服务（也称中间层服务 Middle Tier Service）
- 前端服务（也称边缘化服务Edge Service）

### SpringCloud是什么

#### SpringCloud是一个开发工具集，包含了多个子项目

- 利用SpringBoot的开发便利
- 主要是基于对Netflix开源组件的进一步封装

#### Spring Cloud简化了分布式开发

### Spring Cloud Eureka

- 基于Netflix Eureka做了2次封装

- 两个组件构成

  Eureka Server 注册中心

  Eureka Client 服务注册

####  Eureka高可用

#### Eureka总结

- @EnableEurekaServer @EnableEurekaClient
- 心跳检测，健康检测，负载均衡等功能
- Eureka的高可用，生产上建议至少2台以上
- 分布式系统中，服务注册中心是最重要的基础部分

分布式系统中为什么需要服务注册中心

#### 服务发现的2种方式

客户端发现

服务端发现

### 适合上微服务吗？

业务形态不适合的

业务中包含很多强事务场景

业务相对稳定，迭代周期长

访问压力不大，可用性不高

#### 康威定律

####  服务拆分方法论

单一职责，松耦合，高内聚

每个微服务都有独立的数据存储

依据服务特点选择不同结构的数据库

针对边界设计API

依据边界权衡数据冗余

关注点分离

- 按职责

- 按通用性

- 按粒度级别

服务和数据的关系

- 先考虑业务功能，再考虑数据
- 无状态服务

### 应用间通信

#### HTTP vs RPC

- Dubbo
- Spring Cloud

#### Spring Cloud服务间调用

- RestTemplate
- Feign

### 客户端负载据衡器

Ribbon

- 服务发现
- 服务选择规则
- 服务监听
- ServerList
- IRule
- ServerListFilter

### Feign

声明式REST客户端（伪RPC）

采用了基于接口的注解

#### 使用

1. 配置pom

   ```xml
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-openfeign</artifactId>
           </dependency>
   ```

2. 配置启动类

   ```java
   @SpringBootApplication
   @EnableDiscoveryClient
   public class ProductApplication {
   
      public static void main(String[] args) {
         SpringApplication.run(ProductApplication.class, args);
      }
   }
   ```

3. 配置client类

   ```java
   @FeignClient(name = "product", fallback = ProductClient.ProductClientFallback.class)
   public interface ProductClient {
   
       @PostMapping("/product/listForOrder")
       List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);
   
       @PostMapping("/product/decreaseStock")
       void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);
   
       @Component
       static class ProductClientFallback implements ProductClient {
   
           @Override
           public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
               return null;
           }
   
           @Override
           public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
   
           }
       }
   }
   ```

### 统一配置中心（config）

为什么需要统一配置中心

- 不方便维护

- 配置内容安全与权限

- 更新配置项目需重启

- ```java
  @EnableConfigServer
  ```

#### SpringCloud Bus自动刷新配置

@RefreshScope

webhooks

### RabbitMQ使用

```java

    //1. @RabbitListener(queues = "myQueue")
    //2. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3. 自动创建, Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message) {
        log.info("MqReceiver: {}", message);
    }
```

### Spring Cloud Stream使用

简化消息队列开发

group分组                 

Redis分布式锁

### 服务网关

常用方案

- Nginx + Lua
- Tyk
- Kong
- Spring Cloud Zuul

zuul特点

路由 + 过滤器 = Zuul

核心是一系列的过滤器

- 前置（Pre）

  限流 

  鉴权 

  参数校验调整

- 后置（Post）

  统计

  日志

- 路由（Route）

- 错误（Error）

#### Zuul的高可用

- 多个Zuul节点注册到Eureka Server
- Nginx和Zuul混搭

#### Zuul限流

时机：请求被转发之前调用

使用guava实现令牌桶算法

#### Zuul权限校验

在前置过滤器中实现相关逻辑

分布式Session Vs Oauth2

#### Zull跨域

在被调用的类或者方法上添加@CrossOrigin注解

在Zuul中添加CorsFulter过滤器

### 服务容错

#### Spring Cloud Hystrix

- 基于Netflix对应的Hystrix

- 服务降级

  优先核心服务，非核心服务不可用或弱可用

  通过HystrixCommand注解指定

  fallbackMethod（回退函数）中具体实现降级逻辑

- 服务熔断

  Circuit Breaker：断路器

  ```java
  @HystrixCommand(commandProperties = {
      @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  				//设置熔断
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),	//请求数达到后才计算
      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),	//错误率
  })
  @GetMapping("/getProductInfoList")
  public String getProductInfoList(@RequestParam("number") Integer number) {
  ...
  }
  ```

  feign中整合hystrix

- 依赖隔离

  线程池隔离

  Hystrix自动实现了依赖隔离

- 监控（Hystrix Dashboard）

### 服务追踪

链路监控

分布式追踪系统

- 数据采集
- 数据存储
- 查询展示

#### OpenTracing规范

- 来自大名鼎鼎的CNCF
- 实现：ZIPKIN，TRACER，JAEGER，GRPC
- Spring Cloud Sleuth
- ZIPKIN图形化界面

### 微服务部署

#### docker部署第一个应用

自定义docker file

```dockerfile
FROM hub.c.163.com/library/java:8-alpine

MAINTAINER XXX XXX@imooc.com

ADD target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

构建镜像

```shell
mvn clean package -Dmaven.test.skip=true
docker build -t springcloud/eureka .
```

创建容器

```shell
docker run -p 8761:8761 -d springcloud/eureka
```

#### rancher的安装

<https://www.cnrancher.com/docs/>

#### docker部署微服务

网易云镜像仓库服务

##### 部署config

```shell
mvn clean package -Dmaven.test.skip=true
docker build -t springcloud/config .
```

htop监控cpu

```shell
yum install htop
```

### Graylog-日志管理



   