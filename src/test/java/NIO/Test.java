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

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
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
                SelectionKey curKey = (SelectionKey) iterator.next();
                // 删除这个
                iterator.remove();

                if ( curKey.isAcceptable() ){
                    System.out.println("isAccepted!");
                    ServerSocketChannel serverChannel = (ServerSocketChannel) curKey.channel();
                    SocketChannel channel = serverChannel.accept();

                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (curKey.isReadable()) {
                    System.out.println("isReadable");
                    SocketChannel socketChannel = (SocketChannel) curKey.channel();
                    readBuffer.clear();
                    int cnt = socketChannel.read(readBuffer);
                    readBuffer.flip();
                    if (cnt > 0) {
                        String receiveData = Charset.forName("UTF-8").decode(readBuffer).toString();
                        System.out.println("receiveData:" + receiveData);
                        curKey.attach("server message echo:" + receiveData);
                    }else{
                        System.out.println("客户端关闭");
                        curKey.cancel();
                    }
                }
            }
        }
    }

    public void handler(SelectionKey key) throws IOException {
        //客户端请求连接事件
        if(key.isAcceptable()){
            handlerAccept(key);
        }
        //获得了可读事件
        else if(key.isReadable()){
            handlerRead(key);
        }
    }

    /**
     * 处理连接请求
     * @param key
     * @throws IOException
     */
    public void handlerAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        //获得和客户端连接的通道
        SocketChannel channel = serverSocketChannel.accept();
        //设置成非阻塞
        channel.configureBlocking(false);
        //在这里可以给客户端发送信息
        System.out.println("新的客户端连接");
        //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限
        channel.register(this.selector,SelectionKey.OP_READ);
    }

    /**
     * 处理读的事件
     * @param key
     * @throws IOException
     */
    public void handlerRead(SelectionKey key) throws IOException {
        //服务器可以读取消息：获取事件发生的socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        if(read > 0){
            byte[] data = buffer.array();
            String msg = new String(data).trim();
            System.out.println("服务端收到信息：" + msg);
            //回写数据
            ByteBuffer outBuffer = ByteBuffer.wrap("服务端收到了你的消息".getBytes());
            channel.write(outBuffer);
        }else {
            System.out.println("客户端关闭");
            key.cancel();
        }
    }
    public static void main(String[] args) throws Exception {
        new Test().go();
    }
}
