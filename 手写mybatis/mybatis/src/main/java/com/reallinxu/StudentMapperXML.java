package com.reallinxu;

import java.util.HashMap;
import java.util.Map;

public class StudentMapperXML {

    public static final String namespace = "com.reallinxu.StudentMapper";

    private static Map<String,String> methodSqlMap = new HashMap<String, String>();

    static {
        methodSqlMap.put("findById","select * from student where id=%s");
    }

    public static String getMethodSql(String method){
        return methodSqlMap.get(method);
    }
}
