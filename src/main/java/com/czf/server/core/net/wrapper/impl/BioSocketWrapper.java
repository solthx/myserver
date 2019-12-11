package com.czf.server.core.net.wrapper.impl;

import com.czf.server.core.net.wrapper.SocketWrapper;
import lombok.Data;

import java.io.IOException;
import java.net.Socket;

@Data
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

}
