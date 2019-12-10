package com.czf.server.core.net.wrapper.impl;

import com.czf.server.core.net.wrapper.SocketWrapper;

import java.io.IOException;
import java.net.Socket;

public class BioSocketWrapper implements SocketWrapper {
    private Socket socket = null;
    public BioSocketWrapper(Socket socket){
        this.socket = socket;
    }

    @Override
    public void close() throws IOException {
        if ( socket!=null )
            socket.close();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
