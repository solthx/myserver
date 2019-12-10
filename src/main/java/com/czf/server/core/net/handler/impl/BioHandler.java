package com.czf.server.core.net.handler.impl;

import com.czf.server.core.net.handler.Handler;

import java.io.InputStream;
import java.net.Socket;

public class BioHandler implements Handler {
    private Socket socket=null;
    public BioHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
//            // test
//            System.out.println("获取输入流");
            InputStream in = socket.getInputStream();
//            System.out.println("开始读取");
            byte[] buf = new byte[1024];
            int cnt;
            while ((cnt = in.read(buf)) > 0) {
                System.out.print(new String(buf, 0, cnt));
            }
//            System.out.println("读取完毕");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
