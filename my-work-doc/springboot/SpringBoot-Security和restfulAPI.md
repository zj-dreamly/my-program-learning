## **Spring Security**

用来控制maven之间的依赖版本，这样添加依赖的时候，不需要加版本号，spring会自动的帮你选择

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.spring.platform</groupId>
            <artifactId>platform-bom</artifactId>
            <version>Cairo-SR3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## Restful api路径写法

### 域名的利用

若域名无法区分出是api还是页面功能的时候，api路径后面统一加/api用于区分是接口服务。

1. https://back.zhuma.com/api/login
2. https://api-back.zhuma.com/login

上面举例中back代表着后台管理的意思，所以想要进入后台管理页面路径应该为：https://back.zhuma.com 前台当然要留给https://www.zhuma.com，在域名使用中我们可以利用三级域名对我们整体系统大的功能或应用进行很好的划分，正是因此，我们看到举例中路径上并没有加上应用的content path。

**★ 备注**

建议通过域名去区分api，也就是举例中2的方式

在开发中对于多环境开发我们也可以通过域名来区分，例如：

https://fe-api-back.zhuma.com 为联调环境，

https://qa-api-back.zhuma.com为QA测试环境，

https://stg-api-back.zhuma.com 为仿真环境，

https://api-back.zhuma.com为生产环境等。

------

### 词性使用

定义自定义路径部分时，使用名词的复数形式定义一个资源，如若有动词词性在url中考虑以下划线区分。

**基本操作**

GET /users                    # 获取用户列表

GET /users/{userId}       # 查看某个具体的用户信息

POST /users                 # 新建一个用户

PUT /users/{userId}       # 全量更新某一个用户信息

PATCH /users/{userId}   # 选择性更新某一个用户信息

DELETE /users/{userId} # 删除某一个用户

**批量操作**

POST /users/_mget         # 批量获取多个用户

POST /users/_mcreate    # 批量创建多个用户

POST /users/_mupdate   # 批量更新多个用户

POST /users/_mdelete    # 批量删除多个用户

POST /users/_bulk          # 批量功能组装（后面会讲到）

**动词词性加入url**（原则上此种情况是不被推荐的）

GET /users/_search        # 搜索用户

POST /users/_init         # 初化所有用户

★**备注**

这里可能有人会纠结路径参数/users/{userId} 是使用userId还是id，毕竟当前资源只有一级，此处不必纠结，原因是：这仅仅是一个后端使用变量而已，不会影响前端的使用，所以我们统一使用userId这种形式定义变量

批量操作时，统一使用POST作为HTTP METHOD，原因是 批量操作参数的数据大小不可控，使用request param可能超过某些浏览器对参数的长度限制，实际上，URL不存在参数长度上限的问题，HTTP协议规范没有对URL长度进行限制，这个限制是特定的浏览器及服务器对它的限制。

这里注意一个小点，URL路径是对大小写敏感的，例如：/users 和 /Users 是两个接口哦，但是我们规定URL全部小写。

------

### URL区分功能

URL区分功能（管理、我的 功能）

上面我们提到的 关于/users 用户功能的举例，通常情况下，这其实是一个管理用户资源的功能的接口，用于表示对用户这个资源的增删改查等管理功能。

那 我的 功能是指的什么呢？

通常来说，是对于前端用户下的某某资源的说明，我们通常定义为my-开头。

**举例**

GET /my-orders 我的订单列表

GET /users/{userId}/orders 管理查看某一个用户下的订单列表

------

### 一些约定

1. 路径中多个单词时，使用中划线 `-` 来连接
2. 不允许在路径中出现大写字母（查询参数名称除外）
3. 接口后省略xxx.do（很多人愿意加上.do这种形式，注意我们的每一个url代表的是一个资源哦）

**举例**

GET /my-account/profile 获取我的账户的简要信息

GET /my-notifications     获取我的消息列表

★**备注**

上面的举例我们看到，my-account是单数而不是复数形式，这里说明下，在系统中如果明确该信息就是单数，那我们在url定义时也应该使用单数表示。

### Springboot常见注解参数

@**RestController**标明此Controller提供RestAPI

@**RequestMapping**及其变体，映射http请求url到Java方法

@**RequestParam**映射请求参数到Java方法的参数

@**Pageabledefault**指定分页参数的默认值

@**PathVariable**映射url片段到Java方法的参数

@**JsonView**控制json输出内容

@**RequestBody**映射请求体到Java方法的参数

