## Reactor 介绍

`Imperative` 模式：任务由多个子任务构成，数据在只有在一个子任务处理完后才会交给下一个子任务。

`Reactive` 模式：数据也是由多个子任务处理，但是子任务之间是可以并行执行的，每个子任务只处理一部分数据，处理完后交给下一个子任务，同时自己开始处理另一部分数据。

Imperative 和 Reactive 模式，有各自的适用场景，并没有谁更优之说。

Imperative 模式的优点是直观、容易理解。主要缺点是，容易导致阻塞，比如一个线程执行一个耗时操作（如读写数据库或访问远程资源），当前线程就会阻塞等待。当然多线程可以在一定程度上解决问题，但是多线程带来的复杂性也是不容忽略的。

相反，Reactive 模式天生就是函数式  的，通过定义数据流动的 pipeline 或 stream，在数据到达的时候才处理，而不是一次处理所有的数据。

`Backpressure`：通过控制数据源产生数据的速率，以免消费者来不及处理而被淹没。

### Reactors

Reactor 主要有两种类型的数据：`Mono`和`Flux`，其中`Mono`表示单个元素，`Flux`表示一组元素。Reactive Stream 与 Java Stream 操作有一些相似之处：

## 创建

```java
Mono.just("hello");
Mono.justOrEmpty(Optional.empty());

Flux.just("hello", "world", "!");
Flux.fromArray(new String[]{"hello", "world", "!"});
Flux.fromIterable(List.of("hello", "world", "!"));
Flux.fromStream(Stream.of("hello", "world", "!"));
Flux.range(0, 4);
Flux.interval(Duration.ofMillis(100)).take(5);
```

## 操作

```java
Flux<Integer> flux1 = Flux.just(0, 1);
Flux<Integer> flux2 = Flux.just(2, 3);
flux1.mergeWith(flux2);

Flux<Integer> flux3 = Flux.just(0, 2);
Flux<Integer> flux4 = Flux.just(1, 3);
flux3.zipWith(flux4);

Flux.just("Kobe", "Durant", "Curry").map(String::length);

Flux<Integer> length = Flux.just("Kobe", "Durant", "Curry")
  .flatMap(name -> Mono.just(name).map(String::length).subscribeOn(Schedulers.parallel()));
```

### Reactive Stream 工作流

Publisher：可以看作数据的生产者，`Publisher`接口只有一个方法：

    public void subscribe(Subscriber<? super T> s);

Subscriber：数据的消费者，通过`Publisher`的`subscribe()`方法订阅数据。

Processor：数据处理器，作为`Subscriber`获取数据，作为`Publisher`将数据转发给下一个`Subscriber`。

## 本地启动 MongoDB

本地 docker 启动，`docker-compose.yml`文件内容如下：

    version: "3"
    services:
      mongo:
        image: mongo
        hostname: localhost
        ports:
          - 27017:27017
        environment:
          MONGO_INITDB_ROOT_USERNAME: mongo
          MONGO_INITDB_ROOT_PASSWORD: mongo
        container_name: my_mongo

使用`docker-commpose`启动：

    $ docker-compose up

添加依赖，如果使用 blocking 的 mongodb，对应的依赖为：`spring-boot-starter-data-mongodb`， 如果使用 reactive 模型，
则需要使用依赖：`spring-boot-starter-data-mongodb-reactive'`

    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-webflux', version: "$springBootVersion"
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-data-mongodb-reactive', version: "$springBootVersion"
    implementation group: 'io.projectreactor', name: 'reactor-test', version: "$reactorVersion"

配置 mongodb 连接，编辑`application.yml`：

    spring:
      data:
        mongodb:
          host: localhost
          port: 27017
          username: mongo
          password: mongo
          authentication-database: admin

##
