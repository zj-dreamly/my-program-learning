##### 查看隐藏文件

```properties
ll -a 
```

##### 拷贝文件夹

```properties
cp -R xxx /usr/local/xxx 
```

##### 重启Mysql

```properties
service mysqld restart
```

##### 重启tomcat

```properties
./startup.sh
./shutdown.sh
```

**直接关闭防火墙**

```properties
#停止firewall
systemctl stop firewalld.service
#禁止firewall开机启动
systemctl disable firewalld.service 
```

**设置 iptables service**

```shell
yum -y install iptables-services
```

**如果要修改防火墙配置，如增加防火墙端口3306**

```shell
vi /etc/sysconfig/iptables 
```

**增加规则**

```properties
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
#重启防火墙使配置生效
systemctl restart iptables.service
#设置防火墙开机启动
systemctl enable iptables.service 
```

##### linux关闭防火墙

```properties
service iptables stop
```

##### linux永久关闭防火墙

```properties
chkconfig iptables off
```

##### 查看tomcat的启动

```properties
tail -f /logs/catalina.out
```

##### 查看tomcat是否已经停止

```properties
ps -ef | grep tomcat
```

##### 在linux中创建多个目录

```properties
mkdir /var/temp/nginx -p
#其中，加上了-p就是依次创建的意思
```

##### 启动nginx

```properties
#bin目录中
./nginx
```

##### 停止nginx

```properties
./nginx -s quit
```

##### nginx热部署

```properties
./nginx -s reload
```

##### 指定makeinstall的安装目录

```properties
make install PREFIX=/usr/local/xxx
```

##### 启动redis

```properties
./redis-server
```

##### 连接redis

```properties
./redis-cli -h(ip地址) -p（端口号，redis默认的端口号是6379）
```

##### 设置redis静默启动

```properties
#修改redis.conf中的daemonize no为yes
./redis-server redis.conf
```

##### 关闭redis

```properties
./redis-cli shutdown
```

##### 给批处理开启可执行的权限

```properties
chmod u+x xxx.sh
```

##### 查看80端口被谁占用：

```properties
fuser -n tcp 1111
```

##### 杀死进程

```properties
kill -9 1279
```

##### 静默启动SpringBoot项目

```properties
nohup java -jar server.jar &
```

##### 将war包解压到当前文件夹

```properties
jar -xvf hello.war
```

##### 拷贝文件夹（-r代表递归）

```properties
cp -r folder newfolder
```

##### 删除文件或目录

```properties
#删除文件
rm file
#删除文件夹（r代表递归，f代表强制删除。）
rm -rf folder
```

##### 移动目录或者文件

```properties
#强制覆盖已经存在的目录或者文件
mv -f folder newfolder
```

##### 创建文件

```properties
touch hello.txt
```

##### 向文件中写入内容

```properties
echo Thanks > hello.txt
```

##### 查看文件内容

```properties
cat hello.txt
```

##### 查看文件属性信息

```properties
ls -l
ls -l file
```

##### 修改文件可读写属性的方法

```properties
#把index.html 文件修改为可写可读可执行
chmod 777 index.html
#修改目录下所有文件属性可写可读可执行:
chmod 777 *.*
#修改所有htm文件的属性:
chmod 777 *.htm
#把目录 /images/xiao 修改为可写可读可执行
chmod 777 /images/xiao
#修改目录下所有的文件夹属性
chmod 777 *
#要修改文件夹内所有的文件和文件夹及子文件夹属性为可写可读可执行
chmod -R 777 /upload
```

##### 解除文件保护

```shel
chattr -i a.txt
```

##### centos创建文件夹快捷方式

第一个为目标文件夹，第二个为快捷方式存储的文件夹

```shell
ln -s /var/lib/docker/volumes/b697ab9597c813c614a933543e40a84c3677d13de9b70e966d97783a01a5cb74/_data /root
```

**在指定的文件夹下查询指定内容的文件**

```shell
grep -r "fonts.lug.ustc.edu.cn" kavo.live/
```

**查看当前文件夹所占空间大小**

```shell
du -sh *
```

#### 查看当前活动的tcp链接

```shell
netstat -tnlp
```



