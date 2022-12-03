# HDFS

## 存储模型

- 文件线性按照字节切割成块 (block) ，具有 offset，id
- 文件和文件的 block 可以大小不一样
- 一个文件除最后一个 block ，其他 block 大小一致
- block 的大小依据硬件的 I/O 特性调整
- block 被分散存放在集群的节点中，具有 location
- block 具有副本 (replication)，没有主从概念，副本不能同时出现在同一个节点
- 副本是满足可靠性和性能的关键
- 副本上传可以指定 block 大小和副本数，上传后只能修改副本数 
- 一次写入多次读取，不支持修改
- 支持追加数据

## 架构设计

- HDFS 是一个主从 (Master/Slaves) 架构
- 由一个 NameNode 和 DataNode 组成
- 面向文件，包含：文件数据 (data) 和元数据 (metadata)
- NameNode 负责存储和管理文件元数据，并维护了一个层次性的文件目录树
- DataNode 负责存储文件数据 (block)，并提供 block 的读写
- DataNode 与 NameNode 维持心跳，并汇报自己持有的 block 信息
- Client 与 NameNode 交互文件元信息和 DataNode 交互文件 block 数据

## 角色功能

- NameNode
  - 完全基于内存存储文件元数据，目录结构，文件 block 的映射
  - 需要持久化方案保证数据可靠性
  - 提供副本放置策略
- DataNode
  - 基于本地磁盘存储 block (文件的形式)
  - 保存 block 的校验和数据保证 block 的可靠性
  - 与 NameNode 保持心跳，汇报 block 列表状态

## 元数据持久化

- 任何对文件系统元数据产生修改的操作，Namenode 都会使用一种称为 EdiLog 的事务日志记录下来
- 使用 Fslmage 存储内存所有的元数据状态
- 使用本地磁盘保存 EditLog 和 Fsimage
- EditLog 具有完整性，数据丢失少，但恢复速度慢，井有体积膨帐风险
- Fslmage 具有恢复速度快，体积与内存数据相当，但不能实时保存，数据丢失多
- NameNode 使用了 Fslmage + EditLog 整合的方案
  - 滚动将增量的 Editlog 更新到 Fsimage，以保证更近时点的 Fslmage 和更小的 Editlog 体积

## 安全模式

- HDFS搭建时会格式化，格式化操作会产生一个空的 Fslmage
- 当 Namenode 启动时，它从硬盘中读取 Editlog 和 Fslmage，将所有 Editlog 中的事务作用在内存中的 Fslmage上，并将这个新版本的Fslmage 从内存中保存到本地破盘上，然后删除旧的 Editlog，因为这个旧的 Editlog 的事务都已经作用在 Fslmage 上了

- NameNode 启动后会进入一个称为安全模式的特殊状态
- 处于安全模式的 NameNode 是不会进行数据块的复制的
- NameNode 从所有的 DataNode接收心跳信号和块状态报告
- 每当 NameNode 检测确认某个数据块的副本数目达到这个最小值，那么该数据块就会被认为是副本安全 (safely replicated) 的
- 在一定百分比 (这个参数可配置) 的数据块被 NameNode 检测确认是安全之后，加上一个额外的 30 秒等待时间，NameNode 将退出安全模式状态
- 接下来它会确定还有哪些数据块的副本没有达到指定数目，井将这些数据块复制到其他 DataNode 上

## HDFS 中的SNN

- 在非 Ha 模式下，SNN 一般是独立的节点，周期完成对 NN 的 EditLog 向 Fsimage 合并，减少 EditLog 大小，减少 NN 启动时间
- 根据配置文件设置的时间间隔 fs.checkpoint.period 默认3600秒
- 根据配置文件设置 edits log 大小 fs.checkpoint.size 规定 edits 文件的最大值默认是64MB

## Block 的副本放置策略

- 第一个副本：放置在上传文件的 DN，如果是集群外提交，则随机挑选一台磁盘不太满，CPU不太忙的节点
- 第二个副本：放置在于第一个副本不同的机架的节点上
- 第三个副本：与第二个副本相同机架的节点
- 更多副本：随机节点

## HDFS 写流程

- Client 和 NN 连接创建文件元数据
- NN 判定元数据是否有效
- NN触发副本放置策路，返回一个有序的 DN 列表
- Client 和 DN 建立 Pipeline 连接
- Client 将块切分成 packet (64KB)，井使用 chunk (512B) tchucksum (48）填充
- Client 将 packet放入发送队列 dataqueue 中，井向第一个 DN 发送
- 第一个 DN 收到 packet 后本地保存并发送给第二个 DN
- 第二个 DN 收到 packet 后本地保存并发送给第三个 DN
- 这一个过程中，上游节点同时发送下一个 packet
  - 生活中类比工厂的流水线：结论：流式其实也是变种的并行计算
- HDFS 使用这种传输方式，副本数对于 client 是透明的
- 当 block 传输完成，DN 们各自向 NN 汇报，同时 client 继续传输下一个 block，所以 client 的传输和 black 的汇报也是并行的

## HDFS 写流程

- 为了降低整体的带宽消耗和读取延时，HDFS 会尽量让读取程序读取离它最近的副本
- 如果在读取程序的同一个机架上有一个副本，那么就读取该副本
- 如果一个 HDFS 集群跨越多个数据中心，那么客户端也将首先读本地数据中心的副本
- 下载一个文件
- Client 和 NN 交互文件元数据获取 fileBlockLocation
- NN 会按距离策略排序返回
- client 尝试下载 block 并校验数据完整性
- 下载一个文件其实是获取文件的所有的 block 元数据，那么子集获取某些 block 应该成立
- HDFS 支持 client 给出文件的 offset 并自定义连接哪些 blcck 的 DN 服务
- 这个是支持计算层的分治、并行计算的核心

## HDFS 解决方案

- 单点故障
  - 高可用方案：HA（High Available）
  - 多个NN，主备切换
- 压力过大，内存受限
  - 联帮机制： Federation（元数据分片）
  - 多个NN，管理不同的元数据
- HADOOP 2.x 只支持HA的一主一备