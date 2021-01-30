### 依赖管理

```xml
    <properties>
        <java.version>1.8</java.version>
        <alibaba.cloud.version>0.9.0.RELEASE</alibaba.cloud.version>
        <spring.boot.version>2.1.4.RELEASE</spring.boot.version>
        <spring.cloud.version>Greenwich.RELEASE</spring.cloud.version>
        <spring.platform.version>Cairo-RELEASE</spring.platform.version>
    </properties>

<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring.boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${alibaba.cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!-- 依赖管理-->
            <dependency>
                <groupId>io.spring.platform</groupId>
                <artifactId>platform-bom</artifactId>
                <version>${spring.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

### maven变量引用

父项目定义`properties`，子项目引用变量

### web测试

使用`MockMvc`来集成测试

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
		</dependency>
```

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void whenUploadSuccess() throws Exception {
		String result = mockMvc.perform(fileUpload("/file")
				.file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes("UTF-8"))))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
```

### `PageableDefault`

分页注解，默认第一页，10条

### `JsonView`

1. 使用接口声明多个视图
2. 在值对象的get方法上指定视图
3. 在controller方法上指定视图

### Rest异步处理

1. 使用Callable

```java
	@RequestMapping("/order")
	public Callable<String> order() throws Exception {
		return () -> {
			logger.info("副线程开始");
			Thread.sleep(1000);
			logger.info("副线程返回");
			return "success";
		};
	}
```



2. 使用DeferredResult

### WireMock伪造Rest服务

下载地址：<http://wiremock.org/>

启动服务端，略

启动客户端

```xml
<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock</artifactId>
</dependency>
```

```java
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * @author zhailiang
 *
 */
public class MockServer {

	public static void main(String[] args) throws IOException {
		configureFor(8062);
		removeAllMappings();

		mock("/order/1", "01");
		mock("/order/2", "02");
	}

	private static void mock(String url, String file) throws IOException {
		ClassPathResource resource = new ClassPathResource("mock/response/" + file + ".txt");
		String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(), "\n");
		stubFor(get(urlPathEqualTo(url)).willReturn(aResponse().withBody(content).withStatus(200)));
	}

}
```

### userDetailService接口-处理用户信息获取逻辑

### userDetail接口-处理用户校验逻辑

```java
//账户是否过期,过期无法验证

boolean isAccountNonExpired();

//指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证

boolean isAccountNonLocked()；

//指示是否已过期的用户的凭据(密码),过期的凭据防止认证

boolean isCredentialsNonExpired();

//是否被禁用,禁用的用户不能身份验证

boolean isEnabled();
```

### PasswordEncoder-处理密码加解密

### 个性化用户认证流程

自定义登录页面

```java
http.formLogin().loginPage("/authentication/require")//指定登录页
    .loginProcessingUrl("/authentication/form")//告诉spring-security按照UsernamePasswordAuthenticationFilter提交认证
    .and()
    .csrf().disable();
```

```java
    /**
     * 当需要身份认证时，跳转到这里
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是:" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getSignInPage());
            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }
```

自定义登录成功处理：`AuthenticationSuccessHandler`

自定义登录失败处理：`AuthenticationFailureHandler`

### ServletRequestUtils

### InitializingBean

### HttpSessionSessionStrategy

### spring依赖搜索

### Session管理

#### session超时处理

```properties
#springboot默认超时时间最低是1分钟
server.session.timeout=10
```

#### session并发控制

继承`WebSecurityConfigurerAdapter`

#### 集群session管理

spring-session实现

### Spring Social

### spring security oauth2

### JWT

json web token

- 自包含
- 密签
- 可扩展

### sso单点登录

```java
@EnableAuthorizationServer
```

```java
@EnableOAuth2Sso
```

#### Spring Security控制授权

| 表达式                            | 描述                                                         |
| --------------------------------- | ------------------------------------------------------------ |
| `hasRole([role]`)                 | 用户拥有制定的角色时返回true （`Spring security`默认会带有`ROLE_`前缀）,去除参考[Remove the ROLE_](https://github.com/spring-projects/spring-security/issues/4134) |
| `hasAnyRole([role1,role2])`       | 用户拥有任意一个制定的角色时返回true                         |
| `hasAuthority([authority])`       | 等同于`hasRole`,但不会带有`ROLE_`前缀                        |
| `hasAnyAuthority([auth1,auth2])`  | 等同于`hasAnyRole`                                           |
| `permitAll`                       | 永远返回true                                                 |
| `denyAll`                         | 永远返回false                                                |
| `anonymous`                       | 当前用户是`anonymous`时返回true                              |
| `rememberMe`                      | 当前勇士是`rememberMe`用户返回true                           |
| `authentication`                  | 当前登录用户的`authentication`对象                           |
| `fullAuthenticated`               | 当前用户既不是`anonymous`也不是`rememberMe`用户时返回true    |
| `hasIpAddress('192.168.1.0/24'))` | 请求发送的IP匹配时返回true                                   |

部分代码：

```java
......
private String defaultRolePrefix = "ROLE_"; //ROLE_前缀

	/** Allows "permitAll" expression */
	public final boolean permitAll = true; //全部true

	/** Allows "denyAll" expression */
	public final boolean denyAll = false; //全部false
public final boolean permitAll() {
		return true;
	}

	public final boolean denyAll() {
		return false;
	}

	public final boolean isAnonymous() {
		//是否是anonymous
		return trustResolver.isAnonymous(authentication);
	}

	public final boolean isRememberMe() {
		//是否是rememberme
		return trustResolver.isRememberMe(authentication);
	}
......
```

#### URL安全表达式

```java
onfig.antMatchers("/person/*").access("hasRole('ADMIN') or hasRole('USER')")
                .anyRequest().authenticated();
```

这里我们定义了应用`/person/*`URL的范围，该URL只针对拥有`ADMIN`或者`USER`权限的用户有效。

#### 在Web安全表达式中引用bean

```java
config.antMatchers("/person/*").access("hasRole('ADMIN') or hasRole('USER')")
                .antMatchers("/person/{id}").access("@rbacService.checkUserId(authentication,#id)")
                .anyRequest()
                .access("@rbacService.hasPermission(request,authentication)");
```

RbacServiceImpl

```java
@Component("rbacService")
@Slf4j
public class RbacServiceImpl implements RbacService {
    /**
     * uri匹配工具
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        log.info("【RbacServiceImpl】  --hasPermission={}", authentication.getPrincipal());
        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;
        //有可能是匿名的anonymous
        if (principal instanceof SysUser) {
            //admin永远放回true
            if (StringUtils.equals("admin", ((SysUser) principal).getUsername())) {
                hasPermission = true;
            } else {
                //读取用户所拥有权限所有的URL 在这里全部返回true
                Set<String> urls = new HashSet<>();

                for (String url : urls) {
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }
        return hasPermission;
    }

	  public boolean checkUserId(Authentication authentication, int id) {
        return true;
    }
}
```



### Method安全表达式

针对方法级别的访问控制比较复杂，`Spring Security`提供了四种注解，分别是`@PreAuthorize` , `@PreFilter` , `@PostAuthorize` 和 `@PostFilter`

#### 使用method注解

1. 开启方法级别注解的配置

   ```java
   @Configuration
   @EnableGlobalMethodSecurity(prePostEnabled = true)
   public class MerryyouSecurityConfig extends WebSecurityConfigurerAdapter {
   ```

2. 配置相应的bean

   ```java
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
   
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
   
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   ```

3. 在方法上面使用注解

   ```java
   /**
   * 查询所有人员 
   */ 
   @PreAuthorize(“hasRole(‘ADMIN’)”) 
   @ApiOperation(value = “获得person列表”, notes = “”) 
   @GetMapping(value = “/persons”) 
   public List getPersons() { 
       return personService.findAll(); 
   } 
   ```


PreAuthorize

@PreAuthorize 注解适合进入方法前的权限验证

```java
@PreAuthorize("hasRole('ADMIN')")
    List<Person> findAll();
```

PostAuthorize

@PostAuthorize 在方法执行后再进行权限验证,适合验证带有返回值的权限。`Spring EL` 提供 返回对象能够在表达式语言中获取返回的对象`return Object`。

```java
@PostAuthorize("returnObject.name == authentication.name")
    Person findOne(Integer id);
```

PreFilter 针对参数进行过滤

```java
//当有多个对象是使用filterTarget进行标注
@PreFilter(filterTarget="ids", value="filterObject%2==0")
public void delete(List<Integer> ids, List<String> usernames) {
   ...

}
```

PostFilter 针对返回结果进行过滤

```java
 @PreAuthorize("hasRole('ADMIN')")
 @PostFilter("filterObject.name == authentication.name")
 List<Person> findAll();
```

### AntPathMatcher

