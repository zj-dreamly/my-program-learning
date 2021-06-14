### 数据库建模工具

- pdman

### Spring事务传播行为

| 事务传播行为类型          | 说明                                                         |
| ------------------------- | ------------------------------------------------------------ |
| PROPAGATION_REQUIRED      | 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。 |
| PROPAGATION_SUPPORTS      | 支持当前事务，如果当前没有事务，就以非事务方式执行。         |
| PROPAGATION_MANDATORY     | 使用当前的事务，如果当前没有事务，就抛出异常。               |
| PROPAGATION_REQUIRES_NEW  | 新建事务，如果当前存在事务，把当前事务挂起。                 |
| PROPAGATION_NOT_SUPPORTED | 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。   |
| PROPAGATION_NEVER         | 以非事务方式执行，如果当前存在事务，则抛出异常。             |
| PROPAGATION_NESTED        | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。 |

### Keepalived

- 解决单点故障

- 组件免费
- 可以实现高可用HA机制
- 基于VRRP协议
  - Virtual Router Redundancy Protocol（虚拟路由冗余协议）
  - 解决内网单机故障的路由协议
  - 构建有多个路由器MASTER BACKUP
  - 虚拟IP-VIP（ Virtual IP Address）

### LVS

- Linux Virtual Server
- 章文嵩博士主导的负载均衡开源项目
- LVS（ipvs：IP Virtual Server）已被集成到Linux内核

#### 优点

- 基于4层网络协议，工作效率高
- 单个 Nginx 承受不了压力，需要集群
- LVS 充当 Nginx 集群的调度者
- Nginx 接受请求来回，LVS 可以只接受不响应

#### 模式

- NAT
  - 基于网络地址转换
- TUN
  - IP隧道
- DR
  - 直接路由模式

#### arp-ignore

- arp 响应级别（处理请求）
- 0：只要本机配置了IP，就能响应请求
- 1：请求的目标地址到达对应的网络接口，才会请求响应

#### arp-announce

- ARP 通告行为（返回响应）
- 0：本机上任何网络接口都对外通告，所有的网卡都能接受到通告
- 1：尽可能避免本网卡与不匹配的目标进行通告
- 2：只在本网卡通告

### Keepalived + LVS + Nginx高可用

### Redis

#### String

- keys 
- type
- setnx、msetnx
- ttl
- expire
- append
- strlen
- incr、incrby
- decr、decrby
- getrange
- setrange
- mset
- mget
- flushdb、flushall

#### Hash

- hset、hmset
- hget、hmget
- hgetall
- hlen
- hkeys
- hvalues
- hincrby
- hincrbyfloat
- hexists
- hdel

#### List

- lpush
- lrange
- rpush
- lpop
- rpop
- llen
- lindex
- lset
- linsert
- lrem
- ltrim

#### Set

- sadd 
- smembers 
- scard
- sismember
- srem
- spop
- srandmember

- smove
- sdiff
- sinter
- sunion

#### Zset

- zadd
- zrange
- zrank
- zcore
- zcard
- zcount
- zrangebyscore
- zrem

#### StringRedisTemplate

#### 发布订阅

- subscribe、psubscribe

- publish

#### 主从

- RDB复制

- 无磁盘化复制

#### 缓存过期机制

- （主动）定期删除
- （被动）惰性删除

#### 内存淘汰管理机制

- maxmemory
  - noeviction：当内存使用超过配置的时候会返回错误，不会驱逐任何键
  - allkeys-lru：加入键的时候，如果过限，首先通过LRU算法驱逐最久没有使用的键
  - volatile-lru：加入键的时候如果过限，首先从设置了过期时间的键集合中驱逐最久没有使用的键
  - allkeys-random：加入键的时候如果过限，从所有key随机删除
  - volatile-random：加入键的时候如果过限，从过期键的集合中随机驱逐
  - volatile-ttl：从配置了过期时间的键中驱逐马上就要过期的键
  - volatile-lfu：从所有配置了过期时间的键中驱逐使用频率最少的键
  - allkeys-lfu：从所有键中驱逐使用频率最少的键

#### 哨兵模式

#### Redis集群

- 哈希槽 -16384
- keys * 只会返回当前节点拥有的key

#### 缓存穿透

- 缓存空对象
- 布隆过滤器
  - 优点
    - 相比于其它的数据结构，布隆过滤器在空间和时间方面都有巨大的优势。布隆过滤器存储空间和插入/查询时间都是常数。另外, Hash 函数相互之间没有关系，方便由硬件并行实现。布隆过滤器不需要存储元素本身，在某些对保密要求非常严格的场合有优势
    - 布隆过滤器可以表示全集，其它任何数据结构都不能
    - k 和 m 相同，使用同一组 Hash 函数的两个布隆过滤器的交并差运算可以使用位操作进行
  - 缺点
    - 误算率（False Positive）是其中之一。随着存入的元素数量增加，误算率随之增加。但是如果元素数量太少，则使用散列表足矣
    - 一般情况下不能从布隆过滤器中删除元素. 我们很容易想到把位列阵变成整数数组，每插入一个元素相应的计数器加1, 这样删除元素时将计数器减掉就可以了。然而要保证安全的删除元素并非如此简单。首先我们必须保证删除的元素的确在布隆过滤器里面. 这一点单凭这个过滤器是无法保证的。另外计数器回绕也会造成问题

#### 缓存雪崩

- 不设置过期时间
- redis高可用
- 限流升级
- 数据预热
- 多缓存结合

### MQ

#### RabbitMQ

https://github.com/zj-dreamly/my-program-learning/blob/master/my-work-doc/mq/rabbitmq%E5%AD%A6%E4%B9%A0.md

#### Kafka

##### Kafka的主要特点

Kafka是分布式发布-订阅消息系统。它最初由LinkedIn公司开发，之后成为Apache项目的一部分。Kafka是一个分布式的，可划分的，冗余备份的持久性的日志服

用于处理活跃的流式数据。

kafka的主要特点：

同时为发布和订阅提供高吞吐量。据了解，Kafka每秒可以生产约25万消息（50 MB），每秒处理55万消息（110 MB）。

可进行持久化操作。将消息持久化到磁盘，因此可用于批量消费，例如ETL，以及实时应用程序。通过将数据持久化到硬盘以及r

止数据丢失。

分布式系统，易于向外扩展。所有的producer、broker和consumer都会有多个，均为分布式的。无需停机即可扩展机器。

消息被处理的状态是在consumer端维护，而不是由server端维护。当失败时能自动平衡。

支持online和offline的场景。

##### Kafka的架构

Kafka的整体架构非常简单，是显式分布式架构，producer、broker（kafka）和consumer都可以有多个。Producer，consumer实现Kafka注册的接口，数据从prbroker，broker承担一个中间缓存和分发的作用。broker分发注册到系统中的consumer。broker的作用类似于缓存，即活跃的数据和离线处理系统之间的缓存。客器端的通信，是基于简单，高性能，且与编程语言无关的TCP协议。

- 基本概念
  - Topic：特指Kafka处理的消息源（feeds of messages）的不同分类。
  - Partition：Topic物理上的分组，一个topic可以分为多个partition，每个partition是一个有序的队列。partition中的每条消息都会被分序的id（offset）。
  - Message：消息，是通信的基本单位，每个producer可以向一个topic（主题）发布一些消息。
  - Producers：消息和数据生产者，向Kafka的一个topic发布消息的过程叫做producers。
  - Consumers：消息和数据消费者，订阅topics并处理其发布的消息的过程叫做consumers。
  - Broker：缓存代理，Kafka集群中的一台或多台服务器统称为broker。

- 发送消息的流程
  - Producer根据指定的partition方法（round-robin、hash等），将消息发布到指定topic的partition里面，kafka集群接收到Producer发过来的消息后，将其持久化到硬盘，并保留消息指定时长（可配置），而不关注消息是否被消费。
  - Consumer从kafka集群pull数据，并控制获取消息的offset

##### kafka的优秀设计

接下来我们从kafka的吞吐量、负载均衡、消息拉取、扩展性来说一说kafka的优秀设计。

高吞吐是kafka需要实现的核心目标之一，为此kafka做了以下一些设计：

- 内存访问：直接使用 linux 文件系统的cache，来高效缓存数据，对数据进行读取和写入。
- 数据磁盘持久化：消息不在内存中cache，直接写入到磁盘，充分利用磁盘的顺序读写性能。
- zero-copy：减少IO操作步骤
  - 采用linux Zero-Copy提高发送性能。传统的数据发送需要发送4次上下文切换，采用sendfile系统调用之后，数据直接在内核统上下文切换减少为2次。根据测试结果，可以提高60%的数据发送性能。Zero-Copy详细的技术细节可以参考：https://www.developerworks/linux/library/j-zerocopy/
- 对消息的处理
  - 支持数据批量发送

#### elk

- Kafka 高吞吐量核心实战-日志过滤（logstash）
- Kafka 高吞吐量核心实战-日志持久化（elasticsearch）
- Kafka 高吞吐量核心实战-日志可视化（Kibana）

#### JPS

- jps -v
