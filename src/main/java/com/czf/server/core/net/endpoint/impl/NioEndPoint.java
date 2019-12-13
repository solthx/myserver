package com.czf.server.core.net.endpoint.impl;

import com.czf.server.core.net.acceptor.impl.NioAcceptor;
import com.czf.server.core.net.dispatch.impl.NioDispatcher;
import com.czf.server.core.net.endpoint.EndPoint;
import com.czf.server.core.net.support.nio.NioPoller;
import com.czf.server.core.net.support.nio.PollerCleaner;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NioEndPoint implements EndPoint {
    private ServerSocketChannel server;
    private NioDispatcher dispatcher;
    private List<NioPoller> Pollors;
    private NioAcceptor acceptor;
    private PollerCleaner cleaner;
    private AtomicInteger idx = new AtomicInteger(0);
    private final int pollersCnt = Math.min( 2, Runtime.getRuntime().availableProcessors());
    private boolean isRunning = false;

    @Override
    public void start(int port) {
        try {
            isRunning = true;
            server = ServerSocketChannel.open();
            dispatcher = new NioDispatcher();
            InitAcceptor();
            InitPollers();
            //InitCleaner();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning(){
        return isRunning;
    }

    private void InitAcceptor() {
        acceptor = new NioAcceptor(this);
        Thread acceptorThread = new Thread(acceptor);
        acceptorThread.start();
    }

    // 启动cleaner线程
    private void InitCleaner() {
        cleaner = new PollerCleaner();
        Thread cleanerThread = new Thread(cleaner);
        cleanerThread.start();
    }

    // 初始化并启动Pollers线程
    private void InitPollers() {
        Pollors = new ArrayList<NioPoller>();
        for ( int i=0; i<pollersCnt; ++i ){
            NioPoller poller = new NioPoller(this);
            // 初始化NioPoller, 并启动
            Thread pollerThread = new Thread(poller);
            Pollors.add(poller);
            pollerThread.start(); // 进行select
        }
    }


    public SocketChannel accept() throws IOException {
        return server.accept();
    }

    public void register(SocketChannel socketChannel){
        getPoller().register(socketChannel);
    }

    /**
     * 负载均衡
     * @return
     */
    private NioPoller getPoller() {
        NioPoller next_poller = Pollors.get(idx.getAndIncrement());
        if ( idx.get()>=pollersCnt )
            idx.set(0);
        return next_poller;
    }

    @Override
    public void close() {

    }
}
