package com.czf.server.core.net.endpoint.impl;

import com.czf.server.core.net.endpoint.EndPoint;

public class BioEndPoint extends EndPoint {

    @Override
    public void start() {
        //
        System.out.println("服务器启动了...");
    }

    @Override
    public void close() {
        System.out.println("服务器关闭了...");
    }
}
