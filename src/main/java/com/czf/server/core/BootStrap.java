package com.czf.server.core;

import com.czf.server.core.factory.EndPointFactory;
import com.czf.server.core.net.endpoint.EndPoint;

import java.lang.reflect.InvocationTargetException;

public class BootStrap {
    // 启动服务器
    public static void run(){
        // 读取端口号, 之后改成从配置文件读
        int port = 8888;
        // 读取io模型方式
        String iOPattern = "bio";
        // 根据io模型获取对应的EndPoint (使用工厂模式
        EndPoint endpoint;
        try{
            endpoint = EndPointFactory.getInstance(iOPattern);
            endpoint.start();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            //test
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            endpoint.close();
        }

    }
    public static void main(String[] args) {
        BootStrap.run();
    }
}
