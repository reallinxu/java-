package com.reallinxu.doit;

import java.io.*;

public class MyClassLoader extends ClassLoader{

    //生成的代理类加载路径
    private File dir;

    private String proxyClassPackage;

    public String getProxyClassPackage(){
        return proxyClassPackage;
    }

    public File getDir() {
        return dir;
    }

    public MyClassLoader(String path,String proxyClassPackage){
        this.dir = new File(path);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        if(dir != null){
            File classFile = new File(dir,name+".class");
            if(classFile.exists()){
                //获取class文件
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try {
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = in.read(bytes)) != -1){
                        out.write(bytes,0,len);
                    }
                    return defineClass(proxyClassPackage+"."+name,out.toByteArray(),0,out.size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        return super.findClass(name);





    }
}
