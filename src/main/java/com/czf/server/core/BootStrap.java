package com.czf.server.core;

import com.czf.server.core.factory.EndPointFactory;
import com.czf.server.core.net.endpoint.EndPoint;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class BootStrap {
    // 启动服务器
    public static void run(){
        // 读取端口号, 之后改成从配置文件读
        int port = 8888;
        // 读取io模型方式
        String iOPattern = "nio";
        EndPoint endpoint = null;
        try{
            // 根据io模型获取对应的EndPoint (使用工厂模式
            endpoint = EndPointFactory.getInstance(iOPattern);
            // 启动endpoint
            endpoint.start(port);
            Scanner scanner = new Scanner(System.in);
            String order;
            while (scanner.hasNext()) {
                order = scanner.next();
                if (order.equals("EXIT")) {
                    endpoint.close();
                    System.exit(0);
                }
            }
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
            endpoint.close();
        }

    }
    public static void main(String[] args) {
        BootStrap.run();
    }
}
