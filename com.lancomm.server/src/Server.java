import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Server {
    //定义一个集合来存储客户端管道Socket
    public static final Map<Socket,String> onlineUsers = new HashMap<Socket,String>();
    public static void main(String[] args){
        System.out.println("Server starting...");
        try {
            //注册端口
            ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
            //主线程负责接受客户端的连接请求
            while (true) {
                //使用accept,获取客服端的socket对象
                System.out.println("Waiting for connection...");

                Socket socket = serverSocket.accept();
                //把这个管道交给一个线程来处理,以便支持多个客户端来通信
                new ServerReaderThread(socket).start();

                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
