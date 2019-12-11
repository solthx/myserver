package com.czf.server.core.net.handler.impl;

import com.czf.server.core.net.handler.Handler;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Data
public class BioHandler implements Handler {
    private Socket socket=null;
    public BioHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        InputStream in = null;
        try {
            // 打印测试
            in = socket.getInputStream();
            byte[] buf = new byte[1024];
            int cnt;
            while ((cnt = in.read(buf)) > 0)
                System.out.print(new String(buf, 0, cnt));
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // 关闭连接
            try {
                if ( in!=null )
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
