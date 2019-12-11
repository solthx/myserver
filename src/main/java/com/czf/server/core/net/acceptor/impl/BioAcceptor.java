package com.czf.server.core.net.acceptor.impl;

import com.czf.server.core.net.acceptor.Acceptor;
import com.czf.server.core.net.dispatch.impl.BioDispatcher;
import com.czf.server.core.net.endpoint.impl.BioEndPoint;
import com.czf.server.core.net.wrapper.impl.BioSocketWrapper;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Data
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
            while( server.isRunning() ) {
                client = server.accept();
                dispatcher.doDispatcher(new BioSocketWrapper(client)); // 这里不能阻塞，但却出现了阻塞。
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            try {
                if (in!=null)
                    in.close();
                if ( client!=null )
                    client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
