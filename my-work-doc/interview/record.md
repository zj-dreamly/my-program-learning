

#### undertow与tomcat区别

#### MyBatis的二级缓存
#### 线程池参数配置

```yaml
spring:
  datasource:
    druid:
      initial-size: 5
      max-active: 20
      min-idle: 5
      max-wait: 60000
      
  hikari:
      # 自动提交从池中返回的连接
      auto-commit: true
      # 连接池中维护的最小空闲连接数
      minimum-idle: 10
      # 连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
      maximum-pool-size: 60
      # 空闲连接超时时间，默认值600000（10分钟），大于等于max-lifetime且max-lifetime>0，会被重置为0；不等于0且小于10秒，会被重置为10秒。
      # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放
      idle-timeout: 30000
      # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短
      max-lifetime: 1800000
      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
      connection-timeout: 30000
```

#### 单例（懒汉）模式

```java
public Class Sington{
    
    private volatile static Sington sington;
    
    private Sington(){
        
    }
    
    public static Sington getInstance(){
        
        if(null == sington){
            synchronized(Sington.class){
                if(null == sington){
                    sington = new Sington();
                }
            }
        }
        
        return sington;
    }
    
    public static void main(String[] args){
        Sington sington = Sington.getInstance();
        System.out.printf("对象："+sington);
    }
}
```

#### Spring AOP

- AOP 代理主要分为静态代理和动态代理两大类，静态代理以 AspectJ 为代表；而动态代理则以 Spring AOP 为代表
- 静态代理是指使用 AOP 框架提供的命令进行编译，从而在编译阶段就可生成 AOP 代理类，因此也称为编译时增强
- 动态代理则在运行时借助于 JDK 动态代理、CGLIB 等在内存中“临时”生成 AOP 动态代理类，因此也被称为运行时增强

#### 冒泡排序

```java
  public static void main(String[] args) {
        int[] arr = {10, 8, 5, 6, 11, 7};

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
```



