### 主流消息中间件介绍

#### activeMQ（性能一般，不适用与高并发，高可用）

ActiveMQ是Apache出品，最流行的，能力强劲的开源消息总线，并且它完全支持JMS规范

其丰富的API，多种集群搭建模式使得他成为业界老牌消息中间件，在中小型企业中应用广泛！

MQ衡量指标：服务性能，数据存储，集群架构：Master-Slave模式，NetWork模式

#### Kafka（性能高，数据可能会丢失）

kafka是LinkedIn开源的分布式发布-订阅消息系统，目前归属于Apache顶级项目，Kafka主要特点是基于Pull的模式来处理消息消费，追求高吞吐量，一开始的目的就是用于日志收集和传输。0.8版本开始支持复制，不支持事务，对消息的重复，丢失，错误没有严格要求，适合产生大量数据的互联网服务的数据收集业务。

#### RocketMQ（全能，收费）

RocketMQ是阿里开源的消息中间件，目前也已经孵化为Apache顶级项目，它是纯Java开发，具有高吞吐量，高可用性，适合大规模分布式系统应用的特点。RocketMQ思路起源于Kafka，它对消息的可靠性传输及事务性做了优化，目前在阿里集团被广泛的应用于交易，充值，流计算，消息推送，日置流式处理，binglog分发等场景

#### RabbitMQ

RabbitMQ是使用Erlang语言开发的开源消息队列系统，基于AMQP协议来实现，AMQP的主要特征是面向消息，队列，路由（包括点对点和发布/订阅），可靠性，安全，AMQP协议更多用在企业系统中，对数据一致性，稳定性，可靠性要求很高的场景，对性能和吞吐量的要求还在其次

#### RabbitMQ高性能的原因

Erlang语言最初在与交换机领域的构架模式，这样使得RabbitMQ在Broker之间进行数据交互的性能是非常优秀的，Erlang的优点：Erlang有着和原生Socket一样的延迟

#### AMQP高级消息队列协议

AMQP定义：是具有现代特征的二进制协议。是一个提供统一消息服务的应用层标准高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。

#### AMQP核心概念

Server：又称Broker，接受客户端的连接，实现AMQP实体服务

Connection：连接，应用程序和Broker的网络连接

Channel：网络信道，几乎所有的操作都在Channel中进行，Channel是进行消息读写的通道。客户端可建立多个Channel，每个Channel代表一个会话任务

Message：消息，服务器和应用程序之间传送的数据，由Properties和Body组成，Properties可以对消息进行修饰，比如消息的优先级，延迟等高级特性，body则就是消息体内容。

Virtual host：虚拟地址，用于进行逻辑隔离，最上层的消息路由，一个Virtual Host里面可以有若干个Exchage和Queue，同一个Virtual Host里面可以有若干个Exchange和Queue，同一个Virtual Host 里面不能有相同名称的Exchange或Queue

Exchange：交换机，接收消息，根据路由键转发消息到绑定的队列

Binding：Exchange和Queue之间的虚拟连接，binding中可以包含routing key

Routing key：一个路由规则，虚拟机可以用它来确定如何路由一个特定消息

Queue：也称Message Queue，消息队列，保存信息并将它们转发给消费者

#### RabbitMq安装略过

#### 命令行与管控台-基础操作

```shell
#关闭应用
rabbitmqctl stop_app
#启动应用
rabbitmqctl start_app
#节点状态
rabbitmqctl status
#添加用户
rabbitmqctl add_user username password
#列出所有用户
rabbitmqctl list_users
#删除用户
rabbitmqctl delete_username
#清除用户权限
rabbitmqctl clear_permissions -p vhostpath username
#列出用户权限
rabbitmqctl list_user_permissions username
#修改密码
rabbitmqctl change_password username newpassword
#设置用户权限
rabbitmqctl set_permissions -p vhostpath username ".*" ".*" ".*"
#创建虚拟主机
rabbitmqctl add_vhost vhostpath
#列出所有虚拟主机
rabbitmqctl list_vhosts
#列出虚拟主机所有权限
rabbitmqctl list_permissions -p vhostpath
#删除虚拟主机
rabbitmqctl delete_vhost vhostpath
#查看所有队别
rabbitmqctl list_queues
#清除队别里的消息
rabbitmqctl -p vhostpath purge_queue blue
```

命令行与管控台-高级操作

```shell
#移除所有数据，要在rabbitmqctl stop_app之后使用
rabbitmqctl reset
#组成集群命令
rabbitmqctl join_cluster <clusternode> [--ram]
#查看集群状态
rabbitmqctl cluster_status
#修改集群节点的存储形式
rabbitmqctl change_cluster_node_type disc | ram
#忘记节点（摘除节点）
rabbitmqctl forget_cluster_node [--offline]
#修改节点名称
rabbitmqctl rename_cluser_node oldnode1 newnode1 [oldnode2] [newnode2...]
```

#### 极速入门-消息生产与消费

- ConnectionFactory ：获取连接工厂

- Connection ：一个连接

- Channel ： 数据通信通道，可发送和接收消息

- Queue ：具体的消息存储队列

- Producer & Consumer：生产者和消费者


### Exchange交换机

接收消息，并根据路由键转发消息所绑定的队列

属性：

- Name：交换机名称

- type：交换机类型：direct，topic，fanout，headers

- Durability：是否需要持久化，true为持久化

- Auto Delete：当最后一个绑定到Exchange上的队列删除后，自动删除该Exchange

- Internal：当前Exchange是否用于RabbitMQ内部使用，默认为false

- Arguments：扩展参数，用于AMQP协议自制定化使用


##### Direct Exchange

所有发送到Direct Exchange的消息被转发到RouteKey中指定的Queue

注意：Direct模式可以使用RabbitMQ自带的Exchange：default Exchange，所以不需要将Exchange进行任何绑定（binging）操作，消息传递时，RouteKey必须完全匹配才会被队列接收，否则该消息会被抛弃

##### Topic Exchange

所有发送到Topic Exchange的消息被转发到所有关心RouteKey中指定Topic的Queue上

Exchange将RouteKey和某Topic进行模糊匹配，此时队列需要绑定一个Topic

注意：可以使用通配符进行模糊匹配

符号 “#” 匹配一个或多个词

符号 "*" 只能匹配一个词

例如："log.#" 能匹配到"log.info.oa"

​            "log.*" 只会匹配到"log.error"

##### Fanout Exchange（性能较高）

- 不处理路由键，只需要简单的将队列绑定到交换机上

- 发送到交换机的消息都会被转发到与该交换机绑定的所有队列上

- Fanout交换机转发消息是最快的


### Binding-绑定

- Exchange与Exchange，Queue之间的连接关系

- Binding中可以包含RoutingKey或者参数


### Queue-消息队列

- 消息队列，实际存储消息数据

- Durability：是否持久化，Durable：是，Transient：否

- Auto delete：如选yes，代表当最后一个监听被移除之后，该Queue会被自动删除


### Message-消息

- 服务器和应用程序之间传送的数据

- 本质上就是一段数据，由Properties和Payload（Body）组成

- 常用属性：delivery mode，headers（自定义属性）


### Message-其他属性

- content_type，content_encoding，priority

- correlation_id，reply_to，expiration，message_id

- timestamp，type，user_id，app_id，cluster_id


### Virtual host-虚拟主机

- 虚拟地址，用于进行逻辑隔离，最上层的消息路由

- 一个Virtual Host里面可以有若干个Exchange和Queue

- 同一个Virtual Host里面不能有相同名称的Exchange或Queue


### RabbitMQ高级

#### 消息怎么保障100%的投递成功

- 什么是生产端的可靠性投递？
- 保障消息的成功发出
- 保证MQ节点的成功接收
- 发送端收到MQ节点（Broker）确认应答
- 完善的消息进行补偿机制

#### 生产端-可靠性投递（一）

##### BAT/TMD互联网大厂的解决方案：

消息落库，对消息状态进行打标

消息的延迟投递，做二次确认，回调检查 

#### 幂等性概念

例子：

借鉴数据库的乐观锁机制

执行一条更新库存的SQL语句（保障并发情况下，数据不会异常）

```mysql
UPDATE T_REPS SET COUNT = COUNT - 1,VERSION = VERSION + 1 WHERE VERSION = 1
```

#### 消费端-幂等性保障

在海量订单产生的业务高峰期，如何避免消息的重复消费问题

消费端实现幂等性，就意味着，我们的消息永远不会消费多次，即使我们收到了多条一样的数据

#### 业界主流的幂等性操作：

##### 唯一ID + 指纹码机制，利用数据库主键去重

- SELECT COUNT(1) FROM T_ORDER WHERE ID = 唯一ID + 指纹码
- 好处：实现简单
- 坏处：高并发下有数据库写入的性能瓶颈
- 解决方案：跟进ID进行分库分表进行算法路由

利用Redis的原子性去实现

使用Redis进行幂等，需要考虑的问题

第一：我们是否要进行落库，如果落库，关键解决的问题是数据库和缓存如何做到原子性

第二：如果不进行落库，那么都存储到缓存中，如何设置定时同步的策略

#### Confirm确认消息

消息的确认，是指生产者投递消息后，如果Broker收到消息，则会给我们生产者一个应答。

生产者进行接收应答，用来确认这条消息是否正常的发送到Broker，这种方式也是消息的可靠性投递的核心保障！

##### 实现Confirm确认消息

第一：在Channel上开启确认模式：channel.confirmSelect()

第二：在Channel上添加监听：addConfirmListener，监听成功和失败的返回结果，根据具体的结果对消息进行重新发送，或记录日志等后续处理

#### Return消息机制

Return Listener用于处理一些不可路由的消息！

- 我们的消息生产者，通过指定一个Exchange和RoutingKey，把消息送达到某一个队列中去，然后我们的消费者监听队列，进行消费处理操作

- 但是在某些情况下，如果我们在发送消息的时候，当前的Exchange不存在或者知道的路由Key路由不到，这个时候我们需要监听这种不可达的消息，就要使用Return Listener

- 在基础API中有一个关键性的配置项：

- Mandatory：如果为true，则监听器会接到路由不可达的消息，然后进行后续处理，如果为false（默认），那么Broker会自动删除该消息

#### 消费者自定义监听

我们一般是在代码中编写while循环，进行consumer.nextDelivery方法进行获取下一条信息，然后进行处理

但是我们使用自定义的consumer更加方法名，解耦性更强

#### 消费端限流

假设一个场景，首先，我们Rabbitmq服务器上有万条未处理的消息，我们随便打开一个消费者客户端，会出现下面情况：

巨量的消息瞬间推送，但是我们单个客户端无法同时处理这么多数据

Rabbitmq提供了一种qos（服务质量保证）功能，即在非自动确认消息的前提下，如果一定数目的消息（通过基于consumer或者Channel设置Qos的值）未被确认前，不进行消费新的消息

```java
void BasicQos（uint prefetchSize,ushort prefetchCount,bool global）
```

- prefetchSIze:0 消费的单条消息的大小，0代表不做限制
- prefetchCount：会告诉rabbitmq不要同时给一个消费者推送多于N个消息，即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
- global：true/false是否将上面设置应用于Channel
- 简单的说，就是上面限制是Channel级别的还是consumer级别

##### 注意：

prefetchSize和global这两项，rabbitmq没有实现，暂且不研究prefetch_count在no_ack=false的情况下生效，即在自动应答的情况下，这2个值是不生效的

#### 消费端ACK与重回队列

- 消费端的手工ACK和NACK
- 消费端进行消费的时候，如果由于业务异常我们可以进行日志的记录，然后进行补偿
- 如果由于服务器宕机等严重问题，那我们就需要手工进行ACK保障消费端消费成功
- 消费端的重回队列
- 消费端重回队列是为了对没有处理成功的消息，把消息重新回递给Broker
- 一般我们在实际应用中，都会关闭重回队列，也就是设置为False

#### TTL队列/消息

- TTL是Time To Live的缩写，也就是生存时间
- RabbitMQ支持消息的过期时间，在消息发送的时候可以进行指定
- RabbitMQ支持队列的过期时间，从消息入队列开始计算，只要超过了队列的超时时间配置，那么消息会自动清除

#### 死信队列：DLX，Dead-letter-Exchange

利用DLX，当消息在一个队列中变成死信（dead message）之后，它能被重新publish到另一个Exchange，这个Exchange就是DLX

##### 死信的几种情况

- 消息被拒绝（basic.reject/basic.nack）并且requeue=false
- 消息TTL过期
- 队列达到最大长度

- DLX也是一个正常的Exchange，和一般的Exchange没有区别，它能在任何的队列上被指定，实际上就是设置某个队列的属性。
- 当这个队列中有死信的时候，Rabbitmq就会自动的将这个消息重新发布到设置的Exchange上去，进而被路由到另一个队列。
- 可以监听这个队列中消息做相应的处理，这个特性可以弥补Rabbitmq3.0之前支持的immediate参数的功能

##### 死信队列设置

首先需要设置死信队列的Exchange和queue，然后进行绑定：

- Exchange：dlx.exchange
- Queue：dlx.queue
- RoutingKey：#

然后我们进行正常声明交换机，队列，绑定，只不过我们需要在队列加上一个参数即可

```java
arguments.put("x-dead-letter-exchange",dlx.exchange);
```

这样消息在过期，requeue，队列在达到最大长度时，消息就可以直接路由到死信队列

### RabbitMQ高级整合应用

#### RabbitMQ整合Spring AMQP实战

- RabbitAdmin
- SpringAMQP声明
- RabbitTemplate
- SimpleMessageListenerContainer
- MessageListenerAdapter
- MessageConverter

##### RabbitAdmin类可以很好的操作RabbitMQ，在spring中直接进行注入即可

##### 注意：

autoStartup必须要设置为true，否则Spring容器不会加载RabbitAdmin类

RabbitAdmin底层实现就是从Spring容器中获取Exchange，Bingding，RoutingKey以及Queue的@Bean声明

使用RabbitTemplate的execute方法执行对应的声明，修改，删除等一系列RabbitMQ基础功能操作

例如：添加一个交换机，删除一个绑定，清空一个队列的消息等等

##### SpringAMQP声明

使用SpringAMQP去声明，就需要使用SpringAMQP的如下模式，即声明@Bean方式

##### RabbitTemplate-消息模板

我们在与SpringAMQP整合的时候进行发送消息的关键类

该类提供了丰富的发送消息的方法，包括可靠性投递消息方法，回调监听消息接口ConfirmCallback，返回值确认接口ReturnCallback等，同样我们需要进行注入到Spring容器中，然后直接使用

在于Spring整合的时候需要实例化，但是与SpringBoot整合时候，在配置文件添加配置即可

##### SimpleMessageListenerContainer-简单消息监听容器

这个类非常强大，我们可以对他进行很多设置，对于消费者的配置项，这个类都可以满足

监听队列（多个队列），自动启动，自动声明功能

- 设置事务特性，事务管理器，事务属性，事务容量（并发），是否开启事务，回滚消息等
- 设置消费者数量，最小最大数量，批量消费
- 设置消息确认和自动确认模式，是否重回队列，异常捕捉hander函数。
- 设置消费者标签生成策略，是否独占模式，消费者属性等
- 设置具体的监听器，消息转换器等

注意：SimpleMessageListenerContainer可以进行动态设置，比如在运行中的应用可以动态的修改其消费者数量的大小，接收消息的模式等

很多基于RabbitMQ的自制定化后端管控台在进行动态设置的时候，也是根据这一特性去实现的，所有可以看出SpringAMQP非常的强大

#### MessageListenerAdapter-即消息监听适配器

通过messageListenerAdapter的代码我们可以看出如下核心属性

- defaultListenerMethod：监听方法名称：用于设置监听方法名称
- Delegate：委托对象：实际真实的委托对象，用于处理消息
- queueOrTagToMethodName：队列标识与方法名称组成的集合
- 可以一一进行队列和方法名称的匹配
- 队列和方法名称绑定，即指定队列里的消息会被绑定的方法所接收处理

#### MessageConverter-消息转换器

我们在发送消息的时候，正常情况下消息体为二进制的数据方式进行传输，如果希望内部帮我们进行转换，或者指定自定义的转换器，就需要用到MessageConverter

##### 自定义常用转换器：MessageConverter，一般来说都需要实现这个接口

重写2个方法

toMessage：Java对象转换为Message

fromMessage：Message对象转换为Java对象

##### json转换器：Jackson2JsonMessageConverter：可以进行Java对象的转换功能

##### DefaultJackson2JavaTypeMapper映射器：可以进行Java对象的映射关系

##### 自定义二进制转换器：比如图片类型，PDF，PPT，流媒体

#### SpringBoot整合配置详解

publish-confirms，实现一个监听器用与Broker端给我们返回的确认请求：

RabbitTemplate.confirmCallback

publisher-returns,保证消息对Broker端是可达的，如果出现路由键不可达的情况，则使用监听器对不可达的消息进行后续的处理，保证消息的路由成功：

RabbitTemplate.ReturnCallback

##### 注意：

发送消息的时候，对Template进行配置mandatory=true保证监听有效

生产端还可以配置其他属性，比如发送重试，超时时间，次数，间隔等

##### 消费端核心配置

spring.rabbitmq.listener.simple.acknowledge-mode=MANUAL

spring.rabbitmq.listener.simple.concurrency=1

spring.rabbitmq.listener.simple.max-concurrency=5 

- 首先配置手工确认模式，用于ACK的手工处理，这样我们可以保证消息的可靠性送达。
- 或者在消费端失败的时候可以做到重回队列，根据业务记录日志等处理
- 可以设置消费端的监听个数和最大个数，用于控制消费端的并发情况

#### @RabbitListener注解使用

消费端监听@rabbitListener注解，这个对于在实际工作中非常好用

@RabbitListener是一个组合注解，里面可以配置注解

@QueueBinging，@Queue，@Exchange直接通过这个组合注解一次性搞定消费端交换机，队列，绑定，路由，并且配置监听功能等

#### SpringCloud Stream整合

##### Barista接口：Barista接口是定义来作为后面类的参数，这一接口定义了通道类型和通道名称，通道名称是作为配置用，通道类型则决定了app会使用这一通道进行发送消息还是从中接收消息

@Output：输出注解，用于定义发送消息接口

@Input：输入注解，用于定义消息的消费者接口

@StreamListener：用于定义监听方法的注解

使用Spring Cloud Stream非常简单，只需要使用好这3个注解即可，在实现高性能消息的生产和消费的场景非常适合，但是使用SpringCloudStream框架有一个非常大的问题就是不能实现可靠性投递，也就是没法保证消息的100%可靠性，会存在少量消息丢失的问题

这个原因是因为SpringCloudStream框架是为了和Kafka兼顾所以在实际工作中使用它的目的就是针对高性能的消息通信的，这点就是在当前版本SpringCloudStream的定位

### RabbitMQ集群架构

#### 主备模式

实现RabbitMQ的高可用集群，一般在并发和数据量不高的情况下，这种模型非常的好用且简单，主备

模式也称之为Warren模式，就是一个主/备方案（主节点如果挂了，从节点提供服务而已，和activeMQ利用zookeeper做主/备一样）

##### HaProxy配置（TCP代理）：

```properties
Listen rabbitmq_cluster
#配置TCP模式
bind 0.0.0.0:5672
#简单的轮询
mode tcp
#主节点
balance roundrobin
server bhz76 192.168.11.76:5672 check inter 5000 rise 2 fall 2
#备用节点
server bhz77 192.168.11.77:5672 backup check inter 5000 rise 2 fall 2
```

备注：

rabbitmq集群节点配置#inter每隔5秒对mq集群做健康检查，2次正确证明服务器可用，2次失败证明服务器不可用，并且配置主备机制

#### 远程模式（不推荐）

远程模式是可以实现双活的一种模式，简称Shovel模式，所谓Shovel就是我们可以把消息进行不同数据中心的复制工作，我们可以跨地域的让2个mq集群互联

Shovel集群的配置，首先启动rabbitmq插件，命令如下：

```shell
rabbitmq-plugins enable amqp_client

rabbitmq-plugins enable rabbitmq_shovel
```

创建rabbitmq.conf文件：touch /etc/rabbitmq/rabbitmq.config

添加配置见rabbitmq.config

最后我们需要源服务器和目的服务器都是用相同的配置文件（rabbitmq.config）

#### 镜像模式

Mirror镜像模式队列，目的是为了保证rabbitmq数据的高可靠性解决方案，主要就是实现数据的同步，一般来说是2-3个节点实现数据同步，（对于100%的可靠性解决方案一般是3节点）集群架构如下：

#### 多活模式

这种模式也是实现异地数据复制的主流模式，因为Shovel模式配置比较复杂，所有一般来说实现异地集群都是使用这种双活，或者多活的模式去实现，这种模型需要依赖rabbitmq的federation插件，可以实现持续的可靠的AMQP数据通信，多活模式在实际配置与应用比较简单

RabbitMQ部署架构采用双中心模式（多中心），那么在2套（或多套）数据中心部署一套RabbitMQ服务除了需要为业务提供正常的消息服务外，中心之间还需要实现部分队列消息共享。

##### Federation

federation插件是一个不需要构建Cluster，而在Brokers之间传输消息的高性能插件，Federation插件可以在Brokers或者Cluster之间传输消息，连接的双方可以使用不同的users和Virtual hosts，双方也可以使用版本不同的Rabbitmq和Erlang。federation插件使用AMQP协议通信，可以接受不连续传输

Federation Exchanges，可以看成Downstream从Upstream主动拉取消息，但并不是拉取所有消息，必须是在Downstream已经明确定义Bingings关系的Exchange，也就是说有实际的物理Queue来接收消息，才会从Upstream拉取消息到Downstream。使用AMQP协议实施代理间通信，Downstream会将绑定关系组合在一起，绑定/解除绑定命令将发送到Upstream交换机，因此Federation Exchange只接受具有订阅的消息

##### HAProxy

HAProxy是一款提供高可用性，负载均衡以及基于TCP（第四层）和HTTP（第七层）应用的代理软件。支持虚拟主机，它是免费，快速并且可靠的一种解决方案，HAProxy特别适用于那些负载特大的web站点，这些站点通常又需要会话保持或七层处理，HAProxy运行在时下的硬件上，完全可以支持数以万计的并发连接，并且它的运行模式使得它可以很简单安全的整合进您当前的架构了，同时可以保护您的web服务器不被暴露在网络上

HAProxy借助于OS上几种常见的技术来实现性能的最大化：

- 单进程，事件驱动模型显著降低了上下文切换的开销及内存使用
- 在任何可用的情况下，单缓冲（sing buffering）机制能以不复制任何数据的方式完成读写操作，这会节约大量的CPU时钟周期及内存带宽
- 借助于Linux2.6（>=2.6.27.19）上的splice()系统调用，HAProxy可以实现零复制转发（zero-copy forwarding），在linux3.5及以上OS还可以实现零复制启动（zero-starting）
- 内存分配器在固定大小的内存池中可实现即时内存分配，这能够显著的减少创建一个会话的时长
- 树形存储：侧重与使用作者多年前开发的弹性二叉树，实现了以O（log（N））的低开销来保持计时器命令，保持运行命令及管理轮询及最少连接队列

##### KeepAlived 

Keepalived，它是一个高性能的服务器高可用或热备解决方案，Keepalived主要来防止服务器单点故障的发生问题，可以通过其与Nginx、Haproxy等反向代理的负载均衡服务器配合实现web服务端的高可用。Keepalived以VRRP协议为实现基础，用VRRP协议来实现高可用性（HA）.VRRP（Virtual Router Redundancy Protocol）协议是用于实现路由器冗余的协议，VRRP协议将两台或多台路由器设备虚拟成一个设备，对外提供虚拟路由器IP（一个或多个）。

### 集群恢复和故障转移

#### 前提：比如2个节点A和B组成一个镜像队列（B是Master）

场景1：A先停，B后停

方案：该场景下B是Master，只要先启动B，再启动A即可，或者先启动A，再30s之内启动B即可恢复镜像队列

场景2：A，B同时停机

方案：该场景可能是由于机房掉电等原因造成的，只需在30s之内连续启动A和B即可恢复镜像

场景3：A先停，B后停，且A无法恢复

方案：该场景是场景1的加强版，因为B是Master，所以等B起来以后，在B节点调用控制台命令：**rabbitmqctl forget_cluster_node A**解除与A的Cluster关系，再将新的Slave节点加入B即可恢复镜像队列

场景4：A先停，B后停，且B无法恢复

方案：该场景是场景3的加强版，比较难处理，原因是因为Master节点无法恢复，早在3.1.x时代之前没有什么好的解决方案，但是现在已经有解决方案了，在3.4.2以后的版本，因为B是主节点，所以直接启动A是不行的，当A无法启动的时候，也就没办法在A节点上调用之前的**rabbitmqctl forget_cluster_node B**命令了，新版本中，**forget_cluster_node**支持**--offline**参数，这就意味着允许rabbitmqctl在理想节点上执行该命令，迫使RabbitMQ在未启动Slave节点中选择一个节点作为Mater。当在A节点执行**rabbitmqctl forget_cluster_node --offline B**时，Rabbitmq会mock一个节点代表A，执行**forget_cluster_node**命令将B剔除Cluster，然后A就可以正常启动，最后将新的Slave节点加入到A即可恢复镜像队列

场景5：A先停，B后停，且A，B均无法恢复，但是能得到A或B的磁盘文件

方案：这种场景更加难处理，只能通过恢复数据的方式去尝试恢复，将A或B的数据库文件（默认在$RABBIT_HOME/var/lib/目录中），将它拷贝到新节点的对于mulxia，再将新节点的hostname改成A或B的hostname，如果是A节点（Slave）的磁盘文件，按照场景4处理，如果是B节点（Master）的磁盘文件，按照场景3处理，最后将新的Slave加入到新节点完成恢复

### 互联网大厂SET化架构

随着大型互联网公司业务的多元化发展，就拿滴滴，美团等大厂来说，打车，单车，外卖，酒店，旅行，金融等业务的持久高速增长，单个大型分布式体系的集群，通过加机器+集群内部拆分（kv，mq，mysql），虽然具备了一定的可扩展性，但是随着业务量的进一步增长，整个集群的规模逐渐变得巨大，从而一定会在某个点赏达到瓶颈，无法满足扩展性需要，并且大集群内核心业务服务出现问题，会影响全网所有用户

以滴滴打车，美团外卖举例来说：

打车业务体量巨大尤其是在早晚高峰期，全年订单量超过10亿

外卖业务体量庞大，目前单量已突破1700W/天，对于如此庞大的单个大型分布式集群，会面临下面问题：

##### 容灾问题

- 核心服务（比如订单服务）挂掉，会影响全网所有用户，导致整个业务不可用；
- 数据库主库集中在一个IDC，主机房挂掉，会影响到全网所有用户，整个业务无法快速切换和恢复

##### 资源扩展问题

- 单IDC的资源（机器，网络带宽）已经没法满足，扩展IDC时，存在扩机房访问时延问题（增加异地机房时，时延问题更加严重）
- 数据库主库单点，连接数有限，不能支持应用程序的持续扩展

大集群拆分问题

- 核心问题：分布式集群规模扩大后，会相应的带来资源扩展，大集群拆分以及容灾问题
- 所以出于对业务扩展性以及容灾考虑，我们需要一套从底层架构彻底解决问题的方案：业界主流解决方案：
- 单元化架构方案（阿里，支付宝，饿了么，微信等）

#### 同城双活架构介绍

目前很多大型互联网公司的业务架构可以理解为同城”双活“架构，注意这里的”双活“是加引号的，具体可以这样理解：

- 业务层次上已经做到真正的双活（或者多活），分别承担部分流量
- 存储层面比如定时任务，缓存，持久层，数据分析等都是主从架构，会有扩机房写
- 一个数据中心故障，可以手动切换流量，部分组件可以自动切换

#### 两地三中心架构

使用灾备的思想，在同城双活的基础上，在异地部署一套灾备数据中心，每个中心都具有完备的数据处理能力，只有当主节点故障的时候才会紧急启动备用数据中心

#### SET化方案目标

业务：解决业务遇到的扩展性和容灾需求，支撑业务的告诉发展

通用性：架构侧形成统一通用的解决方案，方便各业务线接入使用

SET化架构策略路

**流量路由：**

按照特殊的key（通常为userid）进行路由，判断某次请求改路由到中心集群还是单元化集群；

**中心集群：**

未进行单元化改造的服务（通常不在核心交易链路，比如供应链系统）称为中心集群，跟当前架构保持一致

**单元化集群：**

- 每个单元化集群只负责本单元内的流量处理，以实现流量拆分以及故障隔离
- 每个单元化集群前期只存储本单元产生的交易数据，后续会做双向数据同步，实现容灾切换需求；

**中间件（RPC，KV，MQ等）：**

- RPC：对于SET服务，调用封闭在SET内，对于非SET服务，沿用现有路由逻辑
- KV：支持分SET的数据生产和查询
- MQ：支持分SET消息生产和消费

**数据同步：**

- 全局数据（数据量小且变化不大，比如商家的菜品数据）部署在中心集群，其他单元化集群同步全局数据到本单元内；
- 未来演变为异地多活架构时，各单元化集群数据需要进行双向同步来实现容灾需要

**SET化路由策略及其能力：**

异地容灾：通过SET化架构的流量调度能力，将SET分别部署在不同地区的数据中心，实现扩地区容灾支持

**高效本地化服务**

利用前端位置信息采集和域名解析策略，将流量路由到最近的SET，提供最高效的本地化服务；

比如O2O场景天然具有本地采集，本地消费的特点，更加需要SET架构支持

**集装箱式扩展：**

SET的封装性支持更灵活的部署扩展性，比如SET一建创建/下线,SET一建发布等

#### SET化架构原则

**业务透明原则：**

SET化架构的实现对业务透明，业务代码层面不需要关心SET化规则，SET化的部署问题

**SET化切分原则**

理论上，切分规则由业务层面按需定制；

实现上，建议优先选最大的业务维度进行切分

比如海量用户的O2O业务，按用户信息进行切分，此外，接入层，逻辑层和数据层可以有单独的SET切分规则，有利于实现部署和运维成本的最优化

**部署规范原则**

一个SET并不一定只限制在一个机房，也可以跨机房或者跨地区部署，为保证灵活性，单个SET内机器数不宜过多（如不超过1000台物理机）

#### RabbitMQ-SET化架构实现

SET化消息中间件架构实现（RabbitMQ双活）

使用RabbitMQ异步消息通信插件federation安装与配置：

rabbitmq-plugins enable rabbitmq_federation

rabbitmq-plugins enable rabbitmq_federation_managerment

备注：

当你在一个Cluster中使用了federation插件，所有在集群中的nodes都需要安装federation插件

### 终章：一线大厂的MQRabbitMQ基础组件架构设计思路







  



