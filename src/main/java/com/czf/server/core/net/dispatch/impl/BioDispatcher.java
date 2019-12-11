package com.czf.server.core.net.dispatch.impl;

import com.czf.server.core.net.dispatch.Dispatcher;
import com.czf.server.core.net.handler.impl.BioHandler;
import com.czf.server.core.net.wrapper.SocketWrapper;
import com.czf.server.core.net.wrapper.impl.BioSocketWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class BioDispatcher implements Dispatcher {

    @Override
    public void doDispatcher(SocketWrapper wrapper) {
        BioSocketWrapper bioWrapper = (BioSocketWrapper) wrapper;
        Socket socket = bioWrapper.getSocket();
        // 任务转发
        Thread processor = new Thread(new BioHandler(socket));
        processor.start();
    }
}
