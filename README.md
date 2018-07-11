### SpringBoot 简化了框架 spring web 的配置成本，简单的配置即可搭建，让开发者可以只专注于代码的开发。
### 配置篇
- Spring Boot 省略了配置 `web.xml` ，以及tomcat（内置），包括其他的扫描路径都已经配有默认配置，默认扫描 启动 `main` 函数包下的所有 `bean` ，以及默认扫描`application.yml`（或者 `application.properties` ）中的配置（如启动端口号等）
- `application.yml` 中的配置可以直接通过 `@value` 注入,也可以自定义配置文件，使用时需要指定文件路径即可。
- 实现多环境配置可通过配置文件后缀区分，比如 `application-dev.yml` 代表开发环境，`application-test.yml` 代表测试环境，可根据 `application.yml` 中指定 `profiles` 是 `dev` 或者 `test` 来加载不同的环境配置.

### 数据库篇
- `jdbcTemplate`是对`jdbc`的封装，配置了数据库信息后，`spring boot`会自动将数据源注入到`jdbcTemplate`中，使用起来也非常方便，在dao层
注入`jdbcTemplate`，即可调用封装好的各种增删改查方法，使用方法类似原生jdbc，需要手写sql，传递参数，查询方法需要使用`rowMapper`
映射数据库字段。
- `pring Data JPA`是基于ORM框架和JPA规范的一套JPA应用框架，简化了开发者对数据集操作的过程，例如使用dao层接口继承`JpaRepository`类即可生成
诸多对表的基本增删改查操作，无需书写具体的实现，使用时直接注入dao接口即可使用。也可以根据需要自定义sql，只是定义方法是需要
遵循规则命名，如`findByLastnameAndFirstname`对应 where x.lastname = ? and x.firstname = ?（还未深入研究）。配置`spring.jpa.properties.hibernate.hbm2ddl.auto`参数可自动根据`entity`维护数据库表结构,`create`是每次都新建表，`update`会根据变化修改表结构,`validate`校验变化但是不修改。



遇到问题：
添加jdbc或者jpa依赖后，如果还为配置数据源信息启动就会报错，因为启动时`spring boot`发现依赖了这两个包后就会去加载数据源，查询
不到就会报错。
