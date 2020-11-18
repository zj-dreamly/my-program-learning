##### 模糊查询统计个数

```mysql
select count(1) from product_message where productNum like "cang%"
```

##### 主键自增归0，数据清空（截断表）

```mysql
truncate table tbl_name
```

##### 返回数据库最后一条数据（根据自增id判断）

```mysql
select * from tbl_name order by id DESC limit 1
```

##### 查询数据总条数

```mysql
SELECT COUNT(*) FROM tbl_name
```

##### 查询重复结果并进行分类

```mysql
select consumerType,count(*) as count from tbl_data group by consumerType having count>1 
```

```mysql
select activityName,province,count(*) as count from tbl_data group by activityName,province having count(1)>1 
```

##### 按照条件随机查询数据库的某条符合结果的数据

```mysql
SELECT column_name
FROM tbl_name AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM tbl_name)-(SELECT MIN(id) FROM tbl_name))+(SELECT MIN(id) FROM tbl_name)) AS id) AS t2 
WHERE t1.id >= t2.id and state=0
ORDER BY t1.id LIMIT 1;
```

##### MySql查询指定日期的数据

**今天**

```mysql
select * from 表名 where to_days(时间字段名) = to_days(now());
```

**昨天**

```mysql
SELECT * FROM 表名 WHERE TO_DAYS( NOW( ) ) - TO_DAYS( 时间字段名) <= 1
```

**近7天**

```mysql
SELECT * FROM 表名 where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(时间字段名)
```

**近30天**

```mysql
SELECT * FROM 表名 where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(时间字段名)
```

**本月**

```mysql
SELECT * FROM 表名 WHERE DATE_FORMAT( 时间字段名, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )
```

**上一月**

```mysql
SELECT * FROM 表名 WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( 时间字段名, '%Y%m' ) ) =1
```

**查询本季度数据**

```mysql
select * from `ht_invoice_information` where QUARTER(create_date)=QUARTER(now());
```

**查询上季度数据**

```mysql
select * from `ht_invoice_information` where QUARTER(create_date)=QUARTER(DATE_SUB(now(),interval 1 QUARTER));
```

**查询本年数据**

```mysql
select * from `ht_invoice_information` where YEAR(create_date)=YEAR(NOW());
```

**查询上年数据**

```mysql
select * from `ht_invoice_information` where year(create_date)=year(date_sub(now(),interval 1 year));
```

**查询当前这周的数据**

```mysql
SELECT name,submittime FROM enterprise WHERE YEARWEEK(date_format(submittime,'%Y-%m-%d')) = YEARWEEK(now());
```

**查询上周的数据**

```mysql
SELECT name,submittime FROM enterprise WHERE YEARWEEK(date_format(submittime,'%Y-%m-%d')) = YEARWEEK(now())-1;
```

**查询上个月的数据**

```mysql
select name,submittime from enterprise where date_format(submittime,'%Y-%m')=date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m')

select * from user where DATE_FORMAT(pudate,'%Y%m') = DATE_FORMAT(CURDATE(),'%Y%m') ; 

select * from user where WEEKOFYEAR(FROM_UNIXTIME(pudate,'%y-%m-%d')) = WEEKOFYEAR(now()) 

select * from user where MONTH(FROM_UNIXTIME(pudate,'%y-%m-%d')) = MONTH(now()) 

select * from user where YEAR(FROM_UNIXTIME(pudate,'%y-%m-%d')) = YEAR(now()) and MONTH(FROM_UNIXTIME(pudate,'%y-%m-%d')) = MONTH(now()) 

select * from user where pudate between  上月最后一天  and 下月第一天 
```

**查询当前月份的数据** 

```mysql
select name,submittime from enterprise   where date_format(submittime,'%Y-%m')=date_format(now(),'%Y-%m')
```

**查询距离当前现在6个月的数据**

```mysql
select name,submittime from enterprise where submittime between date_sub(now(),interval 6 month) and now();
```

 **查询某个月的数据(查询17年10月份数据)**

```mysql
select * from exam where date_format(starttime,'%Y-%m')='2017-10'
```

```mysql
-- 查询某天的积分（月，年就以此类推）
select * from task_time where date_format(task_datetime,'%Y-%m-%d')='2018-08-20';
```

##### 查询某一年的12个月份的数据记录

```mysql
select   
sum(case month(a.task_datetime) when '1' then a.task_integral else 0 end) as january,  
sum(case month(a.task_datetime) when '2' then a.task_integral else 0 end) as february,  
sum(case month(a.task_datetime) when '3' then a.task_integral else 0 end) as march,  
sum(case month(a.task_datetime) when '4' then a.task_integral else 0 end) as april,  
sum(case month(a.task_datetime) when '5' then a.task_integral else 0 end) as may,  
sum(case month(a.task_datetime) when '6' then a.task_integral else 0 end) as june,  
sum(case month(a.task_datetime) when '7' then a.task_integral else 0 end) as july,  
sum(case month(a.task_datetime) when '8' then a.task_integral else 0 end) as august,  
sum(case month(a.task_datetime) wh	en '9' then a.task_integral else 0 end) as september,  
sum(case month(a.task_datetime) when '10' then a.task_integral else 0 end) as october,  
sum(case month(a.task_datetime) when '11' then a.task_integral else 0 end) as november,  
sum(case month(a.task_datetime) when '12' then a.task_integral else 0 end) as december
from task_time a  
where year(a.task_datetime)='2018';
-- 参数解释：
task_time：表的名字
task.datetime：表中的时间字段
task_integral：表中的需要统计的数据
```

##### zerofill自动补零

```sql
create table t (t int(3) zerofill);
```

##### MySql设置自增初始值

```mysql
alter table table_name auto_increment=10011;
```

注意n只能大于已有的auto_increment的整数值,小于的值无效.

##### mysql导出sql脚本

```bash
mysqldump -uroot -p edusoho>edusoho-back.sql    #-p后面是mysql的root密码
```

##### mysql导入脚本

```bash
mysql -uroot -p   #接下来输入root密码，登录mysql

use edusohodb;  #选择edusohodb数据库

source /var/www/edusoho-back.sql    #导入数据库文件， /var/www/edusoho-back.sql  是解压数据库备份文件后的路径和文件名
```

##### 查看该数据库实例下所有库大小，得到的结果是以MB为单位

```mysql
select table_schema,sum(data_length)/1024/1024 as data_length,sum(index_length)/1024/1024 
as index_length,sum(data_length+index_length)/1024/1024 as sum from information_schema.tables;  
```

 

##### 查看该实例下各个库大小

```mysql
select table_schema, sum(data_length+index_length)/1024/1024 as total_mb,   
sum(data_length)/1024/1024 as data_mb, sum(index_length)/1024/1024 as index_mb,   
count(*) as tables, curdate() as today from information_schema.tables group by table_schema order by 2 desc;  
```

 

##### 查看单个库的大小

```mysql
select concat(truncate(sum(data_length)/1024/1024,2),'mb') as data_size,  
concat(truncate(sum(max_data_length)/1024/1024,2),'mb') as max_data_size,   
concat(truncate(sum(data_free)/1024/1024,2),'mb') as data_free,  
concat(truncate(sum(index_length)/1024/1024,2),'mb') as index_size 
from information_schema.tables where table_schema = 'schema_name';   
```

 

##### 查看单个表的状态

```
show table status from data_1234567890 where name = 'data_1234567890_ss' \G  
```

 

##### 查看单库下所有表的状态

```mysql
mysql> select table_name, (data_length/1024/1024) as data_mb , (index_length/1024/1024)   
as index_mb, ((data_length+index_length)/1024/1024) as all_mb, table_rows   
from information_schema.tables where table_schema = 'schema_name';  
```