#### SpringBoot项目使用docker快速部署

##### 前置准备：

- 在云服务上安装docker
- 在idea安装docker插件：docker integration

##### 开始安装

登录云服务器，在这里我用的是腾讯云

1. 修改docker配置文件以支持远程主机访问

```shell
vim /lib/systemd/system/docker.service
```

2. 修改以ExecStart为开头的行

```shell
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2376 -H unix://var/run/docker.sock
```

3. 保存并重启docker

```shell
systemctl daemon-reload
service docker restart
```

配置idea

1. 在setting中找到Build，Execution，Deployment，选择docker，新增一个连接，配置tcp socket：

tcp://111.231.98.177:2376，下面的Certificates folder不要配置，默认空，然后保存。

2. 编写docker file

```properties
FROM java:8

VOLUME /tmp
#指定路径，这里我是把jar包放在了docker file的同级目录下
ADD yshior-0.0.1-SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
```

修改Edit configurations，新增docker部署，参照下面的配置，其中第一个端口为docker暴露的外部端口。

![1547963212589](1547963212589.png)

##### 最后启动