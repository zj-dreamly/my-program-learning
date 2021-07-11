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

示例项目已经迁移：

- RabbitMQ 地址：https://github.com/zj-dreamly/imooc-architect-rabbitmq
- Kafka 地址：https://github.com/zj-dreamly/imooc-architect-kafka

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

### elk

- Kafka 高吞吐量核心实战-日志过滤（logstash）
- Kafka 高吞吐量核心实战-日志持久化（elasticsearch）
- Kafka 高吞吐量核心实战-日志可视化（Kibana）

### JPS

- jps -v

### Spring 手动控制事务

> Spring的自动提交事务在开启事务的方法结束后就提交

#### 获取事务

- PlatformTransactionManager
- TransactionDefinition

- PlatformTransactionManager#getTransaction

#### 提交事务

- PlatformTransactionManager#commit

#### 回滚事务

- PlatformTransactionManager#rollback

### 分布式锁

#### 数据库分布式锁

- select...for update

#### redis分布式锁

- 加锁
  - SET resource_name my_random_value NX PX 30000
    - resource_name：资源名称，可根据不同的业务区分不同的锁
    - my_random_value ：随机值，每个线程的随机值都不同，用于释放锁的校验
    - NX：key 不存在的时候设置成功，key 存在则设置不成功
    - PX：自动失效时间，出现异常情况，锁可以过期释放

- 释放锁
  - 采用 Redis的 delete 命令
  - 释放锁时校验之前设置的随机数，相同才能释放
  - 释放锁需要LUA脚本

> 可直接使用 Redisson 提供的分布式锁：Redisson.getLock

#### zookeeper分布式锁

- 利用 Zookeeper 的瞬时有序节点的特性

- 多线程并发创建瞬时节点时，得到有序的序列
- 序号最小的线程获得锁
- 其他的线程则监听自己序号的前一个序号
- 前一个线程执行完成，删除自己序号的节点
- 下一个序号的线程得到通知，继续执行

> 可直接使用 curator 提供的分布式锁：InterProcessMutex.acquire

### 读写分离

### 分库分表

#### 模式

- 中间层代理（例如：MyCat）
- 客户端模式（sharding-jdbc）

#### 数据切片

- 垂直切分
  - 按照业务去划分数据库
  - 不同业务之间，禁止使用跨库 join 联查
  - 优点
    - 拆分后业务清晰，规则明确
    - 系统之间容易扩展和整合
    - 数据维护简单
  - 缺点
    - 业务表无法 join，只能通过接口调用，提升复杂度
    - 跨库事务难以处理
    - 某些业务数据过于庞大，仍然存在单体性能瓶颈
- 水平切分
  - 将一个表的数据按照某种规则分到不同的数据库
  - 需要确定分片的规则
  - 使用分片字段查询时，可确定实体库，其他字段查询，查询所有表
  - 优点
    - 解决了单库大数据，高并发的性能瓶颈
    - 拆分规则封装好，对应用端几乎透明，开发人员无需关心拆分的细节
    - 提高了系统稳定性和负载能力
  - 缺点
    - 拆分规则很难抽象
    - 分片事务一致性难以解决
    - 二次扩展，数据迁移，维护难度大

#### mycat

- server.xml
  - 配置MyCat的用户名名，密码，权限，Schema
  - 如同给MySql新建用户一样
  - 客户端连接MyCat与连接MySql无异

- schema.xml
  - 配置 dataHost（节点主机）。包括读 host，写 host
  - 配置 dataNode（数据节点），指定到具体数据库
  - 配置 schema，表名，数据节点，分配规则
  - datahost#balance：负载均衡类型，0不开启读写分离，1和2读写均匀分配，3读落在readHost上
  - datahost#writeType：写请求类型：0落在第一个writeHost，1随机

#### MySql主从

- 主配置 log-bin，指定文件的名字
- 主配置 server-id，默认为1
- 从配置 server-id，与主不能重复
- 主创建备份账户并授权 REPLICATION SLAVE
- 主进行锁表 FLUSH TABLES WITH READ LOCK
- 主找到log-bin位置 SHOW MASTER STATUS
- 主备份数据
  - mysqldump --all-databases --master-data > dbdump.db

- 主进行解锁 UNLOCK TABLES
- 从导入 dump 的数据 
- 在从上设置主的配置
  - CHANGE MASTER TO  MASTER_HOST='master_host_name',MASTER_USER='replication_user_name',MASTER_PASSWORD='replication_password',MASTER_LOG_FILE='recorded_log_file_name',MASTER_LOG_POS=recorded_log_position;

- 从库执行  START SLAVE

#### Sharding-Jdbc

##### 逻辑表

水平拆分的数据库（表）的相同逻辑和数据结构表的总称。例：订单数据根据主键尾数拆分为10张表，分别是`t_order_0`到`t_order_9`，他们的逻辑表名为`t_order`。

##### 真实表

在分片的数据库中真实存在的物理表。即上个示例中的`t_order_0`到`t_order_9`。

##### 数据节点

数据分片的最小单元。由数据源名称和数据表组成，例：`ds_0.t_order_0`。

##### 绑定表

指分片规则一致的主表和子表。例如：`t_order`表和`t_order_item`表，均按照`order_id`分片，则此两张表互为绑定表关系。绑定表之间的多表关联查询不会出现笛卡尔积关联，关联查询效率将大大提升。举例说明，如果SQL为：

```sql
SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.order_id in (10, 11);
```

在不配置绑定表关系时，假设分片键`order_id`将数值10路由至第0片，将数值11路由至第1片，那么路由后的SQL应该为4条，它们呈现为笛卡尔积：

```sql
SELECT i.* FROM t_order_0 o JOIN t_order_item_0 i ON o.order_id=i.order_id WHERE o.order_id in (10, 11);

SELECT i.* FROM t_order_0 o JOIN t_order_item_1 i ON o.order_id=i.order_id WHERE o.order_id in (10, 11);

SELECT i.* FROM t_order_1 o JOIN t_order_item_0 i ON o.order_id=i.order_id WHERE o.order_id in (10, 11);

SELECT i.* FROM t_order_1 o JOIN t_order_item_1 i ON o.order_id=i.order_id WHERE o.order_id in (10, 11);
```

在配置绑定表关系后，路由的SQL应该为2条：

```sql
SELECT i.* FROM t_order_0 o JOIN t_order_item_0 i ON o.order_id=i.order_id WHERE o.order_id in (10, 11);

SELECT i.* FROM t_order_1 o JOIN t_order_item_1 i ON o.order_id=i.order_id WHERE o.order_id in (10, 11);
```

其中`t_order`在FROM的最左侧，ShardingSphere将会以它作为整个绑定表的主表。 所有路由计算将会只使用主表的策略，那么`t_order_item`表的分片计算将会使用`t_order`的条件。故绑定表之间的分区键要完全相同。

##### 广播表

指所有的分片数据源中都存在的表，表结构和表中的数据在每个数据库中均完全一致。适用于数据量不大且需要与海量数据的表进行关联查询的场景，例如：字典表。

##### 逻辑索引

某些数据库（如：PostgreSQL）不允许同一个库存在名称相同索引，某些数据库（如：MySQL）则允许只要同一个表中不存在名称相同的索引即可。 逻辑索引用于同一个库不允许出现相同索引名称的分表场景，需要将同库不同表的索引名称改写为`索引名 + 表名`，改写之前的索引名称成为逻辑索引。

#### 分布式事务

##### XA协议

- XA协议是由 X/Open组织提出的分布式的规范
- 由一个事务管理器（TM）和多个资源管理器（RM）组成
- 提交分为两个阶段：prepare 和 commit
- 保证数据的强一致性
- commit 阶段出现问题，事务出现不一致，需要人工处理
- 效率低下，性能与本地数据差10倍
- MySql 5.7 及以上均支持 XA 协议
- MySql connector / j 5.0 以上支持XA协议
- Java系统中，数据源采用 Atomikos

##### MyCat

- 直接支持分布式事务，跟普通配置无区别

##### sharding-jdbc

- 直接支持分布式事务，跟普通配置无区别

##### 事务补偿机制 (TCC)

- 针对每个操作，都要注册一个与其对应的补偿操作
- 执行失败，调用补偿，撤销之前操作
- 逻辑清晰，流程简单
- 数据一致性比 XA 还要差，可能出错的点比较多
- TCC 属于应用层补偿，程序员需要写大量代码

##### 基于本地消息表最终一致性方案

- 采用 BASE 原理，保证事务最终一致
- 在一致性方面，允许一段时间内的不一致

- 将本事务外操作，记录在消息表

##### 基于本地消息表最终一致性方案

#### 幂等性

- 核心思想：通过唯一的业务单号保证幂等性
- 实现
  - 非并发，查询业务单号是否操作过，没有则执行
  - 并发下，整个操作过程加锁
- select 和 delete 操作天然幂等
- update 更新操作通过乐观锁实现幂等性
- insert 操作，此时没有业务单号，通过 token 保证幂等
- 混合操作：有业务单号使用分布式锁，没有通过 token 保证幂等性，后台生成 token 返回前端，前端提交数据，根据token获取分布式锁，获取成功执行操作，获取失败不执行操作

> 举例用户下订单操作，后端生成下单操作 token并存储到 redis 中，前端提交下单数据，先根据 token 获取分布式锁，之后查询 redis 是否有下单操作 token，如果有就完成下单，如果没有就不操作，也就是在下单页面只能完成一次下单

------



#### LUA

- Redis 内置 lua 解释器，执行过程原子性，脚本预编译
- 嵌入式开发

#### LUA&Redis

- 预加载：script load ""
- 执行：evalsha "sha" key1 val1
- 是否存在该脚本：script exists ""
- 脚本清除：script flush

#### MicroService

- CAP：一致性，可用性，分区容错性 
