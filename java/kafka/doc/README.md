# 初识 Kafka

- a distributed streaming platform
- Kafka 是基于zookeeper 的分布式消息系统
- Kafka 具有高吞吐量、高性能实时及高可靠特点

# 常见命令

## 启动 Kafka

```css
bin/kafka-server-start.sh config/server.properties &
```

## 停止 Kafka

```css
bin/kafka-server-stop.sh
```

## 创建 Topic

```css
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic jiangzh-topic
```

## 查看已经创建的 Topic 信息

```css
bin/kafka-topics.sh --list --zookeeper localhost:2181
```

## 发送消息

```css
bin/kafka-console-producer.sh --broker-list 192.168.220.128:9092 --topic jiangzh-topic
```

## 接收消息

```css
bin/kafka-console-consumer.sh --bootstrap-server 192.168.220.128:9092 --topic jiangzh-topic --from-beginning
```

# Consumer 注意事项

- 单个分区的消息只能由 ConsumerGroup 中的某个 Consumer 消费
- Consumer 从 parttition 中消费是顺序，默认从头开始消费
- 单个 ConsumerGroup 会消费所有 partition 中的消费

# Kafka Stream

# Kafka Connect

# 面试题

## Kafka 常见应用场景

- 日志收集或者流式系统
- 消息系统
- 用户活动追踪或者运营指标监控

## Kafka 吞吐量为什么大

- 日志顺序读写和快速检索
- Parttition 机制
- 批量发送接收及数据压缩机制
- 通过 sendfile 实现零拷贝原则

## Kafka 底层原理之日志

- Kafka 的日志是以 Parttition 为单位进行保存
- 日志目录格式为 Topic 名称 + 数字
- 日志文件格式是一个日志条目序列
- 每条日志消息由 4 字节整形和 N 字节消息组成
- 每个 Parttition 的日志会分为 N 个大小相等的 segment
- 每个 segment 中消息数量不一定相等
- 每个 Parttition 只支持顺序读写
- Parttition 会将消息添加到最后一个 segment
- 当 segment 达到一定阈值会 flush 到磁盘
- 每一个 segment 文件分为 index 和 data

## Kafka 零拷贝

- 一般网络传输4次拷贝
  - 磁盘文件->内核缓冲区->用户缓冲区->socket缓冲区->网卡接口
  - 线程上下文切换
- 零拷贝（2次）
  - 使用 transferTo 函数
  - 磁盘文件->内核缓冲区->网卡接口
  - 避免了线程上下文切换

## Kafka 消费者和生产者组

- Kafka 消费者组是 Kafka 消费的单位
- 单个 Parttition 只能由消费者组中的某个消费者消费
- ConsumerGroup 的单个消费者可以消费多个 partition 中的消息

## Kafka Producer 客户端

## Kafka 如何保证有序性

- Kafka 特性只支持单 Parttition 有序
- 使用 Kafka key + offset 做到业务有序
- Kafka Topic 删除背后的故事

