## 手写jdk动态代理

### 回顾jdk动态代理
1. 创建被代理的接口(ProxyObjectInte)和实现类(ProxyObject)
2. 创建TestHandler继承自InvocationHandler
3. 创建测试类TestJdkProxy，通过Proxy.newProxyInstance创建代理类，调用代理类方法。

### just do it
1. 仍使用上面的代理接口以及实现类
2. 自定义创建MyInvocationHandler，并创建MyHandler继承之
3. 自定义创建MyClassLoader继承自ClassLoader，用于加载自己创建的代理类
4. 自定义创建MyProxy生成代理类java文件并编译
5. 创建测试类TestMyProxy，调用代理方法