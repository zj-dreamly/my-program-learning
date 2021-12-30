



# 整体介绍

- RocketMQ 是一款分布式队列模型的消息中间件
- 最新版本是 4.3.X，支持了分布式事务
- 支持集群模型、负载均衡、水平扩展
- 亿级别的消息堆积能力
- 采用零拷贝、顺序写盘、随机读
- 丰富的 API 使用
- 代码优秀，底层通信框架使用 Netty NIO
- NameServer 代替 Zookeeper
- 强调集群无单点，可扩展，任意一点高可用，水平可扩展
- 消息失败重试机制，消息可查询
- 开源社区活跃，成熟度经过双十一考验

> 官方文档：https://github.com/apache/rocketmq/tree/master/docs/cn

# 基本概念

**消息模型（Message Model）**

- RocketMQ主要由 Producer、Broker、Consumer 三部分组成，其中Producer 负责生产消息，Consumer 负责消费消息，Broker 负责存储消息
- Broker 在实际部署过程中对应一台服务器，每个 Broker 可以存储多个Topic的消息，每个Topic的消息也可以分片存储于不同的Broker
- Message Queue 用于存储消息的物理地址，每个Topic中的消息地址存储于多个 Message Queue 中
- ConsumerGroup 由多个Consumer 实例构成

**消息生产者（Producer）**

- 负责生产消息，一般由业务系统负责生产消息
- 一个消息生产者会把业务应用系统里产生的消息发送到broker服务器。RocketMQ提供多种发送方式，同步发送、异步发送、顺序发送、单向发送
- 同步和异步方式均需要Broker返回确认信息，单向发送不需要

**消息消费者（Consumer）**

- 负责消费消息，一般是后台系统负责异步消费
- 一个消息消费者会从Broker服务器拉取消息、并将其提供给应用程序
- 从用户应用的角度而言提供了两种消费形式：拉取式消费、推动式消费

**主题（Topic）**

表示一类消息的集合，每个主题包含若干条消息，每条消息只能属于一个主题，是RocketMQ进行消息订阅的基本单位

**代理服务器（Broker Server）**

- 消息中转角色，负责存储消息、转发消息
- 代理服务器在RocketMQ系统中负责接收从生产者发送来的消息并存储、同时为消费者的拉取请求作准备
- 代理服务器也存储消息相关的元数据，包括消费者组、消费进度偏移和主题和队列消息等

**名字服务（Name Server）**

- 名称服务充当路由消息的提供者
- 生产者或消费者能够通过名字服务查找各主题相应的 Broker IP 列表，多个 Nameserver 实例组成集群，但相互独立，没有信息交换

**拉取式消费（Pull Consumer）**

- Consumer消费的一种类型，应用通常主动调用Consumer的拉消息方法从Broker服务器拉消息、主动权由应用控制
- 一旦获取了批量消息，应用就会启动消费过程

**推动式消费（Push Consumer）**

- Consumer消费的一种类型，该模式下Broker收到数据后会主动推送给消费端，该消费模式一般实时性较高

**生产者组（Producer Group）**

- 同一类Producer的集合，这类Producer发送同一类消息且发送逻辑一致
- 如果发送的是事务消息且原始生产者在发送之后崩溃，则Broker服务器会联系同一生产者组的其他生产者实例以提交或回溯消费

**消费者组（Consumer Group）**

- 同一类Consumer的集合，这类Consumer通常消费同一类消息且消费逻辑一致，消费者组使得在消息消费方面，实现负载均衡和容错的目标变得非常容易
- 要注意的是，消费者组的消费者实例必须订阅完全相同的Topic
- RocketMQ 支持两种消息模式：集群消费（Clustering）和广播消费（Broadcasting）

**集群消费（Clustering）**

集群消费模式下，相同Consumer Group的每个Consumer实例平均分摊消息

**广播消费（Broadcasting）**

广播消费模式下，相同Consumer Group的每个Consumer实例都接收全量的消息

**普通顺序消息（Normal Ordered Message）**

普通顺序消费模式下，消费者通过同一个消息队列（ Topic 分区，称作 Message Queue） 收到的消息是有顺序的，不同消息队列收到的消息则可能是无顺序的

**严格顺序消息（Strictly Ordered Message）**

严格顺序消息模式下，消费者收到的所有消息均是有顺序的

**消息（Message）**

- 消息系统所传输信息的物理载体，生产和消费数据的最小单位，每条消息必须属于一个主题
- RocketMQ中每个消息拥有唯一的Message ID，且可以携带具有业务标识的Key
- 系统提供了通过Message ID和Key查询消息的功能

**标签（Tag）**

- 为消息设置的标志，用于同一主题下区分不同类型的消息
- 来自同一业务单元的消息，可以根据不同业务目的在同一主题下设置不同标签
- 标签能够有效地保持代码的清晰度和连贯性，并优化RocketMQ提供的查询系统
- 消费者可以根据Tag实现对不同子主题的不同消费逻辑，实现更好的扩展性

# 集群模式

- 单点模式
- 主从模式
  - 主从模式环境构建可以保障消息的及时性和可靠性
  - 投递一条消息后，关注主节点，从节点继续可以提供消费者进行消费，但是不能接收消息了
  - 主节点上线后进行消费进度 offset 同步
- 双主模式
- 多主多从

# 主从同步机制

- Master-Slave主从同步
- 同步信息：消息信息 + 元数据信息
- 元数据同步
  - Broker 角色识别，为 Slave 则启动同步任务
  - 底层使用 Netty 去同步
- 消息同步
  - HAService、HAconnection、WaitNotifyObject
  - 底层使用原生 socket 去同步

# RocketMQ 生产者

## Producer 配置

| 参数名                           | 默认值           | 说明                                                         |
| -------------------------------- | ---------------- | ------------------------------------------------------------ |
| producerGroup                    | DEFAULT_PRODUCER | Producer组名，多个Producer如果属于一个应用，发送同样的消息，则应该将它们归为同一组 |
| createTopicKey                   | TBW102           | 在发送消息时，自动创建服务器不存在的topic，需要指定Key，该Key可用于配置发送消息所在topic的默认路由。 |
| defaultTopicQueueNums            | 4                | 在发送消息，自动创建服务器不存在的topic时，默认创建的队列数  |
| sendMsgTimeout                   | 10000            | 发送消息超时时间，单位毫秒                                   |
| compressMsgBodyOverHowmuch       | 4096             | 消息Body超过多大开始压缩（Consumer收到消息会自动解压缩），单位字节 |
| retryAnotherBrokerWhenNotStoreOK | FALSE            | 如果发送消息返回sendResult，但是sendStatus!=SEND_OK，是否重试发送 |
| retryTimesWhenSendFailed         | 2                | 如果消息发送失败，最大重试次数，该参数只对同步发送模式起作用 |
| maxMessageSize                   | 4MB              | 客户端限制的消息大小，超过报错，同时服务端也会限制，所以需要跟服务端配合使用。 |
| transactionCheckListener         |                  | 事务消息回查监听器，如果发送事务消息，必须设置               |
| checkThreadPoolMinSize           | 1                | Broker回查Producer事务状态时，线程池最小线程数               |
| checkThreadPoolMaxSize           | 1                | Broker回查Producer事务状态时，线程池最大线程数               |
| checkRequestHoldMax              | 2000             | Broker回查Producer事务状态时，Producer本地缓冲请求队列大小   |
| RPCHook                          | null             | 该参数是在Producer创建时传入的，包含消息发送前的预处理和消息响应后的处理两个接口，用户可以在第一个接口中做一些安全控制或者其他操作。 |

## 发送消息

- DefaultMQProducer#send
- DefaultMQProducerImpl

## 延时消息

- 消息发到 Broker 后，要特定的时间才会被 Consumer 消费
- 目前只支持固定精度的定时消息
- MessageStoreConfig 配置类和 ScheduleMessageService 任务类
- Message.setDelayTimeLevel 方法设置

## 消息发送到指定队列

- MessageQueueSelector
- DefaultMQProducer#send(Message msg, MessageQueueSelector selector, Object arg)

## Tags的使用

- 一个应用尽可能用一个 Topic，而消息子类型则可以用 tags 来标识
- tags 可以由应用自由设置，只有生产者在发送消息设置了tags，消费方在订阅消息时才可以利用 tags 通过broker做消息过滤：message.setTags("TagA")

## Keys的使用

- 每个消息在业务层面的唯一标识码要设置到 keys 字段，方便将来定位消息丢失问题
- 服务器会为每个消息创建索引（哈希索引），应用可以通过 topic、key 来查询这条消息内容，以及消息被谁消费
- 由于是哈希索引，请务必保证 key 尽可能唯一，这样可以避免潜在的哈希冲突

```java
// 订单Id   
String orderId = "20034568923546";   
message.setKeys(orderId);   
```

## 日志的打印

消息发送成功或者失败要打印消息日志，务必要打印SendResult和key字段，send消息方法只要不抛异常，就代表发送成功，发送成功会有多个状态，在 sendResult 里定义，以下对每个状态进行说明：

- **SEND_OK**

消息发送成功，要注意的是消息发送成功也不意味着它是可靠的，要确保不会丢失任何消息，还应启用同步 Master 服务器或同步刷盘，即 SYNC_MASTER 或 SYNC_FLUSH

- **FLUSH_DISK_TIMEOUT**

消息发送成功但是服务器刷盘超时，此时消息已经进入服务器队列（内存），只有服务器宕机，消息才会丢失，消息存储配置参数中可以设置刷盘方式和同步刷盘时间长度，如果 Broker 服务器设置了刷盘方式为同步刷盘，即 FlushDiskType=SYNC_FLUSH（默认为异步刷盘方式），当Broker服务器未在同步刷盘时间内（默认为5s）完成刷盘，则将返回该状态——刷盘超时

- **FLUSH_SLAVE_TIMEOUT**

消息发送成功，但是服务器同步到 Slave 时超时，此时消息已经进入服务器队列，只有服务器宕机，消息才会丢失，如果Broker服务器的角色是同步 Master，即 SYNC_MASTER（默认是异步Master即ASYNC_MASTER），并且从 Broker 服务器未在同步刷盘时间（默认为5秒）内完成与主服务器的同步，则将返回该状态——数据同步到Slave服务器超时

- **SLAVE_NOT_AVAILABLE**

消息发送成功，但是此时 Slave 不可用，如果Broker服务器的角色是同步Master，即 SYNC_MASTER（默认是异步Master服务器即ASYNC_MASTER），但没有配置 slave Broker 服务器，则将返回该状态——无Slave服务器可用

## 消息发送失败处理方式

Producer 的 send 方法本身支持内部重试，重试逻辑如下：

- 至多重试2次
- 如果同步模式发送失败，则轮转到下一个Broker，如果异步模式发送失败，则只会在当前Broker进行重试，这个方法的总耗时时间不超过sendMsgTimeout设置的值，默认10s
- 如果本身向broker发送消息产生超时异常，就不会再重试

以上策略也是在一定程度上保证了消息可以发送成功，如果业务对消息可靠性要求比较高，建议应用增加相应的重试逻辑：比如调用send同步方法发送失败时，则尝试将消息存储到db，然后由后台线程定时重试，确保消息一定到达Broker

上述db重试方式为什么没有集成到MQ客户端内部做，而是要求应用自己去完成，主要基于以下几点考虑：首先，MQ的客户端设计为无状态模式，方便任意的水平扩展，且对机器资源的消耗仅仅是cpu、内存、网络。其次，如果MQ客户端内部集成一个KV存储模块，那么数据只有同步落盘才能较可靠，而同步落盘本身性能开销较大，所以通常会采用异步落盘，又由于应用关闭过程不受MQ运维人员控制，可能经常会发生 kill -9 这样暴力方式关闭，造成数据没有及时落盘而丢失，第三，Producer所在机器的可靠性较低，一般为虚拟机，不适合存储重要数据，综上，建议重试过程交由应用来控制。

## 选择oneway形式发送

通常消息的发送是这样一个过程：

- 客户端发送请求到服务器
- 服务器处理请求
- 服务器向客户端返回应答

所以，一次消息发送的耗时时间是上述三个步骤的总和，而某些场景要求耗时非常短，但是对可靠性要求并不高，例如日志收集类应用，此类应用可以采用oneway形式调用，oneway形式只发送请求不等待应答，而发送请求在客户端实现层面仅仅是一个操作系统系统调用的开销，即将数据写入客户端的socket缓冲区，此过程耗时通常在微秒级

# RocketMQ 消费者

## PushConsumer 配置

| 参数名                       | 默认值                        | 说明                                                         |
| ---------------------------- | ----------------------------- | ------------------------------------------------------------ |
| consumerGroup                | DEFAULT_CONSUMER              | Consumer组名，多个Consumer如果属于一个应用，订阅同样的消息，且消费逻辑一致，则应该将它们归为同一组 |
| messageModel                 | CLUSTERING                    | 消费模型支持集群消费和广播消费两种                           |
| consumeFromWhere             | CONSUME_FROM_LAST_OFFSET      | Consumer启动后，默认从上次消费的位置开始消费，这包含两种情况：一种是上次消费的位置未过期，则消费从上次中止的位置进行；一种是上次消费位置已经过期，则从当前队列第一条消息开始消费 |
| consumeTimestamp             | 半个小时前                    | 只有当consumeFromWhere值为CONSUME_FROM_TIMESTAMP时才起作用。 |
| allocateMessageQueueStrategy | AllocateMessageQueueAveragely | Rebalance算法实现策略                                        |
| subscription                 |                               | 订阅关系                                                     |
| messageListener              |                               | 消息监听器                                                   |
| offsetStore                  |                               | 消费进度存储                                                 |
| consumeThreadMin             | 10                            | 消费线程池最小线程数                                         |
| consumeThreadMax             | 20                            | 消费线程池最大线程数                                         |
| consumeConcurrentlyMaxSpan   | 2000                          | 单队列并行消费允许的最大跨度                                 |
| pullThresholdForQueue        | 1000                          | 拉消息本地队列缓存消息最大数                                 |
| pullInterval                 | 0                             | 拉消息间隔，由于是长轮询，所以为0，但是如果应用为了流控，也可以设置大于0的值，单位毫秒 |
| consumeMessageBatchMaxSize   | 1                             | 批量消费，一次消费多少条消息                                 |
| pullBatchSize                | 32                            | 批量拉消息，一次最多拉多少条                                 |

## pushConsumer 集群消费模式

- clustering 模式（默认）
- GroupName 用于把多个 Consumer 组织到一起
- 相同 GroupName 的 Consumer 只消费所订阅消息的一部分
- 目的：达到天然的负载均衡机制

## pushConsumer 广播消费模式

- broadcasting 模式
- 同一个 CunsumerGroup 里的 Consumer 都消费订阅 Topic 全部信息
- 每一条消息都会被每一个 Consumer 消费
- setMessageModel

## Offset

- 消息消费进度的核心
- 指某个 Topic 下的一条消息在某个 MessageQueue 里的位置
- 通过 offset 可以定位到这条消息
- 存储实现
  - 远程文件存储
    - 默认集群模式 clustering 采用远程文件存储 offset
    - 本质上因为多消费模式，每个 Consumer 消费所订阅主题的一部分
    - 这种情况需要 broker 控制 offset 的值，使用 RemoteBrokerOffsetStore
  - 本地文件存储
    - 广播模式下，由于每个 Consumer 都会收到消息并消费
    - 各个 Consumer 之间没有任何干扰，独立线程消费
    - 所以使用 LocalBrokerOffsetStore，也就是把 offset 存储到本地

## PushConsumer 长轮询模式分析

- DefaultPushConsumer 是使用长轮询模式进行实现

## PullConsumer 配置

| 参数名                           | 默认值                        | 说明                                                         |
| -------------------------------- | ----------------------------- | ------------------------------------------------------------ |
| consumerGroup                    | DEFAULT_CONSUMER              | Consumer组名，多个Consumer如果属于一个应用，订阅同样的消息，且消费逻辑一致，则应该将它们归为同一组 |
| brokerSuspendMaxTimeMillis       | 20000                         | 长轮询，Consumer拉消息请求在Broker挂起最长时间，单位毫秒     |
| consumerTimeoutMillisWhenSuspend | 30000                         | 长轮询，Consumer拉消息请求在Broker挂起超过指定时间，客户端认为超时，单位毫秒 |
| consumerPullTimeoutMillis        | 10000                         | 非长轮询，拉消息超时时间，单位毫秒                           |
| messageModel                     | BROADCASTING                  | 消息支持两种模式：集群消费和广播消费                         |
| messageQueueListener             |                               | 监听队列变化                                                 |
| offsetStore                      |                               | 消费进度存储                                                 |
| registerTopics                   |                               | 注册的topic集合                                              |
| allocateMessageQueueStrategy     | AllocateMessageQueueAveragely | Rebalance算法实现策略                                        |

**Pull 方式主要做了三件事**

- 获取 MessageQueue 并遍历
- 维护 OffsetStore
- 根据不同的消息状态做不同的处理

# RocketMQ 核心原理解析

## 消息存储结构

## 同步刷盘与异步刷盘

## 同步复制与异步复制

- 同一组 Broker 有 Master-Slave 角色
- 异步复制
- 同步复制

## 高可用机制

- Master-Slave 高可用
- BrokerId
- Master 读、写，Slave 只复制读
- 当 Master 繁忙不可用时，可以自动切换到 Slave 读取消息
- 生产环境一般是：同步双写，异步刷盘

## NameServer 协调者

- 集群状态服务器
- 部署相互独立，并无关联

```java
private final HashMap<String/* topic */, List<QueueData>> topicQueueTable;
private final HashMap<String/* brokerName */, BrokerData> brokerAddrTable;
private final HashMap<String/* clusterName */, Set<String/* brokerName */>> clusterAddrTable;
private final HashMap<String/* brokerAddr */, BrokerLiveInfo> brokerLiveTable;
private final HashMap<String/* brokerAddr */, List<String>/* Filter Server */> 
```

