package com.czf.server.core.net.endpoint;

import java.net.Socket;

/*
* EndPoint提供监听端口的服务器，并将监听到的socket向下交付
* 一个EndPoint实例，对应一个接口的监听.
* */
public interface EndPoint {
    public abstract void start(int port); // 启动服务器
    public abstract void close(); // 关闭服务器

}
