package com.czf.server.core.net.acceptor.impl;

import com.czf.server.core.net.acceptor.Acceptor;
import com.czf.server.core.net.endpoint.impl.NioEndPoint;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class NioAcceptor implements Acceptor, Runnable {
    private NioEndPoint endPoint;
    public NioAcceptor(NioEndPoint endPoint){
        this.endPoint = endPoint;
    }
    @Override
    public void run() {
        // Nio Listening...
        SocketChannel socketChannel;
        while( endPoint.isRunning() ){
            try {
                socketChannel = endPoint.accept();
                endPoint.register(socketChannel); // 把刚接收到的socket注册到selector里
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
