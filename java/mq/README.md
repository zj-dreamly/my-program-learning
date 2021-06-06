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

- 主备模式：一个主 / 方案，如果主节点宕机，从节点提供服务，和 ActiveMQ 利用 zookeeper 做主备一样

- 远程模式：远距离通信和复制，可以实现双活的一种模式，简称 Shovel 模式，把消息进行不同数据中心的复制工作，可以跨地域的让两个 MQ 集群互联

- 镜像模式：保证 100% 数据不丢失
- 多活模式：实现异地数据复制的主流模式，因为 Shovel 模式配置比较复杂，所以一般来说，实现异地集群都是使用这种双活或者多活模型来实现
- federation插件

#### Kafka

- kafka 是LinkedIn开源的分布式消息系统
- 基于 Pull 的模式处理消息消费，追求高吞吐量，一开始的目的就是为了日志手机和传输
- 0.8版本开始支持复制，不支持事务，对消息的重复，丢失，错误没有严格要求，适合产生大量的互联网服务的数据收集业务
- 特点
  - 分布式
  - 跨平台
  - 伸缩性
  - 实时性
- 高性能的因素
  - 顺序写，Page Cache
  - 后台异步，主动Flush
  - IO调度
  - zero copy
- 集群模式
  - zookeeper 集群

