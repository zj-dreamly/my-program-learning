## @Api

### 用于controller类上

| 注解 | 说明           |
| ---- | -------------- |
| @Api | 对请求类的说明 |

```java
@Api：放在请求的类上，与@Controller 并列，说明类的作用，如用户模块，订单类等。
	tags="说明该类的作用"
	value="该参数没什么意义，所以不需要配置"
```



| 属性名称       | 备注                                    |
| -------------- | --------------------------------------- |
| value          | url的路径值                             |
| tags           | 如果设置这个值、value的值会被覆盖       |
| description    | 对api资源的描述                         |
| basePath       | 基本路径                                |
| position       | 如果配置多个Api 想改变显示的顺序位置    |
| produces       | 如, “application/json, application/xml” |
| consumes       | 如, “application/json, application/xml” |
| protocols      | 协议类型，如: http, https, ws, wss.     |
| authorizations | 高级特性认证时配置                      |
| hidden         | 配置为true ，将在文档中隐藏             |

## @ApiOperation

```java
@ApiOperation："用在请求的方法上，说明方法的作用"
value="说明方法的作用"
notes="方法的备注说明"
```

### @ApiImplicitParams、@ApiImplicitParam

### 用于方法上面（说明参数的含义）

| 注解                                  | 说明                                                      |
| ------------------------------------- | --------------------------------------------------------- |
| @ApiOperation                         | 方法的说明                                                |
| @ApiImplicitParams、@ApiImplicitParam | 方法的参数的说明；@ApiImplicitParam用于指定单个参数的说明 |

```java
@ApiImplicitParams：用在请求的方法上，包含一组参数说明
	@ApiImplicitParam：对单个参数的说明	    
	    name：参数名
	    value：参数的汉字说明、解释
	    required：参数是否必须传
	    paramType：参数放在哪个地方
	        · header --> 请求参数的获取：@RequestHeader
	        · query --> 请求参数的获取：@RequestParam
	        · path（用于restful接口）--> 请求参数的获取：@PathVariable
	        · body（请求体）-->  @RequestBody User user
	        · form（普通表单提交）	   
	    dataType：参数类型，默认String，其它值dataType="Integer"	   
	    defaultValue：参数的默认值
	    allowMultiple：表示是数组格式的参数 
```

## @ApiResponses、@ApiResponse

### 用于方法上面（返回参数或对象的说明）

| 注解                        | 说明                                                    |
| --------------------------- | ------------------------------------------------------- |
| @ApiResponses、@ApiResponse | 方法返回值的说明 ；@ApiResponses 用于指定单个参数的说明 |

### 对象类

| 注解              | 说明                                         |
| ----------------- | -------------------------------------------- |
| @ApiModel         | 用在JavaBean类上，说明JavaBean的 用途        |
| @ApiModelProperty | 用在JavaBean类的属性上面，说明此属性的的含议 |

```java
@ApiModel：用于JavaBean的类上面，表示此 JavaBean 整体的信息
			（这种一般用在post创建的时候，使用 @RequestBody 这样的场景，
			请求参数无法使用 @ApiImplicitParam 注解进行描述的时候 ）	
```

