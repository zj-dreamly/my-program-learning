# 生工生物

[Activemq和Rocketmq如何解决MQ消息消费顺序问题](https://segmentfault.com/a/1190000014512075)

[Kafka消息怎么保证顺序消费](https://www.cnblogs.com/-courage/p/15252760.html)

[MVCC](https://www.cnblogs.com/xuwc/p/13873611.html)

https://mp.weixin.qq.com/s/W07ZIW0GRtsrS5nuiy2z_A

# 润和软件

**Spring MVC 执行过程**

1.  用户向服务器发送请求，请求被 Spring 前端控制 Servelt DispatcherServlet 捕获
2.  DispatcherServlet 对请求 URL 进行解析，得到请求资源标识符URI，然后根据该 URI 调用 HandlerMapping 获得该 Handler 配置的所有相关的对象，包括 Handler 对象以及 Handler 对象对应的拦截器，最后以 HandlerExecutionChain 对象的形式返回
3.  DispatcherServlet 根据获得的 Handler，选择一个合适的HandlerAdapter，如果成功获得 HandlerAdapter 后，此时将开始执行拦截器的 preHandler 方法 
4.  提取 Request 中的模型数据，填充 Handler入参，开始执行 Handler 即 Controller，在填充 Handler 的入参过程中，根据你的配置，Spring 将帮你做一些额外的工作
   - HttpMessageConveter： 将请求消息如Json、xml等数据转换成一个对象，将对象转换为指定的响应信息
   - 数据转换：对请求消息进行数据转换，如 String 转换成 Integer、Double 等
   - 数据格式化：对请求消息进行数据格式化，如将字符串转换成格式化数字或格式化日期等
   - 数据验证： 验证数据的有效性，长度、格式等，验证结果存储到 BindingResult 或 Error 中 
5.  Handler 执行完成后，向 DispatcherServlet 返回一个ModelAndView 对象
6.  根据返回的 ModelAndView，选择一个适合的 ViewResolver，必须是已经注册到 Spring 容器中的 ViewResolver 返回给DispatcherServlet 
7.  ViewResolver 结合 Model 和 View，来渲染视图
8.  将渲染结果返回给客户端

**Spring Bean的生命周期**

- 实例化 Bean 对象
- 设置 Bean 属性
- 如果我们通过各种 Aware 接口声明了依赖关系，则会注入 Bean 对容器基础设施层面的依赖。具体包括 BeanNameAware、BeanFactoryAware 和 ApplicationContextAware，分别会注入 Bean ID、Bean Factory 或者 ApplicationContext
- 调用 BeanPostProcessor 的前置初始化方法 postProcessBeforeInitialization
- 如果实现了 InitializingBean 接口，则会调用 afterPropertiesSet 方法
- 调用 Bean 自身定义的 init 方法
- 调用 BeanPostProcessor 的后置初始化方法 postProcessAfterInitialization
- 创建过程完毕

**WebSocket 事件**

| 事件    | 事件处理程序     | 描述                       |
| ------- | ---------------- | -------------------------- |
| open    | Socket.onopen    | 连接建立时触发             |
| message | Socket.onmessage | 客户端接收服务端数据时触发 |
| error   | Socket.onerror   | 通信发生错误时触发         |
| close   | Socket.onclose   | 连接关闭时触发             |



# 晨光

**MD5不可逆的原因**

MD5 不可逆的原因是其是一种散列函数，使用的是 hash 算法，在计算过程中原文的部分信息是丢失了的，一个 MD5 理论上的确是可能对应无数多个原文的，因为 MD5 是有限多个的，而原文可以是无数多个

比如主流使用的 MD5 将任意长度的字节串映射为一个 128bit 的大整数，也就是一共有 2^128 种可能，大概是 3.4*10^38，这个数字是有限多个的，而但是世界上可以被用来加密的原文则会有无数的可能性

尽量这是一个理论上的有限对无限，不过问题是这个无限在现实生活中并不完全成立，因为一方面现实中原文的长度往往是有限的，另一方面目前想要发现两段原文对应同一个  MD5 值非常困难，因此某种意义上来说，在一定范围内想构建MD5值与原文的一一对应关系是完全有可能的

**Unicode 编码与 UTF-8 的关系**

https://www.ruanyifeng.com/blog/2007/10/ascii_unicode_and_utf-8.html

**HTTP2.0**

- 利用二进制的分帧层对请求头、请求体进行分组分包，这样就允许在同一个连接可以发送和接收多个请求数据
- 主要特点：
  - 二进制分帧层：对传输数据分包分组
  - 多路复用：同一个连接中发送和接收多个请求的数据
  - 头部压缩：对于请求头进行压缩处理，重复内容不再传输
  - 服务器推送：在服务端对传输内容进行关联，主动推送关联的传输数据

**GRPC 应用场景**

- 需要对接口进行严格约束的情况，比如我们提供了一个公共的服务，很多人，甚至公司外部的人也可以访问这个服务，这时对于接口我们希望有更加严格的约束，我们不希望客户端给我们传递任意的数据，尤其是考虑到安全性的因素，我们通常需要对接口进行更加严格的约束，这时 GRPC 就可以通过 protobuf 来提供严格的接口约束
- 对于性能有更高的要求时，有时我们的服务需要传递大量的数据，而又希望不影响我们的性能，这个时候也可以考虑 gRPC 服务，因为通过 protobuf 我们可以将数据压缩编码转化为二进制格式，通常传递的数据量要小得多，而且通过 http2 我们可以实现异步的请求，从而大大提高了通信效率

**CRON 表达式**

| 字段                     | 允许值                                 | 允许的特殊字符             |
| ------------------------ | -------------------------------------- | -------------------------- |
| 秒（Seconds）            | 0~59的整数                             | , - * /   四个字符         |
| 分（*Minutes*）          | 0~59的整数                             | , - * /   四个字符         |
| 小时（*Hours*）          | 0~23的整数                             | , - * /   四个字符         |
| 日期（*DayofMonth*）     | 1~31的整数（但是你需要考虑你月的天数） | ,- * ? / L W C   八个字符  |
| 月份（*Month*）          | 1~12的整数或者 JAN-DEC                 | , - * /   四个字符         |
| 星期（*DayofWeek*）      | 1~7的整数或者 SUN-SAT （1=SUN）        | , - * ? / L C #   八个字符 |
| 年(可选，留空)（*Year*） | 1970~2099                              | , - * /   四个字符         |

**注意事项：**

- `*`：表示匹配该域的任意值，假如在 Minutes 域使用 `*`，即表示每分钟都会触发事件
- `?`：只能用在 DayofMonth 和 DayofWeek 两个域，它也匹配域的任意值，但实际不会，因为 DayofMonth 和 DayofWeek 会相互影响，例如想在每月的 20 日触发调度，不管 20 日到底是星期几，则只能使用如下写法： `13 13 15 20 * ?`，其中最后一位只能用 `？`，而不能使用 `*`，如果使用 `*` 表示不管星期几都会触发，实际上并不是这样
- `-`：表示范围，例如在 Minutes 域使用 5-20，表示从 5 分到 20 分钟每分钟触发一次 
- `/`：表示起始时间开始触发，然后每隔固定时间触发一次，例如在 Minutes 域使用 5/20，则意味着 5 分钟触发一次，而 25，45 等分别触发一次 
- `,`：表示列出枚举值，例如：在Minutes域使用 5,20，则意味着在5和20分每分钟触发一次。
- `L`：表示最后，只能出现在 DayofWeek 和 DayofMonth 域，如果在 DayofWeek 域使用 5L，意味着在最后的一个星期四触发
- `W`：表示有效工作日 (周一到周五)，只能出现在 DayofMonth 域，系统将在离指定日期的最近的有效工作日触发事件，例如：在DayofMonth 使用 5W，如果 5 日是星期六，则将在最近的工作日：星期五，即4日触发，如果5日是星期天，则在6日 (周一) 触发，如果5日在星期一到星期五中的一天，则就在 5 日触发，另外一点，W 的最近寻找不会跨过月份 
- `LW` ：这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五
- `#`：用于确定每个月第几个星期几，只能出现在 DayofWeek 域，例如在4#2，表示某月的第二个星期三

**String、StringBuffer、StringBuilder**

- String：String 的值是不可变的，这就导致每次对 String 的操作都会生成新的String对象，不仅效率低下，而且浪费大量优先的内存空间
- StringBuffer：StringBuffer是可变类和线程安全的字符串操作类，任何对它指向的字符串的操作都不会产生新的对象，每个 StringBuffer对象都有一定的缓冲区容量，当字符串大小没有超过容量时，不会分配新的容量，当字符串大小超过容量时，会自动增加容量
- StringBuilder：可变类，速度更快，线程不安全

# 好特卖

**B+ 树的特点**

**覆盖索引**

**双写一致性怎么保证顺序**

**redo log 和 binlog区别**

- redo log 是属于 innoDB 层面，binlog 属于 MySQL Server 层面的，这样在数据库用别的存储引擎时可以达到一致性的要求
- redo log 是物理日志，记录该数据页更新的内容，binlog 是逻辑日志，记录的是这个更新语句的原始逻辑
- redo log 是循环写，日志空间大小固定，binlog 是追加写，是指一份写到一定大小的时候会更换下一个文件，不会覆盖
- binlog 可以作为恢复数据使用，主从复制搭建，redo log 作为异常宕机或者介质故障后的数据恢复使用

**一条更新语句执行的顺序**

update T set c=c+1 where ID=2

- 执行器先找引擎取 ID=2 这一行，ID 是主键，引擎直接用树搜索找到这一行，如果 ID=2 这一行所在的数据页本来就在内存中，就直接返回给执行器，否则，需要先从磁盘读入内存，然后再返回
- 执行器拿到引擎给的行数据，把这个值加上 1，比如原来是 N，现在就是 N+1，得到新的一行数据，再调用引擎接口写入这行新数据
- 引擎将这行新数据更新到内存中，同时将这个更新操作记录到 redo log 里面，此时 redo log 处于 prepare 状态，然后告知执行器执行完成了，随时可以提交事务
- 执行器生成这个操作的 binlog，并把 binlog 写入磁盘

> 二进制日志是MySQL的上层日志，先于存储引擎的事务日志被写入