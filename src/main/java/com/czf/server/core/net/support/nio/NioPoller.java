package com.czf.server.core.net.support.nio;

import com.czf.server.core.net.endpoint.impl.NioEndPoint;
import com.czf.server.core.net.wrapper.impl.NioSocketWrapper;
import lombok.Getter;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class NioPoller implements Runnable{
    private NioEndPoint endPoint;
    private Selector selector;
    private ConcurrentLinkedQueue<RegisterEvent> events; // register事件队列，每次selector前都先将这个队列里的事件注册完.

    public NioPoller(NioEndPoint endPoint){
        this.endPoint = endPoint;
    }

    public void register(SocketChannel socketChannel){
        // 把新来的socket_channel包装成一个register事件，丢到队列里
        RegisterEvent registerEvent = new RegisterEvent(new NioSocketWrapper(socketChannel, this));
        events.add(registerEvent);
        selector.wakeup();  // 如果selector一直处于阻塞状态，那么当前register永远不能写入进去..
    }

    // 执行Register事件队列
    private void execute(){
        RegisterEvent event;
        while ( !events.isEmpty() ){
            event = events.poll();
            event.run();
        }
    }

    @Override
    public void run() {
        int cnt;
        while( endPoint.isRunning() ){
            execute();
            try {
                cnt = selector.select();
                if ( cnt<=0 ) continue;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // to do NioPoller
}
