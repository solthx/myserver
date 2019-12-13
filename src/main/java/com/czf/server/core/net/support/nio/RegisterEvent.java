package com.czf.server.core.net.support.nio;

import com.czf.server.core.net.wrapper.impl.NioSocketWrapper;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;

public class RegisterEvent implements Runnable {
    private NioSocketWrapper wrapper;
    public RegisterEvent(NioSocketWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void run() {
        try {
            // 注册
            wrapper.getSocketChannel().register( wrapper.getNioPoller().getSelector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE , wrapper );
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }

    }
}
