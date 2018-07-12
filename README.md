### SpringBoot 简化了框架 spring web 的配置成本，简单的配置即可搭建，让开发者可以只专注于代码的开发。
### 配置篇
- Spring Boot 省略了配置 `web.xml` ，以及tomcat（内置），包括其他的扫描路径都已经配有默认配置，默认扫描 启动 `main` 函数包下的所有 `bean` ，以及默认扫描`application.yml`（或者 `application.properties` ）中的配置（如启动端口号等）
- `application.yml` 中的配置可以直接通过 `@value` 注入,也可以自定义配置文件，使用时需要指定文件路径即可。
- 实现多环境配置可通过配置文件后缀区分，比如 `application-dev.yml` 代表开发环境，`application-test.yml` 代表测试环境，可根据 `application.yml` 中指定 `profiles` 是 `dev` 或者 `test` 来加载不同的环境配置.

### 数据库篇
- **jdbctemplate**：`jdbcTemplate`是对`jdbc`的封装，配置了数据库信息后，`spring boot`会自动将数据源注入到`jdbcTemplate`中，使用起来也非常方便，在dao层
注入`jdbcTemplate`，即可调用封装好的各种增删改查方法，使用方法类似原生jdbc，需要手写sql，传递参数，查询方法需要使用`rowMapper`
映射数据库字段。
- **JPA**：`pring Data JPA`是基于ORM框架和JPA规范的一套JPA应用框架，简化了开发者对数据集操作的过程，例如使用dao层接口继承`JpaRepository`类即可生成
诸多对表的基本增删改查操作，无需书写具体的实现，使用时直接注入dao接口即可使用。也可以根据需要自定义sql，只是定义方法是需要
遵循规则命名，如`findByLastnameAndFirstname`对应 where x.lastname = ? and x.firstname = ?。配置`spring.jpa.properties.hibernate.hbm2ddl.auto`参数可自动根据`entity`维护数据库表结构,`create`是每次都新建表，`update`会根据变化修改表结构,`validate`校验变化但是不修改,一般开发不建议使用，因为会频繁修改表结构。
- **mybatis**：`spring boot`整合`mybatis`也非常简单，只需要添加`mybatis-spring-boot-starter`依赖，定义对应的`mapper`接口，加上@Mapper注解，在扫描路径内就会被自动注册。接口内一个方法代表一个sql语句，可以使用注解，如`@select`,`@insert`等将sql直接写在方法上。也可以使用xml配置sql,使用`namespace`配置与`mapper`接口的关联关系。
- **事务**：`spring boot`默认为`jdbcTemplate`，`JPA`和`mybatis`开启了事务管理，所以只需要在指定的方法或者类上加上`@Transaction`即可启用。


遇到问题：
添加`jdbc`或者`jpa`依赖后，如果还为配置数据源信息启动就会报错，因为启动时`spring boot`发现依赖了这两个包后就会去加载数据源，查询
不到就会报错。

### redis篇
**整合redis**：使用方法类似`jdbcTemplate`，添加`spring-boot-starter-data-redis`依赖，配置`redis`数据源，使用`stringRedisTemplate`获得`ValueOperations`对象，就可以对`redis`数据进行操作。

**redis缓存**:`redis`缓存可以减少系统与数据库的交互，在同一个方法，且传入参数相同的情况下请求，就会直接取出redis中缓存好的数据直接返回，提升效率，节约资源。存入`redis`的缓存格式为:`"[\"com.springboot.entity.Employee\",{\"id\":1,\"name\":\"333\",\"mobile\":\"333\"}]"`

- 开启`redis`缓存需要配置`cacheManager`，`redisTemplate`等`bean`,里面包含了对象的序列化和反序列化处理，`redis`缓存的`key`也是可以指定自动生成规则的，`cacheManager`中可以设置缓存过期时间。
- `@Cacheable`需要设置`value`(`cache`名，可以理解为一个分组，下面可以有很多缓存对象)，`key`(存入`redis`时的`key`)，例如：`@Cacheable(value = "emp",key="#id")`，其中`id`是传入方法的变量名，该标签加在方法上面，第一次调用spring boot就会以设置的key为键，返回的对象为`value`存入`redis`。往后每次调用方法都会先去`redis`查询是否已有对应缓存，有的话直接返回不会进方法，没有的话就会查询并返回结果，并存到`redis`中。
- `@CachePut`跟`@Cacheable`有所区别，参数设置和`@Cacheable`一样，但是使用`@CachePut`后方法依旧会被调用，并且会根据`key`更新`redis`中的缓存。
- `@CacheEvict`用来清除缓存，需要设置`value`值指定需要删除的缓存，`value`可以接受数组，且设置`allEntries=true`就可以将指定缓存下的缓存数据全部清除。例如：`@CacheEvict(value = "emp",key ="#id",allEntries=true)`
