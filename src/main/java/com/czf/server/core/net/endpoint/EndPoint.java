package com.czf.server.core.net.endpoint;

/*
* EndPoint提供监听端口的服务器，并将监听到的socket向下交付
* */
public abstract class EndPoint {
    public abstract void start(); // 启动服务器
    public abstract void close(); // 关闭服务器
}
