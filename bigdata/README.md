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
## 第一部分：HDFS相关问题

### 一、描述一下HDFS的数据写入流程

​		首先由客户端想NameNode服务发起写数据请求，NameNode接收到请求后会进行基本验证，验证内容包括对请求上传的路径进行合法验证其次还要对请求的用户进行权限验证。验证没有问题后，NameNode会响应客户端允许上传。接下来客户端会对文件按照blocksize大小进行切块，切完块后依次以块为单位进行上传。此时客户端会请求上传第一个块信息，服务端接收到上传请求后会依据HDFS默认的机架感知原理默认情况下返回三台存放数据块副本的DataNode机器。客户端接收到机器列表后会依据网络拓扑的原理找到其中一台机器进行传输通道的建立，然后依次和三台机器进行串行连接，这样的连接方式主要的为了减轻客户端本地的IO的压力。当通道建立成功后，客户端会通过HDFS的FSOutputStream流对象进行数据传输，数据传输的最小单位为packet。传输过程中每太DataNode服务器是串行连接，依次将数据传递。最后一个数据块被传输完成后相当于一次写入结束，如果还有数据块需要传输，那就接着传输第二个数据块。



### 二、描述一下HDFS的数据读取流程

​		首先和写数据一样，由客户端向NameNode发出请求，NameNode接收到请求后会进行文件下载路径的合法性校验以及权限验证。如果验证没有问题，就会给客户端返回目标文件的元数据信息，信息中包含目标文件数据块对应的DataNode的位置信息。然后客户端根据具体的DataNode位置信息结合就近原则网络拓扑原理找到离自己最近的一台服务器对数据进行访问下载，最后通过HDFS提供的FSInputStream对象将数据读取到本地。如果有多个块信息 就会请求多次DataNode直到目标文件的全部数据被下载。



### 三、简述HDFS的架构，其中每个服务的作用

​		HDFS是Hadoop架构中的负责完成数据的分布式存储管理的文件系统。非高可用HDFS集群工作的时候会启动三个服务，分别是NameNode 和 DataNode以及SecondaryNameNode 。其中NameNode是HDFS的中心服务，主要维护管理文件系统中的文件的元数据信息，DataNode主要负责存储文件的真实数据块信息，当然在DataNode的数据块信息中也包含一下关于当前数据块的元数据信息 例如 检验值 数据长度 时间戳等。在非高可用HDFS集群中 NameNode和DataNode可以理解为是一对多的关系。二者在集群工作中也要保持通信，通常默认3秒钟会检测一下心跳。最后SecondaryNameNode的工作很单一，就是为了给NameNode的元数据印象文件和编辑日志进行合并，并自己也保留一份元数据信息 以防NameNode元数据丢失后有恢复的保障。

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


### 四、HDFS中如何实现元数据的维护？

​		NameNode的元数据信息是通过fsimage文件 + edits编辑日志来维护的，当NameNode启动的时候fsimage文件和edits编辑日志的内容会被加载到内存中进行合并形成最新的元数据信息，当我们对元数据进行操作的时候，考虑到直接修改文件的低效性，从而不会直接修改fsimage文件，而是会往edits编辑日志文件中追加操作记录。当满足一定条件的时候 我们会让SecondaryNameNode来完成fsimage文件和edits 编辑日志文件的合并，SecondaryNameNode首先会让NameNode停止对正在使用的edits编辑日志文件的使用，并重新生成一个新的edits编辑日志文件。接着把NameNode 的fsimage文件和已停止的edits文件拷贝到本地在内存中将edits编辑日志文件的操作记录合并到fsimage 文件中形成一个最新的fsimage文件，最后会将这个最新的fsimage文件推送给NameNode并自己也备份一份。



### 五、描述一下NN和DN的关系，以及DN的工作流程

​		NameNode和DataNode从数据结构上看，就是一对多的关系，一个HDFS集群中是能只能有一个NameNode用于维护元数据信息，同时会有多个DataNode用于存储真实的数据块。当HDFS集群启动的时候，首先会进入到安全模式下，在安全模式下我们只能对数据进行读取不能进行任何写操作，此时集群的每一台DataNode服务器会向NameNode注册自己，注册成功后DataNode会上报自己的数据块详细信息，当数据块汇报满足 最小副本条件后，安全模式就自动退出。此后 DataNode和NameNode每三秒会通信一次，如果NameNode检测到DataNode没有响应，会继续检测 一直到10分30秒后还没有检测到 就确定当前DataNode不可用。



## 第二部分：MapReduce相关问题

### 一、描述一下手写MR的大概流程和规范

​		首先，从MapReduce程序的结构划分可以分为三部分，第一是 程序的执行入口通常简称为驱动类，驱动类主要编写MR作业的提交流程以及自定义的一些配置项。第二是 Map阶段的核心类需要自定并且继承Hadoop提供的Mapper类，重写Mapper类中的map方法，在map方法中遍写自己的业务逻辑代码将数据处理后利用context 上下文对象的写出落盘。第三是 Reduce阶段的核心类同时也需要继承Hadoop提供的Reducer类，并重写reduce 方法，在reduce方法中编写自己的业务逻辑代码，处理完数据后也是通过context上下文对象将数据写出，这也就是最终的结果文件。



### 二、如何实现Hadoop中的序列化，以及Hadoop的序列化和Java的序列化有什么区别？

​		首先序列化是把内存中的Java对象转化成二进制字节码，反序列化是将二进制字节码转化成Java对象，通常我们在对Java对象进行磁盘持久化写入或者将Java对象作为数据进行网络传输的时候需要进行序列化，相反如果要将J数据从磁盘读出并转化成Java对象需要进行反序列化。实现Hadoop中的序列化需要让JavaBean对象实现Writable接口，并重写write() 方法和readFields()方法，其中write()方法是序列化方法，readFields()方法是反序列化方法。

​		Hadoop序列化和Java序列化的区别在于，Java序列化更重量级，Java序列化的后的结果不仅仅生成二进制字节码文件，同时还会针对当前Java对象生成对应的检验信息以及集成体系结构，这样的话 无形中我们需要维护更多的数据，但是Hadoop序列化不会产生除了Java对象内部属性外的任何信息，整体内容更加简洁紧凑，读写速度相应也会提升很多，这也符合了大数据的处理背景。

### 三、概述一下MR程序的执行流程 

​		简单的描述，MR程序执行先从InputFormat类说起，由InputFormat负责数据读入，并在内部实现切片，每一个切片的数据对应生成一个MapTask任务，MapTask中按照文件的行逐行数据进行处理，每一行数据会调用一次我们自定义的Mapper类的map方法，map方法内部实现具体的业务逻辑，处理完数据会通过context对象将数据写出到磁盘（此处会经历Shuffle过程，详情请参考下面第七问！！！），接下来ReduceTask会开始执行，首先ReduceTask会将MapTask处理完的数据结果拷贝过来，每一组相同key的values会会调用一次我们自定的Reducer类的reduce方法，当数据处理完成后，会通过context对象将数据结果写出到磁盘上。

### 四、InputFormat负责数据写的时候要进行切片，为什么切片大小默认是128M

​			首先切片大小是可以通过修改配置参数来改变的，但是默认情况下是和切块blocksize大小一致，这样做的目的就是为了在读取数据的时候正好能一次性读取一个块的数据，避免了在集群环境下发生跨机器读取的情况，如果跨机器读取会造成二外的网络IO，不利于MR程序执行效率的提升。

​			

### 五、描述一下切片的逻辑（从源码角度描述）

​			MR中的切片是发生在数据读入的阶段中，所以我们要关注InputFormat的实现，通过追溯源码，在InputFormat这个抽象类中有一个getSplits(),这个方法就是我们实现切片的具体逻辑。首先我们先关注两个变量，分别是 minSize 和 maxSize，通过对源码的跟踪默认情况 minSize = 1，maxSize = Long.MAX_VALUE，源码中声明了一个集合List<InputSplit> splits = new ArrayList<InputSplit>();，用于装载将来的切片对象并返回。接下来我们根据提交的job信息获取到当前要进行切片的文件详情，首先判断点前文件是否可以进行切分，这一步主要考虑到一些不支持切分的压缩文件时不能进行切片操作，否则就破坏了数据的完整性，如果当前文件可以切片的话，那么接下来就要计算切片的大小，计算切片大小一共需要三个因子，分别是minSize 、maxSize 、blocksize ，最后通过Math.max(minSize, Math.min(maxSize, blockSize)); 计算逻辑获取到切片大小，默认情况切片大小和数据库块大小一致，如果我们想改变切片大小可以通过修改一下两个配置参数实现 mapreduce.input.fileinputformat.split.minsize mapreduce.input.fileinputformat.split.maxsize，

如果把切片大小调大改mapreduce.input.fileinputformat.split.minsize
如果把切片大小调小改mapreduce.input.fileinputformat.split.maxsize。

当我们可以获取到切片大小后就可以继续往下执行，在最终完成切片之前还有一个关键判断，就是判断剩余文件是否要继续进行切片，如果剩余文件/切片大小>1.1 那就继续切片，否则就不会再进行切片，这个规则考虑的情况就就是让将来的切片尽可能资源使用均衡，不至于很小的文件内容也开启一个MapTask。到此整个切片规则就表述完毕了！

### 六、CombineTextInputFormat机制是怎么实现的

​			CombineTextInputFormat也是InputFormat的一个实现类，主要用于解决小文件场景的。如果我们在处理大量小文件的时候由于默认的切片规则是针对文件进行切片，所以就导致大量MapTask的产生 但是每一个MapTask处理的文件又很小 这样就违背了MapReduce的设计初衷。如果遇到以上场景我们就不能使用默认的切片规则了，而是使用CombineTextInputFormat中的切片规则。CombineTextInputFormat中的切片规则大概思路是 先在Job提交的时候设定一个参数为切片最大的值，当这个值设置好以后并且在Job提交中指定使用InputFormat的实现类为CombineTextInputFormat，那么接下来在切片的过程中首先会把当前文件的大小和设置的切片的最大值进行比较，如果小于切片的最大值那就单独划分为一块，如果大于切片的最大值并且小于两倍的切片的最大值那就把当前文件一分为二划分成两个块，以此类推逐个对文件进行处理，这个过程称之为虚拟过程。最后生成真正的切片的时候 根据虚拟好的文件进行合并，只要合并后文件大小不超过最开始设置好的切片的最大值那就继续追加合并文件直到达到设置好的切片的最大值，此时就会产生一个切片对应生成一个MapTask。

### 七、阐述一下 Shuffle机制流程

​			shuffle是MR执行过程中很重要，也是必不可少的一个过程。当MapTask执行完map() 方法后通过context对象写数据的时候开始执行shuffle过程。首先数据先从Map端写入到环形缓冲区内，写出的数据会根据分区规则进入到指定的分区，并且同时在内存中进行区内排序。环形缓冲区默认大小为100M,当数据的写入的容量达到缓冲区大小的80%，数据开始向磁盘溢写，如果数据很多的情况下 可能发生N次溢写，这样在磁盘上就会产生多个溢写文件，并且保证每个溢写文件中区内数据是有序的，接下来在磁盘中会把多次溢写的文件归并到一起形成一个文件，这归并的过程中会根据相同的分区进行归并排序，保证归并完的文件区内是有序的，到此shuffle过程在Map端就完成了。  接着Map端输出的数据会作为Reduce端的输入数据再次进行汇总操作，此时ReduceTask任务会把每一个MapTask中计算完的相同的分区的数据拷贝到ReduceTask的内存中，如果内存放不下，开始写入磁盘，再接着就是对数据进行归并排序，排完序还要根据相同key进行分组，将来一组相同的key对应的values调用一次reduce方法。如果有多个分区就会产生多个ReduceTask来处理，处理的逻辑都一样。



### 八、在MR程序中由谁来决定分区的数量，哪个阶段环节会开始往分区中写数据？

​			MR程序中，从编码设置的角度分析，在Job提交的时候可以设置ReduceTask的数量，ReduceTask的数量就决定的分区的编号，默认有多少ReduceTask任务就会产生多少个分区，但是具体应该设置多少ReduceTask是由具体的业务决定。在Map阶段的map方法中通过context.write()往出写数据的时候其实就往指定的分区中写数据了。

### 九、阐述MR中实现分区的思路（从源码角度分析）

​			分区是MR中一个重要的概念，通常情况下 分区是由具体业务逻辑决定的，默认情况下不指定分区数量就会有一个分区，如果要指定分区我们可以通过在Job提交的时候指定ReduceTask的数量来指定的分区的数量。我们从Map端处理完数据后，数据就会被溢写到指定的分区中，而决定一个kv数据究竟写到哪个分区中是由Hadoop提供的分区器对象控制的，这个对象叫做Partitioner。Partitioner对象默认的实现HashPartitioner类，通过追溯源码 当我们调用map方法往出写数据的时候，会调用到HashPartitioner，它的规则就是用当前写出数据的key 和在Job提交中设置的ReducesTask的数量做取余运算，得到的结果就是当前数据要写入的分区的分区编号。 除此之外，我们也可以自定义分区器对象，需要继承Hadoop提供的Partitioner对象，然后重写getPartition() 方法，在该方法中根据自己的业务实现分区编号的返回。最后再将我们自定义的分区器对象设置到Job提交的代码中覆盖默认的分区规则。

### 十、Hadoop中实现排序的两种方案分别是什么

​		第一种实现方式是 直接让参与比较的对象实现 WritableComparable 接口，并指定泛型，接下来
实现compareTo() 方法 在该方法中实现比较规则即可。

​	    第二种实现方式是 自定义一个比较器对象，需要继承WritableComparator类 ，重写它的compare方法，注意在构造器中调用父类对当前的要参与比较的对象进行实例化。注意当前要参与比较的对象必须要实现WritableComparable 接口。最后在Job提交代码中将自定义的比较器对象设置到Job中就可以了。

### 十一、描述一下Hadoop中实现排序比较的规则（源码角度分析）

​		Hadoop中的排序比较，本质上就是给某个对象获取到一个比较器对象，至于比较逻辑就直接调用该比较器对象中的compareTo() 方法来实现即可！接下来我们主要聊聊如何给一个对象获取比较器对象。从源码角度分析，我们在Hadoop的MapTask类中的init方法中关注一行代码comparator=job.getOutputKeyComparator();  此代码就是在获取比较器对象，跟到该方法中，首先源码中会先从Job配置中获取是否指定过自定义的比较器对象，如果获取到已经设置的比较器对象的Class文件，接下来就会利用反射将比较器对象创建出来，到此获取比较器对象的流程就结束了。

​		如果我们没有在Job中设置自定义的比较器对象，那就没办法用反射的形式获取比较器对象，接下来Hadoop框架会帮助我们创建，具体思路如下：

1.在获取之前有个前提 判断当前job的MapOutputKeyClass 是否实现了WritableComparable接口，因为我们       实现比较的时候是根据key进行比较的，所以重点关注MapOutputKeyClass。

2.如果上面一步正常，MapOutputKeyClass实现了WritableComparable接口，接下来考虑到参与比较的对象是Hadoop自身的数据类型，例如Text 、LongWritable 等，这些数据类型在类加载的时候就已将获取到了比较器对象，并且以当前对象的class为key,一当前对象的比较器对象为value维护在内存中的一个叫做comparators的HashMap中。所以从源码上看会执行 

WritableComparator comparator = comparators.get(c); 这样以及代码，意思就是从comparators这个HashMap中根据当前对象的class文件获取它的比较器对象。如果不出意外到这就可以获取Hadoop自身数据类型对象的比较对象了。但是考虑的一些异常情况，比如内存溢出导致GC垃圾回收这时候可能会获取不到比较器对象，那么接下来会执行forceInit(c);这样一个方法，让类重新再加载一遍，确保万无一失。如果到此还是获取不到比较器对象，那么只有一种情况了，那就是当前的参与比较的对象不是Hadoop自身的数据类型，而是我们自定义的对象，在源码中也已看到 最后执行了一句

comparator = new WritableComparator(c, conf, true); 代码，意思是Hadoop会给我创建一个比较器对象。以上就是Hadoop中获取比较器对象的全过程了！

### 十二、编写MR的时候什么情况下使用Combiner ,具体实现流程是什么？

​				Combiner流程再MR中是一个可选流程，通常也是一种优化手段，当我们执行完Map阶段的计算后数据量比较大，kv组合过多。这样在Reduce阶段执行的时候会造成拷贝大量的数据以及汇总更多的数据。为了减轻Reduce的压力，此时可以选择在Map阶段进行Combiner操作，将一些汇总工作提前进行，这样会减少kv的数量从而在数据传输的过程中对IO的消耗也大大降低。

​				实现Combiner的大概流程为：首先需要自定义一个Combiner类，接着继承Hadoop的Reducer类，重写reduce()方法，在该方法中进行合并汇总。最后把自定义的Combiner类设置到Job中即可！

### 十三、OutputFormat自定义实现流程描述一下

​			OutputFormat类是MR中最后一个流程了，它主要负责数据最终结果的写出，一般我们不需要自定义，默认即可。但是如果我们对象最终输出结果文件的名称或者输出路径有个性化的要求，就可以通过自定OutputFormat来实现。实现流程大概如下：

​		首先自定义个OutputFormat类，然后继承OutputFormat，重写OutputFormat的getRecordWriter()方法，在该方法中返回 RecordWriter 对象。由于RecordWriter 也是Hadoop内部对象，如果我们想实现自己的逻辑，我们还得自定义个RecordWriter类，然后继承RecordWriter类，重写该类中的write() 方法和close()方法,在write() 方法中实现数据写出的逻辑，在close()方法对资源进行关闭。

### 十四、MR实现 ReduceJoin 的思路，以及ReduceJoin方案有哪些不足？

​			说到MR中的ReduceJoin，首先在Map阶段我们对需要jion的两个文件的数据进行统一搜集，用一个对象去管理，另外还要再改对象中新增一个属性用于记录每一条数据的来源情况。当在Map阶段将数据搜集好后，直接写出，写出的时候一定要注意输出的key的选择，这个key一定两个文件的关联字段。接下来Reduce阶段开始执行，先把Map端处理完的数据拷贝过来，然后一组相同key的values就会进入到reduce() 方法,由于之前已经定义好key是两文件的关联数据，那本次进入reduce() 的做join就可以，第一步根据不同的数据来源将两文件的数据分别利用容器或者对象维护起来，然后遍历其中一个容器根据具体业务逻辑将想要关联的数据获取到即可 然后输出结果。

​		以上就是ReduceJoin的大概思路，但是ReduceJoin相对来说比较耗费性能，而且如果出现数据倾斜场景，更不太好处理。

### 十五、MR实现 MapJoin 的思路，以及MapJoin的局限性是什么？

​			MapJoin顾名思义就是在Map端直接进行Join，不会走Reduce阶段，这样就很大程度的提升了MR的属性效率，同时也解决的数据倾斜给Reduce阶段带来的问题。接下来就聊聊MapJoin的核心思路，首先MapJoin的前提就是我们面对需要join的两个文件符合一个是大文件一个是小文件的条件。再次前提下，我们可以将小的文件提前缓存的内存中，然后让Map端直接处理大文件，每处理一行数据就根据当前的关联的字段到内存中获取想要的数据，然后将结果写出。

​		
