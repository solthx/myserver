package NIO;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Test {
    private ByteBuffer readBuffer = ByteBuffer.allocateDirect(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocateDirect(1024);
    private Selector selector;

    public Test() throws IOException {
        // 获取server实例
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 与Selector一起使用时，Channel必须处于非阻塞模式下
        serverSocketChannel.configureBlocking(false);
        // .accept()是阻塞式监听, 下面的两行是实现了非阻塞的监听.
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(8080));
        System.out.println("listening on port 8080");
        this.selector = Selector.open();
        // 把serverChannel注册到Selector上.. 感兴趣的事件为发生accept.
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void go() throws IOException {
        while( selector.select()>0 ){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while( iterator.hasNext() ){
                SelectionKey curKey = iterator.next();
                iterator.remove();
                if ( curKey.isAcceptable() ){
                    System.out.println("isAccepted!");
                    ServerSocketChannel server = (ServerSocketChannel) curKey.channel();
                    SocketChannel client = server.accept();
                    if ( client==null ){
                        System.out.println("....");
                    }

                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    buff.put("response".getBytes());
                    buff.flip();
                    client.write(buff);
                }else if ( curKey.isReadable() ){
                    System.out.println("isReadable!");
                    SocketChannel client = (SocketChannel) curKey.channel();
                    readBuffer.clear();
                    client.read(readBuffer);
                    readBuffer.flip();
                    String recv = Charset.forName("UTF-8").decode(readBuffer).toString();
                    System.out.println(recv);
                    curKey.attach("server message echo: " + recv);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Test().go();
    }
}
