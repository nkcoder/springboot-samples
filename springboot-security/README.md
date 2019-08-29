## 1. 快速开始

添加`spring-boot-starter-security`依赖，就添加了基本的安全：

- 基本认证，在弹出的对话框中输入用户名和密码
- 只有一个用户user，密码会在日志中输出
- 所有的接口都被拦截
- 不需要特定的role或authority

## 2. User Store

如果需要支持多用户认证，需要配置user store，即存储用户信息的地方。Spring Security支持以下几种方式：

- In-Memory
- JDBC
- 自定义的`UserDetailService`
- LDAP

无论采用哪种方式，都只需要重写`WebSecurityConfigurerAdapter`类中的`configure(AuthenticationManagerBuilder auth)`方法。接下来依次介绍前三种方式，基于`LDAP`的方式可以自己尝试下。

### 2.1 In-Memory

如果用户不多，且几乎不会变动，可以考虑将用户信息保存在内存中。配置示例如下：

```java
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth.inMemoryAuthentication()
      .withUser("kobe")
      .password(passwordEncoder().encode("123456"))
      .authorities("USER")
      .and()
      .withUser("durant")
      .password(passwordEncoder().encode("123456"))
      .roles("ADMIN")
      .authorities("USER");
}	
```

`withUser()`表示开始配置一个用户。

这种方式一般用于测试，因为不能动态地增加、删除、修改用户。

### 2.2 JDBC

用户信息一般存在关系型数据库中，所以使用JDBC作为user store是比较合理的。配置时需要指定使用的`DataSource`，如：

```java
private DataSource dataSource;

public JdbcSecurityConfig(DataSource dataSource) {
  this.dataSource = dataSource;
}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth.jdbcAuthentication()
      .dataSource(dataSource);
}
```

首先需要配置数据源，可以在`application.yml`中配置：

```text
spring:
	datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: root
```

配置了数据源之后，怎么从数据库获取用户信息进行安全认证呢？Spring Security默认会使用以下SQL查询：

```sql
public static final String DEF_USERS_BY_USERNAME_QUERY =
        "select username,password,enabled " +
        "from users " +
        "where username = ?";
public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
        "select username,authority " +
        "from authorities " +
        "where username = ?";
public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
        "select g.id, g.group_name, ga.authority " +
        "from groups g, group_members gm, group_authorities ga " +
        "where gm.username = ? " +
        "and g.id = ga.group_id " +
        "and g.id = gm.group_id";
```

如果我们的数据表schema与该查询语句结构不匹配，我们可以自定义查询语句，如：

```java
private DataSource dataSource;

public JdbcSecurityConfig(DataSource dataSource) {
  this.dataSource = dataSource;
}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth.jdbcAuthentication()
      .dataSource(dataSource)
      .usersByUsernameQuery("select username, password, enabled from user where username = ?")
      .authoritiesByUsernameQuery(
          "select username, authority from user_authority where username = ?")
      .passwordEncoder(passwordEncoder());
}
```

### 2.3 使用UserDetailsService

用户信息仍然是存在数据库中，但是利用`Spring Data Repository`来存取用户信息，会更方便一些。

首先需要定义用户信息Entity，实现`UserDetails`接口，该接口中的方法会将获取到的用户信息集成到Spring Security中，比如：

```java
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails {

  @Id
  private final Long id;

  private final String username;

  private final String password;

  private final Byte enabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  ...
}
```

然后定义用户的`repository`：

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String name);

}
```

最后需要实现`UserDetailsService`接口，实现其中的`loadUserByUsername()`方法，表示通过用户名获取用户信息：

```java
@Service
public class UserServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User " + username + " not found");
    }

    return user;
  }
}
```

这些定义好之后，只需要在`configure(AuthenticationManagerBuilder auth)`中配置使用`UserDetailsService`作为user store即可：

```java
private final UserDetailsService userDetailsService;

public UserServiceSecurityConfig(UserDetailsService userDetailsService) {
  this.userDetailsService = userDetailsService;
}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
}
```

## 3. 配置HttpSecurity

不同的HTTP请求往往有不同的认证要求，比如有些请求不需要认证，有些请求需要用户拥有`ADMIN`角色等，`WebSecurityConfigurerAdapter`中另一个configure方法`configure(HttpSecurity http)`主要用于配置HTTP请求的认证。

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests()
      .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
      .antMatchers("/", "/h2-console/**").permitAll()

      .and()

      .csrf().ignoringAntMatchers("/h2-console/**");
}
```

认证请求的规则之间的顺序很重要，前面规则的优先级高于后面的规则。用于HTTP请求的配置方法主要有：

- anonymous(): 允许匿名用户访问
- authenticated()：允许认证过的用户访问
- denyAll()：无条件拒绝所有访问
- hasAnyAuthority()：如果用户有指定的authority之一
- hasAuthority()：有该authority的允许访问
- hasAnyRole()：如果用户有指定的role之一
- hasRole(): 有该role的可以访问
- hasIpAddress()：如果IP地址匹配
- not()：取反
- permitAll()：允许所有访问
- rememberMe()：允许已经认证过的用户通过`remeber-me`访问
- access(String)：参数为SPEL，如果evaluate为true则允许访问，以上所有方法内部调用的都是access()

所以以上配置通过`access()`方法来写就是：

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests()
      .antMatchers("/user/**").access("hasAnyAuthority('ADMIN', 'USER')")
      .antMatchers("/", "h2-console/**").access("permitAll")
      
      .and()

      .csrf().ignoringAntMatchers("/h2-console/**");
}
```

请求认证配置完成后，可以配置login和logout的url，表示登录/登出的跳转地址：

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests()
      .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
      .antMatchers("/", "/h2-console/**").permitAll()

      .and()
      .formLogin()
      .loginPage("/login")

      .and()
      .logout()
      .logoutUrl("/logout")
}
```

## 4. CSRF

CSRF(Cross-Site Request Forgery)，即跨站请求伪造，攻击者引诱已登录用户访问伪造网站，向被攻击服务器（用户已登录的服务器）发送跨站请求，执行某种操作。
具体为：

- 1. 受害者登录a.com，并保留登录凭证（cookie）
- 2. 攻击者引诱用户访问了b.com，并向a.com发送一个请求，浏览器默认会带上a.com的cookie
- 3. a.com收到请求后，对请求进行验证，并确认是受害者的凭证，误以为是受害者自己发送的请求，于是执行了该请求对应的操作
- 4. 攻击完成

使用CSRF token是预防CSRF攻击的有效方式，主要包括三个步骤：

- 1. 服务器生成一个token，返回给客户端，为了安全考虑，这个token应该放在服务端的session
- 2. 客户端在提交请求时带上token
- 3. 服务端对比token，验证请求是否合法

Spring Security默认启用了CSRF，如果有特殊原因，可以禁用：

```java
http.csrf().disable();
```

## 5. 获取用户信息

在Spring Security中，有以下几种方式可以获取当前的用户信息：

- 将`Principal`作为Controller的方法参数
- 将`Authentication`作为Controller的方法参数
- 通过`SecurityContextHolder`获取context，然后再获取`Authentication`
- 使用`@AuthenticationPrincipal`注入用户信息

对应的示例代码如下：

```java
@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/principal")
  public Principal getUserByPrincipal(Principal principal) {
    return principal;
  }

  @GetMapping("/authentication")
  public Principal getUserByAuthentication(Authentication authentication) {
    return authentication;
  }

  @GetMapping("/auth-principal")
  public User getUserByAuthPrincipal(@AuthenticationPrincipal User user) {
    return user;
  }

  @GetMapping("/security-context")
  public Principal getUserBySecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication;
  }

}
```

## 参考文章

- [13. Cross Site Request Forgery (CSRF)](https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/csrf.html)
- [前端安全系列（二）：如何防止CSRF攻击？](https://tech.meituan.com/2018/10/11/fe-security-csrf.html)







