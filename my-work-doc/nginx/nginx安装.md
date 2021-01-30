### 编译安装

#### 安装make：

```shell
yum -y install gcc automake autoconf libtool make
```

#### 安装g++:

```shell
yum install gcc gcc-c++
```

**安装PCRE库**

```shell
cd /usr/local/src
wget http://downloads.sourceforge.net/project/pcre/pcre/8.35/pcre-8.35.tar.gz 
tar -zxvf pcre-8.35.tar.gz
cd pcre-8.35
./configure
make
make install
```

#### **安装zlib库**

```shell
cd /usr/local/src
wget http://zlib.net/zlib-1.2.11.tar.gz
tar -zxvf zlib-1.2.11.tar.gz
cd zlib-1.2.11
./configure
make
make install
```

#### **安装openssl（某些vps默认没装ssl)**

```shell
cd /usr/local/src
wget https://www.openssl.org/source/openssl-1.0.1t.tar.gz
tar -zxvf openssl-1.0.1t.tar.gz
```

#### **安装nginx**

```shell
cd /usr/local/nginx
wget http://nginx.org/download/nginx-1.1.10.tar.gz
tar -zxvf nginx-1.1.10.tar.gz
cd nginx-1.1.10
./configure
make
make install
```

#### 有可能报错（执行）

```shell
yum -y install openssl openssl-devel
```

#### 启动nginx

```shell
#测试nginx.conf的配置是否正确 　
/usr/local/nginx/sbin/nginx -t 
#根据nginx.conf里的配置，启动nginx服务
/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf

./nginx -s quit         优雅停止nginx，有连接时会等连接请求完成再杀死worker进程  

./nginx -s reload       优雅重启，并重新载入配置文件nginx.conf

./nginx -s reopen       重新打开日志文件，一般用于切割日志

./nginx -v              查看版本  

./nginx -t              检查nginx的配置文件

./nginx -h              查看帮助信息

./nginx -V              详细版本信息，包括编译参数 

./nginx  -c filename    指定配置文件
```

***************************************************************************************

####  升级到支持https

```shell
cd /usr/local/nginx/nginx-1.1.10
./configure --with-http_ssl_module
make
```

因为这次是升级nginx，所以不需要执行 make install，首先备份原nginx执行脚本：

```shell
mv /usr/local/nginx/sbin/nginx /usr/local/nginx/sbin/nginx.old
```

把新编译的nginx执行脚本拷贝到相应的目录下：

```shell
cd objs/
cp nginx /usr/local/nginx/sbin/
```

最后进行平滑升级

```shell
cd ..
make upgrade
```

编辑配置文件

```shell
cd /usr/local/nginx/conf
vim nginx.conf
```

```nginx
   #以下属性中以ssl开头的属性代表与证书配置有关，其他属性请根据自己的需要进行配置。
server {
         #配置HTTPS的默认访问端口号为443。此处如果未配置HTTPS的默认访问端口，可能会造成Nginx无法启动。  Nginx 1.15.0以上版本请使用listen 443 ssl代替listen 443和ssl on。
         listen 443; 
    #将www.certificatestests.com修改为您证书绑定的域名，例如：www.example.com。如果您购买的是通配符域名证书，要修改为通配符域名，例如：*.aliyun.com。
         server_name www.certificatestests.com; 
         root html;
         index index.html index.htm;
         ssl_certificate cert/domain name.pem;  #将domain name.pem替换成您证书的文件名称。
         ssl_certificate_key cert/domain name.key; #将domain name.key替换成您证书的密钥文件名称。
         ssl_session_timeout 5m;
         ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4; #使用此加密套件。
         ssl_protocols TLSv1 TLSv1.1 TLSv1.2; #使用该协议进行配置。
         ssl_prefer_server_ciphers on;
         location / {
         root html;  #站点目录。
         index index.html index.htm;
                    }
      }
```



最后重新reload下nginx，搞定！

#### 配置http请求强制转发https请求

```shell
     server {
        listen 80;
        server_name www.xyilai.cn;
        rewrite ^ https://$http_host$request_uri? permanent; 
     }
```

或者

```nginx
server {
 listen 443;
 server_name www.certificatestests.com; #将www.certificatestests.com修改为您证书绑定的域名，例如：www.example.com。
rewrite ^(.*)$ https://$host$1 permanent;   #将所有HTTP请求通过rewrite重定向到HTTPS。
 location / {
index index.html index.htm;
}
}
```

单个配置

```shell
  # tale
  upstream proxy_tale{
    server localhost:9000;
  }
  # ormco
  server{
    listen 80;
    listen 443;
    server_name www.xyilai.cn;
    charset utf8;
    location / {
      proxy_pass http://proxy_tale;
      proxy_redirect default;
      proxy_set_header Host $host;
      proxy_set_header X-Real-IP $remote_addr;
      proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
      index index.jsp index.html index.htm default.jsp default.html default.htm;
    }
  }
```

集群配置

```shell
  # tale
  upstream proxy_tale{
    server localhost:1111 weight=1;
    server localhost:1112 weight=1;
  }
  # tale
  server{
    listen 80;
    listen 443;
    server_name www.xyilai.cn;
    charset utf8;
    location / {
      proxy_pass http://proxy_tale;
      proxy_redirect default;
      proxy_set_header Host $host;
      proxy_set_header X-Real-IP $remote_addr;
      proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
      index index.jsp index.html index.htm default.jsp default.html default.htm;
    }
  }
```

#### 支持history模式

```shell
  
  server {
      listen       80;
      server_name  	test.synconize.com;

      #charset koi8-r;

      #access_log  logs/host.access.log  main;

      location / {
          root   /synco/html/test;
          index  index.html index.htm;

         # 配置支持history模式
         if (!-e $request_filename) {
          rewrite ^/(.*) /index.html last;
          break;
         }
      }
  }
```

### yum安装

通过`yum search nginx`看看是否已经添加源成功。如果成功则执行下列命令安装nginx。

```shell
yum install nginx
```

安装完后，`rpm -qa | grep nginx` 查看

启动nginx

```shell
systemctl start nginx
```

加入开机启动

```shell
systemctl enable nginx
```

查看nginx的状态

```shell
systemctl status nginx
```

### Ubuntu安装之后的文件结构

- 所有的配置文件都在/etc/nginx下，并且每个虚拟主机已经安排在了/etc/nginx/sites-available下
- 程序文件在/usr/sbin/nginx
- 日志放在了/var/log/nginx中
- 并已经在/etc/init.d/下创建了启动脚本nginx
- 默认的虚拟主机的目录设置在了/var/www/nginx-default (有的版本 默认的虚拟主机的目录设置在了/var/www, 请参考/etc/nginx/sites-available里的配置)