package com.czf.server.core.net.support.nio;

import com.czf.server.core.net.wrapper.impl.NioSocketWrapper;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class RegisterEvent implements Runnable {
    private NioSocketWrapper wrapper;
    public RegisterEvent(NioSocketWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void run() {
        try {
            // 注册
            SocketChannel channel = wrapper.getSocketChannel();
            channel.configureBlocking(false);
            SelectionKey key = channel.register( wrapper.getNioPoller().getSelector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE , wrapper );
            wrapper.setSelectionKey(key);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
