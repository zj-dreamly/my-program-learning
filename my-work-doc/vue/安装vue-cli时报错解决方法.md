### 安装vue-cli时-4058报错的解决方法

一、报错信息

安装vue-cli时-4058报错

二、解决办法

1、安装淘宝镜像

npm --registry https://registry.npm.taobao.org info underscore

2、cnpm install --global vue-cli



### 安装版本问题

1、全局安装过旧版本的 `vue-cli`(1.x 或 2.x)要先卸载它，否则跳过此步：

```shell
1 npm uninstall vue-cli -g //或者 yarn global remove vue-cli
```

2、Vue CLI 3需要 nodeJs ≥ 8.9 (官方推荐 8.11.0+，你可以使用 nvm 或 nvm-windows在同一台电脑中管理多个 Node 版本）。

（2）下载安装nodeJs，**中文官方**下载地址：[http://nodejs.cn/download/ ](http://nodejs.cn/download/)

3、安装@vue/cli（Vue CLI 3的包名称由 `vue-cli` 改成了 `@vue/cli`）

```shell
1 cnpm install -g @vue/cli //yarn global add @vue/cli
```

4、vue -V  检查vue版本号