## 手写Hash

### just do it
1. 创建MyMap接口并内部创建Entry接口
2. 创建MyEntry实现Entry接口
3. 创建MyHashMap实现MyMap接口
4. 创建测试类Test并执行之

### hashMap死循环原因
hashmap非线程安全，jdk1.8以前在resize扩容时，会将同一个hash值的链表通过头插法重新放入新的数组中，此时顺序会改变，如果并发操作，线程1读取A节点后设置A节点的next是B，但线程2填充新数组时又将B节点的next设为A，就会导致死循环，jdk1.8后都是用尾插法，不会存在这个问题
