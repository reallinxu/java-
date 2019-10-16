package com.reallinxu;

import com.reallinxu.bean.Product;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        IProductService iproductService = (IProductService) rpc(IProductService.class);
        Product product = iproductService.queryById(100);
        System.out.println(product);
    }

    public static Object rpc(final Class clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8888);
                //我们想远程调用哪个类的哪个方法，并传递给这个方法什么参数
                String apiClassName = clazz.getName();
                String methodName = method.getName();
                Class[] paramterTypes = method.getParameterTypes();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeUTF(apiClassName);
                objectOutputStream.writeUTF(methodName);
                objectOutputStream.writeObject(paramterTypes);
                objectOutputStream.writeObject(args);
                objectOutputStream.flush();

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object object = objectInputStream.readObject();
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                return object;
            }
        });
    }
}
