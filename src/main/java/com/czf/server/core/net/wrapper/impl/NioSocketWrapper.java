package com.czf.server.core.net.wrapper.impl;

import com.czf.server.core.net.support.nio.NioPoller;
import com.czf.server.core.net.wrapper.SocketWrapper;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

@Data
public class NioSocketWrapper implements SocketWrapper {
    private SocketChannel socketChannel;
    private NioPoller nioPoller;
    private SelectionKey selectionKey;

    public NioSocketWrapper(SocketChannel socketChannel, NioPoller nioPoller){
        this.socketChannel = socketChannel;
        this.nioPoller = nioPoller;
    }

    @Override
    public void close() throws IOException {

    }
}
