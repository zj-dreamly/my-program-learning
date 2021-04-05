## 简介

- 中间件，提供协调服务
- 作用于分布式系统，发挥其优势，可以为大数据服务
- 支持java，提供Java和C语言的客户端API

### 分布式系统

- 很多台计算机组成一个整体，一个整体一致对外并且处理同一请求
- 内部的每台计算机都可以相互通信（rets / rpc）
- 客户端到服务端的一次请求到相应结束会历经多台计算机

### zookeeper特性

- 一致性：数据一致性，数据按照顺序分批入库
- 原子性：事务要么成功，要么失败，不会局部化
- 单一视图：客户端连接集群中的任一zk节点，数据都是一致的
- 可靠性：每次对zk的操作状态都会保存到服务端
- 实时性：客户端可以读取到zk服务端的最新数据

### zoo.cfg配置

- tickTime：用于计算的时间单元，比如 session 超时，：N*tickTime
- initLimit：用于集群，允许从节点连接并同步到 master 节点的初始化连接时间，以 tickTime的倍数来表示
- syncLimit：用于集群，master主节点与从节点之间发送消息，请求和应答时间长度（心跳机制）
- dataDir：必须配置
- dataLogDir：日志目录，如果不配置会和dataDir公用
- clientPort：连接服务器的端口，默认2181

### zookeeper数据结构

- 是一个树形结构，类似于前端开发的 tree.js 组件

- 每一个节点都称之为 znode，它可以有子节点，也可以有数据
- 每个节点分为临时节点和永久节点，临时节点在客户端断开后消失

- 每个 zk 节点都有各自的版本号，可以通过命令行来显示节点信息
- 每当节点数据发生变化，那么该节点的版本号会累加（乐观锁）
- 删除 / 修改过时节点，版本号不匹配则会报错
- 每个 zk 节点存储的数据不宜过大，几 k 即可
- 节点可以设置权限 acl ，可以通过权限来限制用户的访问

### zk作用

- master节点选举，主节点挂了之后，从节点就会接手工作，并且保证这个节点是唯一的，这也是所谓的首脑模式，从而保证我们的集群是高可用的

- 统一配置文件管理，即只需要部署一台服务器，则可以把相同的配置文件同步更新到其他服务器，此操作在云计算中用的特别多
- 发布与订阅，类似消息队列MQ，dubbo发布者把数据存储到znode上，订阅者会读取这个数据
- 提供分布式锁，分布式环境不同进程之间争夺资源，类似于多线程的锁
- 集群管理，集群中保证数据的强一致性

### zk常用命令行

#### 打开zk客户端进行连接server

```sh
./zkCli.sh
```

#### 查看zk目录

```shell
ls [path]
```

#### 查看zk节点详情

```shell
ls2 [path]
get [path]
stat [path]
```

#### create 命令

```shell
create [-s] [-e] path data acl
```

####  set 命令

```shell
set [path] data [version]
```

#### del 命令

```shell
delete [path] version
```



### zk特性-session基本原理

- 客户端与服务端之间的连接存在会话
- 每个会话都会设置一个和超时时间
- 心跳结束（异常），session 则过期
- Session过期，则临时节点znode会被抛弃
- 心跳机制：客户端向服务端的 ping 包请求

### zk特性-watcher机制

- 针对每个节点的操作，都会有一个监督者 -> watcher
- 当监控的某个对象 (znode) 发生了变化，则触发 wathcer 事件
- zk 中的 watcher 是一次性的，触发后立即销毁
- 父节点，子节点增删改都能够触发其 watcher
- 针对不同类型的操作了，触发的 wathcer 事件也不同
    - （子）节点创建事件
    - （子）节点删除事件
    - （子）节点数据变化事件

#### watcher 命令行学习

- 通过 get path [version] 设置 watcher
- 父节点增删改操作触发watcher
- 子节点增删改操作触发watcher

#### watcher 事件类型

- 创建父节点触发：NodeCreated
- 修改父节点数据触发：NodeDataChanged
- 删除父节点触发：NodeDeleted
- ls 为父节点设置 watcher，创建子节点触发：NodeChildrenChanged

- ls 为父节点设置 watcher，删除子节点触发：NodeChildrenChanged

- ls 为父节点设置 watcher，修改子节点不触发事件

#### watcher 使用场景

- 统一资源配置

### ACL 权限控制

- 针对节点可以设置相关读写操作，目的为了保障数据的安全性
- 权限 permissions 可以指定不同的权限范围以及角色

#### ACL 命令行

- getAcl：获取某个节点的acl权限信息
- setAcl：设置某个节点的acl权限信息
- addauth：输入认证授权信息，注册时输入明文密码，但是在 zk 中，密码是以加密的形式存在的
- world:anyone:crdwa
- auth:user:pwd:cdrwa
- digesty:username:BASE64(SHA1(password)):[permissions]
- ip:192.168.1.1:d=crdwa
- Super
    - 修改 zkServer.sh，增加super管理员
    - 重启 zkServer.sh

#### ACL 构成

zk的 acl 通过 [scheme:userId:permissions] 来构成权限列表

- scheme：代表采用的某种权限机制

    - world：world下只有一个id，即只有一个用户，也就是anyone，那么组合的写法就是world:anyone:[permissions]
    - auth：代表认证登录，需要注册用户有权限就可以，形式为auth:user:password:[permissions]
    - digest：需要对密码加密才能访问，组合形式为：digesty:username:BASE64(SHA1(password)):[permissions]
    - IP：当设置的IP为指定的IP地址，此时进行IP限制访问，比如ip:192.168.1.1:[permissions]
    - super：代表超级管理员，拥有所有的权限

- id：代表允许访问的用户

- permissions：权限字符串

    - 权限字符串：crdwa

    - CREATE：创建子节点
    - READ：获取节点/子节点
    - WRITE：设置节点数据
    - DELETE：删除节点数据
    - ADMIN：设置权限

#### ACL 使用场景

- 开发/测试环境分离，开发者无权操作测试库的节点，只能看
- 生产环境控制指定IP的服务可以访问相关节点，防止混乱

### zk 四字命令（Four Letter Words）

- zk 可以通过它自身提供的简写命令来和服务器进行交互
- 需要使用到 nc 命令，安装：yum install nc
- echo [commond] | nc [ip] [port]
- [stat] 查看 zk 的状态信息，以及是否 mode
- [ruok] 查看当前 zkserver 是否启动，返回 imok
- [dump] 列出未经处理的会话和临时节点
- [conf] 展示连接到服务器的客户端信息
- [envi] 环境变量
- [mntr] 监控 zk 健康信息
- [wchs] 展示watch信息
- [wchc] 与 [wchp] session和watch及path与watch的信息

### zk集群

- 主从节点，心跳机制（选举模式）
- 配置数据文件 myid 1/2/3 对应server.1/2/3

#### zk Java api使用

- 会话连接恢复
- 节点增删改查
- watch与acl操作

### 常用的zk java客户端

- zk 原生api
- zkclient
- Apache curator

#### 原生api缺陷

- 超时重连不支持，需要手动操作
- watch注册一次后会失效
- 不支持递归创建节点

#### Apache curator

- 解决watch的注册一次就失效
- Api更加简单易用
- 分布式锁

## Dubbo

- 最大程度解耦
- 生产者消费者模式
- zk注册中心，admin监控中心，协议支持