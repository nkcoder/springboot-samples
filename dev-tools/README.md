# Key Points

`spring-boot-devtools`提供了一些辅助开发的功能，比如：

- 自动禁用依赖库或模板的缓存，启动 web 模块的 debug 日志
- 自动重启：保存文件（Eclipse）或者 Build（Intellij Idea）时自动重启应用
- LiveReload 自动触发浏览器的刷新（资源发生变化时）
- 全局配置：提供全局配置文件，对所有包含 devtools 的应用生效

在实际开发中，自动重启功能比较有用，所以这里主要介绍自动重启的配置与使用。

首先需要添加依赖，并将依赖标记为可选的：

maven 配置：

    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-devtools</artifactId>
    	<optional>true</optional>
    </dependency>

gradle 配置：

    configurations {
      developmentOnly
      runtimeClasspath {
        extendsFrom developmentOnly
      }
    }
    dependencies {
      developmentOnly("org.springframework.boot:spring-boot-devtools")
    }

- 在依赖配置中，如 maven 中添加`optional`标记，在 gradle 中使用自定义的 `developmentOnly`，表示该依赖不会被传递。
- 在生产环境下，`devtool`会自动禁用，通过`java -jar`启动应用被认为是生产环境。

## 自动重启

如果 `classpath` 上的文件发生了变化，应用会自动重启。

自动重启的触发：

- 在 Eclipse 中，被修改的文件保存时会触发重启；在 Intellij Idea 中，使用`Build -> Build Project (CMD+F9)`手动触发。
- 在 Intellij Idea 中也可以配置在文件保存时触发重启： 
    - Preference -> compiler -> Build project automatically: 选中
    - Help -> Find action(Shift + CMD + A) -> compiler.automake.allow.when.app.running: 选中

自动重启原理：Spring Boot 会使用两个 `classloader`，不会发生变化的类（主要是第三方依赖库）会被加载到 `base classloader` 中，开发过程中使用的类会被加载到 `restart classloader`，应用重启时，只有 `restart classloader` 会重启，而 `base classloader` 不变，所以重启速度会更快一些。另一方面，如果依赖发生了变化，则需要手动重启。

### 扩展自动重启监视的目录

    spring.devtools.restart.exclude=static/**,public/**
    spring.devtools.restart.additional-paths=mypackage/**

### 禁用自动重启：

自动重启是通过配置`spring.devtools.restart.enabled`控制的，所以可以在 application.properties 中设置：

    spring.devtools.restart.enabled=false

或者在调用`SpringApplication.run()`之前调用：

    System.setProperty("spring.devtools.restart.enabled", "false");

项目的源码见[Github](https://github.com/nkcoder/spring-demo/blob/master/dev-tools/README.md)

## 参考

- 1. [Spring Boot Developer Tools: How to enable automatic restart in IntelliJ IDEA](https://dev.to/suin/spring-boot-developer-tools-how-to-enable-automatic-restart-in-intellij-idea-1c6i)
- 2. [Developer Tools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html)
