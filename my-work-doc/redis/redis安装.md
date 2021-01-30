### 安装redis数据库

```shell
yum install redis
```

### 下载fedora的epel仓库
```bash
yum install epel-release
```

### 启动redis服务

```shell
systemctl start redis
```

### 查看redis状态

```shell
systemctl status redis
```

### 停止服务

```shell
systemctl stop redis
```

### 重启服务

```shell
systemctl restart redis
```

### 查看redis进程

```shell
ps -ef |grep redis
```

### 设置开机自启动

```shell
systemctl enable redis
```

### 开放端口号 

```bash
firewall-cmd --zone=public --add-port=80/tcp --permanent

firewall-cmd --zone=public --add-port=6379/tcp --permanent
```

注意：80端口是必须要开放的

重启防火墙：

```shell
systemctl restart firewalld
```

查看端口 （若此命令无效可百度自行安装）

```shell
netstat -lnp|grep 6379
```

### 设置配置文件

vi /etc/redis.conf


```mysql
# 解除只有本次访问权限
#bind 127.0.0.1
# 保护模式修改为no
protected-mode no
# 设置redis密码
requirepass password
```

