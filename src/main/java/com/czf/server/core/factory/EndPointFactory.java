package com.czf.server.core.factory;

import com.czf.server.core.net.endpoint.EndPoint;

import java.lang.reflect.InvocationTargetException;

public class EndPointFactory {
    // 根据输入的io模型来实例化对象
    public static EndPoint getInstance(String ioPattern) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        StringBuilder clazzName = new StringBuilder("com.czf.server.core.net.endpoint.impl.");
        clazzName.append((char)(ioPattern.charAt(0)&'_'))
                 .append(ioPattern.substring(1))
                 .append("EndPoint");
        return (EndPoint) Class.forName(clazzName.toString())
                               .getDeclaredConstructor()
                               .newInstance();
    }
}
