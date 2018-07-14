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

**1.root节点**:必选节点，通过 `level `配置整个项目的基本输出级别,`level` 属性级别由低到高为`TRACE < DEBUG < INFO < WARN < ERROR < FATAL`。

``` xml

    <root level="DEBUG">
         <appender-ref ref="FILE" />
         <appender-ref ref="STDOUT" />
    </root> 
```

**2.appender节点**:定义日志的输出格式或者存储策略。可以通过定义pattern来规定输出的格式，比如%d{yyyy-MM-dd HH:mm:ss} [%p] [%t] %c{36} - %m%n，%d表示显示日期格式，%t 显示线程名，%m显示输出信息等等。像RollingFileAppender这种输出日志文件的appender还需要配置存储策略，比如存储多少天，文件名，最大容量等等。

``` xml

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder"> 
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>   
        </encoder> 
    </appender>
```

**3.logger节点**:日志记录器，appender配置的执行者。可以针对某个包或者类定制输出格式，只需要在class属性中定义路径。

``` xml

    <logger name="com.springboot.controller" level="DEBUG"  additivity="false">
        <appender-ref ref="STDOUT" />
    </logger>
```


