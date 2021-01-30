### nginx做转发时，带'_'的header内容丢失

今天在线上切换系统时，发现后端微服务报错，说xx header的数值为空，也就是没有传。查看browser信息，发现前端页面系统发出时是带了这个header (user_id)的。也是就header user_id达到后端微服务时没有了。

那么代码没有改动，怎么平白无故会丢失头信息？ 

于是想到两个环境的不同之处在于线上是通过nginx做的代理转发，会不会是nginx搞的鬼？于是搜索“nginx request header 丢失”，果不其然是这个问题，nginx对下划线的头信息做了限制，找到问题所在就等于完成了一大半，办法总比困难多。遂决定记录之。 


- 方法一：不用下划线 

既然nginx对下划线不支持，那没关系，不用下划线就是了。比如原来”app_version”改成”app-version”就可以了。（难怪一般header的name都是’-‘来拼接的，比如”User-Agent”） 



- 方法二：从根本解除nginx的限制 
  nginx默认request的header的那么中包含’_’时，会自动忽略掉。 
  解决方法是：在nginx里的nginx.conf配置文件中的http部分中添加如下配置： 
  underscores_in_headers on; （默认 underscores_in_headers 为off）