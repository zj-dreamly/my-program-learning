很早之前就注册了sonatype的账户，一直没有发布自己的jar包上去，昨天终于准备发布，以为很简单，结果踩坑一天多。记录下自己的心路历程。

发布过程具体分为以下几个步骤：

1. 注册sonatype并提交issue
2. 配置公钥私钥
3. 上传构件
4. close和release
5. 发布成功

## 一、注册sonatype

网上参考很多，这里给出一个：<https://blog.csdn.net/xiajiqiu/article/details/77610765>

## 二、配置公钥私钥

我其实主要就是卡在这个步骤。

1. 安装Gpg4win<https://www.gpg4win.org/>
2. 使用命令行的操作，我创建的公钥在网站上一直检测不到，直到最后，我使用GPA的图形化界面创建，才被检测到

### 三、上传构件

这里没啥可说的，网上参考足够

<https://my.oschina.net/looly/blog/270767>

有一点坑就是，当时我使用的IDEA去执行`mvn clean deploy -P release`的时候，一直提示运行的是JRE环境，但其实我所有配置应该是没有问题，我最后解决的办法是在maven窗口执行的maven命令，没有使用Terminal方式，这个问题还有待解决

### 四、 close和release

在部署完毕之后，就可以到<https://oss.sonatype.org/#stagingRepositories>查看，默认是open状态，需要手动关闭，我的公钥就是在这里一直检查不通过，导致无法close，通过之后就release就好

### 五、发布成功

稍等一会会有邮件告诉你，发布成功，这里我并没有向sonatype提交，他们自动发布了。