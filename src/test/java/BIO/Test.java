package BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while(true) {
            Socket client = server.accept();
            Thread t1 = new Thread(new Handler(client));
            t1.start();
        }
    }
}

class Handler implements Runnable{
    private Socket client;
    public  Handler(Socket socket){
        client = socket;
    }
    @Override
    public void run() {
        InputStream in = null;
        try {
            in = client.getInputStream();
            byte[] buf = new byte[1024];
            int cnt;
            while ((cnt = in.read(buf)) > 0) {
                System.out.print(new String(buf,0, cnt));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
