`spring-boot-devtools`提供了一些辅助功能，可以提高开发阶段的效率，主要包括：默认属性(Property Defaults), 自动重启(Auto Restart), LiveReload, 全局设置(Global Settings)。

## 添加依赖

maven配置：

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<optional>true</optional>
	</dependency>
	
gradle配置：

    configurations {
      developmentOnly
      runtimeClasspath {
        extendsFrom developmentOnly
      }
    }
    dependencies {
      developmentOnly("org.springframework.boot:spring-boot-devtools")
    }
    
- `devtool`在生产环境自动禁用，比如使用`java -jar`启动。
- 在依赖配置中，如maven中添加`optional`标记，在gradle中使用自定义的developmentOnly，表示该依赖不会被传递。

    
## 自动重启

  如果classpath上的文件发生了变化，应用会自动重启。
  自动重启的触发：在Eclipse中，被修改的文件保存时会触发重启；在Intellij Idea中，使用`Build -> Build Project (CMD+F9)`手动触发。
  在Intellij Idea中也可以配置在文件保存时触发重启：
    - Preference -> compiler -> Build project automatically: 选中
    - Help -> Find action(Shift + CMD + A) -> compiler.automake.allow.when.app.running: 选中
    
重启原理：Spring Boot会使用两个classloader，不经常变化的类（主要时第三方依赖）会被加载到base classloader中，开发过程中使用的类会被加载到restart classloader，应用重启时，只有restart classloader会重启，而base classloader不变，所以重启速度会更快一些。另一方面，如果依赖发生了变化，则需要手动重启。

禁用自动重启：
  - 在application.properties中配置：spring.devtools.restart.enabled=false
  - 在调用`SpringApplication.run()`之前调用：System.setProperty("spring.devtools.restart.enabled", "false");

## 参考

- 1. [Spring Boot Developer Tools: How to enable automatic restart in IntelliJ IDEA](https://dev.to/suin/spring-boot-developer-tools-how-to-enable-automatic-restart-in-intellij-idea-1c6i)
- 2. [Developer Tools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html)