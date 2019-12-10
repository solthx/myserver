package com.czf.server.core.net.endpoint.impl;

import com.czf.server.core.net.acceptor.impl.BioAcceptor;
import com.czf.server.core.net.dispatch.impl.BioDispatcher;
import com.czf.server.core.net.endpoint.EndPoint;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class BioEndPoint implements EndPoint {
    private boolean isRunning = false;
    private BioDispatcher dispatcher;
    private BioAcceptor acceptor;
    private ServerSocket server;

    public BioEndPoint(){
        dispatcher = new BioDispatcher();
    }

    @Override
    public void start(int port) {
        try {
            server = new ServerSocket(port);
            acceptor = new BioAcceptor(this,  dispatcher);
            Thread acceptorProcessor = new Thread(acceptor);
            acceptorProcessor.setDaemon(true);
            acceptorProcessor.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(){

    }

    public Socket accept() throws IOException {
        return server.accept();
    }

}
