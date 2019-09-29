## 手写mybatis

## just do it
1. 创建数据库test，创建表student,添加jdbc依赖。  
    CREATE TABLE student ( id bigint, age INT, name VARCHAR(50) ) ENGINE = InnoDB CHARSET = gbk;
2. 自定义MyExecutor，并创建实现类MyBaseExecutor实现查询方法(jdbc)
3. 创建Student实体类，创建对应的StudentMapper和StudentMapperXML(直接用类来代替xml文件)
4. 自定义MySqlSession，并创建对应的MyDefautSqlSession和创建mapper对应的代理handler类MyMapperProxy。
5. 创建启动类BootStrap并执行之。

