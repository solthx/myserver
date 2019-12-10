package com.czf.server.core.net.acceptor.impl;

import com.czf.server.core.net.acceptor.Acceptor;
import com.czf.server.core.net.dispatch.impl.BioDispatcher;
import com.czf.server.core.net.endpoint.impl.BioEndPoint;
import com.czf.server.core.net.wrapper.impl.BioSocketWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class BioAcceptor implements Acceptor, Runnable {
    private BioEndPoint server;
    private BioDispatcher dispatcher;

    public BioAcceptor(BioEndPoint bioEndPoint, BioDispatcher dispatcher){
        this.server = bioEndPoint;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        Socket client = null;
        InputStream in = null;
        try {
            while( true ) {
//                System.out.println("正在监听...");
                // 这里可以利用信号量来控制最大连接数, 防止线程过多。
                client = server.accept();
//                System.out.println("建立连接...");
                dispatcher.doDispatcher(new BioSocketWrapper(client)); // 这里不能阻塞，但却出现了阻塞。
//                System.out.println("监听结束...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            try {
                if ( client!=null )
                    client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
