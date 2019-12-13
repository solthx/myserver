package com.czf.server.core.net.dispatch.impl;

import com.czf.server.core.net.dispatch.Dispatcher;
import com.czf.server.core.net.wrapper.SocketWrapper;
import com.czf.server.core.net.wrapper.impl.NioSocketWrapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NioDispatcher implements Dispatcher {
    @Override
    public void doDispatcher(SocketWrapper wrapper) {
        // to do NioDispacher
        // 打印测试
        NioSocketWrapper nioSocketWrapper = (NioSocketWrapper) wrapper;
        SocketChannel socketChannel = nioSocketWrapper.getSocketChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        try {
            buff.clear();
            int read = socketChannel.read(buff);
            buff.flip();
            if ( read>0 ){
                String recv = Charset.forName("UTF-8").decode(buff).toString();
                System.out.println(recv);
            }else{
                System.out.println("关闭");
                nioSocketWrapper.getSelectionKey().cancel(); // 取消这个连接
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
