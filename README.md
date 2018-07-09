### SpringBoot 简化了框架 spring web 的配置成本，简单的配置即可搭建，让开发者可以只专注于代码的开发。
- Spring Boot 省略了配置 `web.xml` ，以及tomcat（内置），包括其他的扫描路径都已经配有默认配置，默认扫描 启动 `main` 函数包下的所有 `bean` ，以及默认扫描`application.yml`（或者 `application.properties` ）中的配置（如启动端口号等）
- `application.yml` 中的配置可以直接通过 `@value` 注入,也可以自定义配置文件，使用时需要指定文件路径即可。
- 实现多环境配置可通过配置文件后缀区分，比如 `application-dev.yml` 代表开发环境，`application-test.yml` 代表测试环境，可根据 `application.yml` 中指定 `profiles` 是 `dev` 或者 `test` 来加载不同的环境配置.
