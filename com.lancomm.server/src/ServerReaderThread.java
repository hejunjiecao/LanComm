import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class ServerReaderThread extends Thread{
    private Socket socket;
    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //接受消息:1.用户上线消息包含昵称 2.群聊消息 3.私聊消息
        //要声明协议发送消息来判断消息类型
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dataInputStream.readInt();
                switch (type) {
                    case 1://服务端接受昵称数据,转发给所有客户端,并且更新在线人数
                        String nickname = dataInputStream.readUTF();
                        //登录成功,存进online集合
                        Server.onlineUsers.put(socket,nickname);
                        updateOnlineUserList();
                        break;
                    case 2://服务端接受群聊消息,转发给所有客户端
                        String msg = dataInputStream.readUTF();
                        sendMsgToAll(msg);
                        break;
                    case 3://服务端口收到私聊消息,转发给指定用户
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
            Server.onlineUsers.remove(socket);
            updateOnlineUserList();
        }
    }

    private void sendMsgToAll(String msg) {
        StringBuilder sb = new StringBuilder();
        String name = Server.onlineUsers.get(socket);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd E HH:mm:ss");
        String date = dtf.format(now);
        String msgResult = sb.append(name).append(" ").append(date).append("\r\n")
                .append(msg).append("\r\n").toString();
        for(Socket socket : Server.onlineUsers.keySet()) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeInt(2);//说明信息类型
                dataOutputStream.writeUTF(msgResult);
                dataOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void updateOnlineUserList() {
        Collection<String> onlineUsers = Server.onlineUsers.values();
        for(Socket socket : Server.onlineUsers.keySet()) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeInt(1);//说明信息类型
                dataOutputStream.writeInt(onlineUsers.size());//说明转发给多少个客户端
                for(String onlineUser : onlineUsers) {
                    dataOutputStream.writeUTF(onlineUser);
                }
                dataOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
