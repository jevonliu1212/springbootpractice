### SpringBoot简化了框架spring web的配置成本，简单的配置即可搭建，让开发者可以只专注于代码的开发。
- Spring Boot省略了配置web.xml，以及tomcat（内置），包括其他的扫描路径都已经配有默认配置，默认扫描 启动main函数包下的所有bean，以及默认扫描application.yml（或者application.properties）中的配置（如启动端口号等）
- application.yml中的配置可以直接通过@value注入,也可以自定义配置文件，使用时需要指定文件路径即可。
- 实现多环境配置可通过配置文件后缀区分，比如application-dev.yml代表开发环境，application-test.yml代表测试环境，可根据application.yml中指定profiles是dev或者test来加载不同的环境配置.
