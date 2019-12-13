package com.czf.server.core.net.wrapper.impl;

import com.czf.server.core.net.support.nio.NioPoller;
import com.czf.server.core.net.wrapper.SocketWrapper;
import lombok.Getter;

import java.io.IOException;
import java.nio.channels.SocketChannel;

@Getter
public class NioSocketWrapper implements SocketWrapper {
    private SocketChannel socketChannel;
    private NioPoller nioPoller;

    public NioSocketWrapper(SocketChannel socketChannel, NioPoller nioPoller){
        this.socketChannel = socketChannel;
        this.nioPoller = nioPoller;
    }

    @Override
    public void close() throws IOException {

    }
}
