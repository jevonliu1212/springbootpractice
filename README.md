## SpringBoot 简化了框架 spring web 的配置成本，简单的配置即可搭建，让开发者可以只专注于代码的开发。
### 1. 配置篇
- Spring Boot 省略了配置 `web.xml` ，以及tomcat（内置），包括其他的扫描路径都已经配有默认配置，默认扫描 启动 `main` 函数包下的所有 `bean` ，以及默认扫描`application.yml`（或者 `application.properties` ）中的配置（如启动端口号等）
- `application.yml` 中的配置可以直接通过 `@value` 注入,也可以自定义配置文件，使用时需要指定文件路径即可。
- 实现多环境配置可通过配置文件后缀区分，比如 `application-dev.yml` 代表开发环境，`application-test.yml` 代表测试环境，可根据 `application.yml` 中指定 `profiles` 是 `dev` 或者 `test` 来加载不同的环境配置.

### 2. 数据库篇

#### 2.1 jdbctemplate：

 `jdbcTemplate`是对`jdbc`的封装，配置了数据库信息后，`spring boot`会自动将数据源注入到`jdbcTemplate`中，使用起来也非常方便，在dao层
注入`jdbcTemplate`，即可调用封装好的各种增删改查方法，使用方法类似原生jdbc，需要手写sql，传递参数，查询方法需要使用`rowMapper`
映射数据库字段。

#### 2.2 JPA：
`pring Data JPA`是基于ORM框架和JPA规范的一套JPA应用框架，简化了开发者对数据集操作的过程，例如使用dao层接口继承`JpaRepository`类即可生成
诸多对表的基本增删改查操作，无需书写具体的实现，使用时直接注入dao接口即可使用。也可以根据需要自定义sql，只是定义方法是需要遵循规则命名，如`findByLastnameAndFirstname`对应 where x.lastname = ? and x.firstname = ?。配置`spring.jpa.properties.hibernate.hbm2ddl.auto`参数可自动根据`entity`维护数据库表结构,`create`是每次都新建表，`update`会根据变化修改表结构,`validate`校验变化但是不修改,一般开发不建议使用，因为会频繁修改表结构。
#### 2.3 mybatis：
`spring boot`整合`mybatis`也非常简单，只需要添加`mybatis-spring-boot-starter`依赖，定义对应的`mapper`接口，加上@Mapper注解，在扫描路径内就会被自动注册。接口内一个方法代表一个sql语句，可以使用注解，如`@select`,`@insert`等将sql直接写在方法上。也可以使用xml配置sql,使用`namespace`配置与`mapper`接口的关联关系。
#### 2.4 事务：
`spring boot`默认为`jdbcTemplate`，`JPA`和`mybatis`开启了事务管理，所以只需要在指定的方法或者类上加上`@Transaction`即可启用。


>遇到问题：
> 添加`jdbc`或者`jpa`依赖后，如果还为配置数据源信息启动就会报错，因为启动时`spring boot`发现依赖了这两个包后就会去加载数据源，查询
不到就会报错。


### 3. redis篇

#### 3.1 整合redis：
使用方法类似`jdbcTemplate`，添加`spring-boot-starter-data-redis`依赖，配置`redis`数据源，使用`stringRedisTemplate`获得`ValueOperations`对象，就可以对`redis`数据进行操作。

#### 3.2 redis缓存:

`redis`缓存可以减少系统与数据库的交互，在同一个方法，且传入参数相同的情况下请求，就会直接取出redis中缓存好的数据直接返回，提升效率，节约资源。存入`redis`的缓存格式为:

``` java
["com.springboot.entity.Employee",{"id":1,"name":"333","mobile":"333"}]

```

1. 开启`redis`缓存需要配置`cacheManager`，`redisTemplate`等`bean`,里面包含了对象的序列化和反序列化处理，`redis`缓存的`key`也是可以指定自动生成规则的，`cacheManager`中可以设置缓存过期时间。

2. `@Cacheable`需要设置`value`(`cache`名，可以理解为一个分组，下面可以有很多缓存对象)，`key`(存入`redis`时的`key`)，例如：`@Cacheable(value = "emp",key="#id")`，其中`id`是传入方法的变量名，该标签加在方法上面，第一次调用spring boot就会以设置的key为键，返回的对象为`value`存入`redis`。往后每次调用方法都会先去`redis`查询是否已有对应缓存，有的话直接返回不会进方法，没有的话就会查询并返回结果，并存到`redis`中。
3. `@CachePut`跟`@Cacheable`有所区别，参数设置和`@Cacheable`一样，但是使用`@CachePut`后方法依旧会被调用，并且会根据`key`更新`redis`中的缓存。
4. `@CacheEvict`用来清除缓存，需要设置`value`值指定需要删除的缓存，`value`可以接受数组，且设置`allEntries=true`就可以将指定缓存下的缓存数据全部清除。例如：

	``` java
	@CacheEvict(value = "emp",key ="#id",allEntries=true)
	
	```
### 4. 定时任务

#### 4.1 配置

`spring boot` 开启定死任务只需要两步：

1.程序入口添加 `@EnableScheduling` 注解

2.对应方法上添加 `@Scheduled` 注解

#### 4.2 cron表达式

##### 4.2.1 域

`cron`包括以下几个域：**Seconds Minutes Hours DayofMonth Month DayofWeek Year**（可为空）

每一个域可出现的字符如下： 

**Seconds**:可出现", - * /"四个字符，有效范围为0-59的整数 

**Minutes**:可出现", - * /"四个字符，有效范围为0-59的整数 

**Hours**:可出现", - * /"四个字符，有效范围为0-23的整数 

**DayofMonth**:可出现", - * / ? L W C"八个字符，有效范围为0-31的整数 

**Month**:可出现", - * /"四个字符，有效范围为1-12的整数或JAN-DEC 

**DayofWeek**:可出现", - * / ? L C #"四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推 

Year:可出现", - * /"四个字符，有效范围为1970-2099年

##### 4.2.1 字符详解

每一个域都使用数字，但还可以出现如下特殊字符，它们的含义是： 

- (1) * ：表示匹配该域的任意值，假如在Minutes域使用*, 即表示每分钟都会触发事件。

- (2) ? ：**只能用在DayofMonth和DayofWeek两个域**。它也匹配域的任意值，但实际不会。因为DayofMonth和 DayofWeek会相互影响。例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, **其中最后一位只能用？**，而不能使用*，如果使用*表示不管星期几都会触发，实际上并不是这样。 

- (3) - ：表示范围，例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次 

- (4) / ：表示起始时间开始触发，然后每隔固定时间触发一次，例如在Minutes域使用5/20,则意味着5分钟触发一次，而25，45等分别触发一次. 

- (5) , :表示列出枚举值值。例如：在Minutes域使用5,20，则意味着在5和20分每分钟触发一次。 

- (6) L :表示最后，只能出现在DayofWeek和DayofMonth域，如果在DayofWeek域使用5L,意味着在最后的一个**星期四**触发。 

- (7) W :表示有效工作日(周一到周五),只能出现在DayofMonth域，系统将在离指定日期的最近的有效工作日触发事件。例如：在 DayofMonth使用5W，如果5日是星期六，则将在最近的工作日：星期五，即4日触发。如果5日是星期天，则在6日(周一)触发；如果5日在星期一 到星期五中的一天，则就在5日触发。另外一点，**W的最近寻找不会跨过月份** 

- (8) LW:这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五。 

- (9) #:用于确定每个月第几个星期几，只能出现在DayofWeek域。例如在4#2，表示某月的第二个星期三。

##### 4.2.2 实例

（1）`0 0 2 1 * ? *`   表示在每月的1日的凌晨2点开始任务

（2）`0 15 10 ? * MON-FRI`   表示周一到周五每天上午10:15执行作业

（3）`0 15 10 L * ?`    每月**最后一日**的上午10:15触发

（4）`0 0/30 9-17 * * ?`   朝九晚五工作时间内每半小时 

（5）`0 0-5 14 * * ?`    在每天下午2点到下午2:05期间的每1分钟触发 

（6）`0 15 10 ? * 6L 2002-2005`   2002年至2005年的每月的**最后一个星期五**上午10:15触发 

（7）`0 15 10 ? * 6#3`   每月的**第三个星期五**上午10:15触发

#### 5. logback配置

日志的输出也是系统的重要的组成部分，合理的日志对于日后追踪系统报错原因会有很大的帮助。配置 `logback` 需要在 `src/main/resources` 下新建 `logback.xml` ，在文件中定制自己日志的输出和存储方式。下面介绍主要配置参数：

**1. root节点**:必选节点，通过 `level `配置整个项目的基本输出级别,`level` 属性级别由低到高为`TRACE < DEBUG < INFO < WARN < ERROR < FATAL`。

``` xml

    <root level="DEBUG">
         <appender-ref ref="FILE" />
         <appender-ref ref="STDOUT" />
    </root> 
```

**2. appender节点**:定义日志的输出格式或者存储策略。可以通过定义 `pattern` 来规定输出的格式，比如 `%d{yyyy-MM-dd HH:mm:ss} [%p] [%t] %c{36} - %m%n`，`%d` 表示显示日期格式，`%t` 显示线程名，`%m` 显示输出信息等等。像 `RollingFileAppender` 这种输出日志文件的`appender` 还需要配置存储策略，比如存储多少天，文件名，最大容量等等。

``` xml

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder"> 
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>   
        </encoder> 
    </appender>
```

**3. logger节点**:日志记录器，`appender` 配置的执行者。可以针对某个包或者类定制输出格式，只需要在 `class` 属性中定义路径。

``` xml

    <logger name="com.springboot.controller" level="DEBUG"  additivity="false">
        <appender-ref ref="STDOUT" />
    </logger>
```

**4. springProfile节点**:可以通过指定 `name` 属性来使各节点在不同的环境下生效，达到不同环境不同输出的效果。

``` xml 

   <springProfile name="dev">
    <logger name="com.springboot.controller" level="DEBUG"  additivity="false">
        <appender-ref ref="STDOUT" />
     </logger>
   </springProfile>
```

#### 6. RestTemplate消费服务

`RestTemplate` 是封装了的 `http` 请求，可以更加简单的访问 `rest` 服务，不需要使用原生的`http` 。配置方面只需要在 `context` 里添加相应的 `bean` ，即可直接将 `RestTemplate` 注入使用，方法有诸如 `getForEntity` ，`postForEntity` 等等，方法名对应请求方式 `get` 和`post` ，传入对应的 `class` 类型可以将返回值映射成对应的对象。

``` java

ResponseEntity<Employee>  res =restTemplate.getForEntity("http://localhost:8888/emp/1", Employee.class);

```

``` java

Map<String,Object> postData = new HashMap<>();
postData.put("id", 1);
postData.put("name", "222");
postData.put("mobile","444");
restTemplate.postForEntity("http://localhost:8888/emp/update", postData, null);
		
```

#### 7. 文件上传

使用 `MultipartFile` 接收需要上传的文件，可以获取到文件的名称和大小等数据，通过流的方式进行上传。上传的文件也可以通过在 `application.properties` 配置来限制，超出范围就会抛出异常，配置参数如下：

``` xml

spring.http.multipart.max-file-size=128KB

spring.http.multipart.max-request-size=128KB

```

#### 8. 整合ActiveMQ

配置类似 `redis`，配置 `ActiveMQ` 相关参数，使用 `template` 模版对象 `JmsMessagingTemplate` 即可向指定队列发送消息，接收方使用监听器（ `@JmsListener` 注解即可）监听队列，即可消费队列里的内容。

`application.properties`配置:

``` xml

spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.in-memory=true
spring.activemq.pool.enabled=false

```

- **Quene**:队列是点对点的模式，队列里的每个消息只能被一个客户端消费。如果多个消息消费者正在监听队列上的消息，，JMS消息服务器将根据“先来者优先”的原则确定由哪个消息消费者接收下一条消息。如果没有消息消费者在监听队列，消息将保留在队列中，直至消息消费者连接到队列为止。

- **Topic**:通过该消息传递模型，应用程序能够将一条消息发送给多个消息消费者。在此传送模型中，消息目的地类型是主题。消息首先由消息生产者发布至消息服务器中特定的主题中，然后由消息服务器将消息传送至所有已订阅此主题的消费者。开启 `topic` 功能需要另外添加 `spring.jms.pub-sub-domain=true`，因为 `spring boot` 默认 `topic` 是关闭的。

#### 9. 拦截器

拦截器可以拦截指定格式的请求，可以在请求开始前后添加一些行为，对应的方法都封装了`HttpServletRequest` 对象，可以获取需要的请求信息，拦截时发现请求不符合业务需求时可以直接抛异常或者返回 `false` 阻止请求执行下去。配置拦截器的步骤：

- 定义拦截器：定义一个类，继承 `HandlerInterceptorAdapter` 实现对应的 `preHandle` 和 `postHandle` 等方法。
- 注册拦截器：定义拦截器后需要托管给 `spring` 才能生效，定义一个配置类，实现接口   `WebMvcConfigurerAdapter` 实现 `addInterceptors` 将定义的拦截器注册生效，还可以定义`pattern` 来指定需要拦截的请求，比如/**表示所有请求等等。

#### 10. ApplicationRunner和CommandLineRunner
有时候系统会需要这样一个需求，就是在系统启动完成前进行一些操作，比如查询数据缓存起来，这时候就会用到 `ApplicationRunner` 和 `CommandLineRunner` ，定义一个类实现这两个接口中任意一个都可以实现这种功能，具体的逻辑写在对应的 `run` 方法里。当有多个 `runner` 时可以通过 `@Order` 注解来指定执行顺序，数字越小优先级越高，两种 `runner` 都可以使用。

扩展：除了使用这两个接口之外，使用监听器也可以完成类似的功能，监听器可以监听不同是的 `event` ，如果我们监听 `ApplicationReadyEvent` 事件，也可以实现在项目启动完成前进行一些操作。具体使用方法：1.定义自己的 `event` ，需要继承 `ApplicationEvent` 类。2.定义一个类，定义一个监听的方法，方法入参即需要监听的 `event` 对象，同时需要加上 `@EventListener` 注解。

``` java

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventHandler {

	@EventListener
	public void listener(ApplicationEvent event){
		System.out.println("application event。。。。。。。。。。。。。。。"+event.getClass().getName());
	}
}
