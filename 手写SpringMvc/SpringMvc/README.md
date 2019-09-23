## 手写实现简单SpringMVC

### 实现功能
1. @Autowired注解
2. @Controller注解
3. @RequestMapping注解
4. @Service注解

### just do it
1. 模仿Spring创建出上面四个注解,com.reallinxu.anno包(参考Spring源码)  
    java自带注解功能对照: 
    + @Target({ElementType.TYPE}) : 标志此注解可以修饰在哪些地方，类，成员变量，方法等  
    + @Retention(RetentionPolicy.RUNTIME) : Annotation的生命周期，一般情况下，我们自定义注解的话，显然需要在运行期获取注解的一些信息。  
    + @Documented : JavaDoc文档
    
2. 编写核心控制器：DispatcherServlet
    pom中添加servlet依赖，dispatch中重写方法init，主要实现以下四步：  
    1. 扫描基包下的所有类
    2. 拿到@Controller/@Service注解对应的名称，并初始化它们修饰的类，形成映射关系
    3. 扫描有@Autowired的字段完成自动注入
    4. 扫描@RequestMapping，完成URL到某一个Controller的某一个方法上的映射关系

3. 更改pom文件，使编译为war包，并添加war包编译插件，添加后编译运行，使用tomcat发布。