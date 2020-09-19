在我们使用mysql导入大文件sql时可能会报MySQL server has gone away错误，该问题是max_allowed_packet配置的默认值设置太小，只需要相应调大该项的值之后再次导入便能成功。该项的作用是限制mysql服务端接收到的包的大小，因此如果导入的文件过大则可能会超过该项设置的值从而导致导入不成功！下面我们来看一下如何查看以及设置该项的值。

查看 max_allowed_packet 的值

```sql
show global variables like 'max_allowed_packet';
+--------------------+---------+
| Variable_name      | Value   |
+--------------------+---------+
| max_allowed_packet | 4194304 |
+--------------------+---------+
```

可以看到默认情况下该项的大小只有4M，接下来将该值设置成150M(1024\*1024*150)

```sql
set global max_allowed_packet=157286400;
```

此时再查看大小

```sql
show global variables like 'max_allowed_packet';
```

通过调大该值，一般来说再次导入数据量大的sql应该就能成功了，如果任然报错，则继续再调大一些就行，请注意通过在命令行中进行设置只对当前有效，重启mysql服务之后则恢复默认值，但可以通过修改配置文件（可以在配置文件my.cnf中添加max_allowed_packet=150M即可）来达到永久有效的目的，可其实我们并不是经常有这种大量数据的导入操作，所以个人觉得通过命令行使得当前配置生效即可，没有必要修改配置文件。