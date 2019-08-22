## Spring 环境配置

Spring 的环境变量可以是以下来源：

- JVM 系统属性
- 操作系统环境变量
- 命令行参数
- 属性配置文件

比如，自定义配置属性`app.name=spring config demo`，在不同的来源里配置如下：

- 在属性配置文件`application.yml`中配置为：`app.name: spring config demo`
- 使用 JVM 系统属性：`java -Dapp.name="spring config demo" -jar xxx.jar`
- 使用操作系统环境变量：`export APP_NAME="spring config demo" java -jar xxx.jar`
- 使用命令行参数：`java -jar xxx.jar --app.name="spring config demo"`

系统环境变量的命名与其它方式略有不同，不过 Spring 会自动处理，将`APP_NAME`映射为`app.name`。

## 配置 DataSource

我们可以手动配置`DataSource` bean，但是更简单的方式是通过配置文件配置 URL 以及账户密码等信息，如：

    spring:
      datasource:
        url: jdbc:mysql://localhost/test
        username: test
        password: my-password
        driver-class-name: com.mysql.jdbc.Driver

其中`driver-class-name`通常不是必需的，Spring Boot 可以通过 url 自动识别并加载，如果出现问题才需要去手动设置。

至于数据库连接池，根据是否在 classpath 上，会依次使用 Tomcat 的 JDBC 连接池，`HikariCP`或`Commons DBCP 2`。

默认，Spring Boot 会执行 classpath 下的`schema.sql`以及`data.sql`，这些数据库初始化的脚本也是可以配置的：

    spring:
      datasource:
        schema:
          - classpath: sql/org-schema.sql
          - classpath: sql/user-schema.sql
        data:
          - classpath: sql/fill-data.sql

## 配置 web server (https)

内嵌 server 默认端口是 8080，可以配置，如果配置为 0 表示随机的可用端口：

    server.port: 0

如果应用需要处理 HTTPS 请求，则需要生成证书，并配置 SSL。

证书主要有两种格式：

- PKCS12: [Public Key Cryptographic Standards](https://en.wikipedia.org/wiki/PKCS_12)是一种密码保护格式，是行业通用标准。
- JKS：[Java KeyStore](https://en.wikipedia.org/wiki/Java_KeyStore)与 PKCS12 类似，但是仅在 Java 环境下可用。

生成 JKS 格式的证书：

    $ keytool -keystore mykeys.jks -genkey -alias tomcat -keyalg RSA

需要记住输入的密码，将生成的 mykeys.jks 文件放在`src/main/resources/keystore`下，然后在`application.yml`配置文件中配置：

    server:
      port: 8443
      ssl:
        key-store: classpath:keystore/mykeys.jks
        key-store-password: admin123
        key-password: admin123

生成 PKCS12 格式的证书：

    $ keytool -genkeypair -alias mykeys -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore mykeys.p12 -validity 3650

将生成的 mykeys.p12 文件放在`src/main/resources/keystore`下，然后在`application.yml`中添加配置：

    server:
      port: 8443
      ssl:
        key-store: classpath:keystore/mykeys.p12
        key-store-password: admin123
        key-password: admin123
        key-store-type: PKCS12
        key-alias: mykeys

## 配置日志

Spring Boot 默认使用 Logback 配置日志，将 INFO 级别的日志输出到 Console。如果需要详细配置 logback，则可以在 classpath 下创建一个`logback.xml`文件。

如果只是配置日志的 Level 以及文件目录，Spring Boot 支持通过属性来配置。

配置日志级别时，使用`logging.level`前缀，然后加上 logger 的名字，配置日志文件相关属性时，使用`logging.file`前缀。

    logging:
      level:
        root: INFO
        org.springframework.security: DEBUG
      path: /var/log
      file:
        max-history: 5
        max-size: 10MB

> 注意：`logging.path`和`logging.file`不能同时设置。如果设置了`logging.path`，日志将会写入该目录下的 _spring.log_ 文件，如果设置了`logging.file`，则日志会写入该文件（可以是绝对路径和相对路径）。

属性配置可以通过`$`引用：

    logging:
      path: /var/log
      file: ${logging.path}/config.log

另外，Spring Boot 使用比较灵活的方式进行属性绑定，如以下形式都可以绑定到属性`app.userName`上：

- app.userName
- app.user-name
- app.user_name
- app.USER_NAME

## @ConfigurationProperties

可以使用`@ConfigurationProperties`将一组属性绑定到一个对象上，适合有共同前缀的层次结构的属性配置。在这之前需要添加`spring-boot-configuration-processor`依赖：

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")

然后定义一个属性配置类，就是一个普通的 Bean，需要 getter/setter 方法：

```java
@Data
@Component
@Configuration
@PropertySource(value = "classpath:mail-config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "mail")
public class MailConfig {

  private String host;
  private int port;
  private String from;
  private List<String> defaultRecipients;
  private Map<String, String> additionalHeaders;
  private Credentials credentials;
  private Map<String, Credentials> memberCredentials;

  @Data
  public static class Credentials {

    private String name;
    private String password;
  }

}
```

`ConfigurationProperties`表示这是一个属性配置，`prefix`指定前缀。
`@PropertySource`指定配置文件的路径，如果不指定，默认是可以加载`application.yml`中的配置的，但是如果指定了文件路径，默认只能是 properties 文件，不能是 YAML 文件，如果需要支持 yaml 文件，需要自定义 YAML 的 SourceFactory，稍后会给出。
属性配置类需要`setter`方法，所以使用了 lombok 的`@Data`。

在配置文件`mail-config.yml`中，对应的配置如下：

    mail:
      # simple config
      host: 127.0.0.1
      port: 110
      from: 000@test.com

      # list config
      default-recipients:
        - 111@test.com
        - 222@test.com

      # map config
      additional-headers:
        header1: one
        header2: two

      # class member config
      credentials:
        name: kobe
        password: passwd

      # map class config
      member-credentials:
        kobe:
          name: kb
          password: passwd
        james:
          name: lj
          password: passwd

下面是使`@PropertySource`支持 YAML 格式的`YamlPropertySourceFactory.java`:

```java
public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

  @Override
  public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource)
      throws IOException {
    Properties propertiesFromYaml = loadYamlIntoProperties(resource);
    String sourceName = name != null ? name : resource.getResource().getFilename();
    return new PropertiesPropertySource(sourceName, propertiesFromYaml);
  }

  private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
    try {
      YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
      factory.setResources(resource.getResource());
      factory.afterPropertiesSet();
      return factory.getObject();
    } catch (IllegalStateException e) {
      // for ignoreResourceNotFound
      Throwable cause = e.getCause();
      if (cause instanceof FileNotFoundException) {
        throw (FileNotFoundException) e.getCause();
      }
      throw e;
    }
  }

}
```

## 自定义属性的 metadata

对于自定义属性，IDE 等会提示：无法解析配置属性。出现该提示是因为这些自定义属性缺少元数据(metadata)。元数据对程序的执行没有任何影响，只是给属性配置提供一些描述信息。

![config-warn-IDE](./config-warn-IDE.png)

在`src/main/resources`目录下新建目录`META-INF`，并新建文件`spring-configuration-metadata.json`，内容为：

    {
      "properties": [{
        "name": "app.name",
        "type": "java.lang.String",
        "description": "the name of the application.",
        "defaultValue": ""
      }]
    }

IDE 中的提示将会消失，鼠标放上在属性上，会提示`spring-configuration-metadata.json`中对应配置项的`description`信息。

## 基于 profile 的配置

可以基于不同的环境定义不同的配置文件，基于 profile 的配置的规则是：application-{profile}.yml 或 application-{profile}.properties。
也可以使用一个配置文件，在里面通过`---`分隔不同的 profile。

    logging:
      level:
        root: info

    ---
    spring:
      profiles: local
      datasource:
        url: jdbc:mysql://localhost/test
        username: test
        password: passwd

    logging:
      level:
        root: debug
    ---
    spring:
      profiles: dev
      datasource:
        url: jdbc:mysql://localhost/test
        username: test
        password: passwd
    ---
    spring:
      profiles: prod
      datasource:
        url: jdbc:mysql://localhost/test
        username: test
        password: passwd

要使用具体的 profile，可以通过上面提到的四种方式配置：

    $ export SPRING_PROFILES_ACTIVE=prod,audit
    $ java -jar xxx.jar --spring.profiles.active=prod,audit
    $ java -Dspring.profiles.active=prod xxx.jar

    spring.profiles.active:
      - prod
      - audit

可以使用`@Profile`根据特定的 profile 去加载 Bean，profile 名称前面使用`!`表示取反，如：

```java
@Profile({"local", "dev"})
@Profile("!prod")

```

Spring Boot 默认提供属性配置有很多，详细列表请参考[Appendix A. Common application properties
](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)

### 参考

- [HTTPS using Self-Signed Certificate in Spring Boot](https://www.baeldung.com/spring-boot-https-self-signed-certificate)
- [Use @PropertySource with YAML files](https://mdeinum.github.io/2018-07-04-PropertySource-with-yaml-files/)
