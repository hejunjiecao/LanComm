import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientReaderThread extends Thread{
    private Socket socket;
    private DataInputStream dataInputStream;
    private ChatRoomFrame chatRoomFrame;
    public ClientReaderThread(Socket socket, ChatRoomFrame chatRoomFrame) {
        this.socket = socket;
        this.chatRoomFrame = chatRoomFrame;
    }

    @Override
    public void run() {
        //接受消息:1.用户上线消息包含昵称 2.群聊消息 3.私聊消息
        //要声明协议发送消息来判断消息类型
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dataInputStream.readInt();
                switch (type) {
                    case 1://服务端接受昵称数据,转发给所有客户端,并且更新在线人数
                        updateOnlineUserList();
                        break;
                    case 2://服务端接受群聊消息,转发给所有客户端
                        showMsg();
                        break;
                    case 3://服务端口收到私聊消息,转发给指定用户
                        break;
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void showMsg() throws Exception {
        String msg = dataInputStream.readUTF();
        chatRoomFrame.showMsgToAll(msg);
    }

    private void updateOnlineUserList() throws Exception {
        //1.读取有多少个用户
        int count = dataInputStream.readInt();
        //2.循环控制读取多个用户信息
        String[] onlineUsers = new String[count];
        for (int i = 0; i < count; i++) {
            String onlineUser = dataInputStream.readUTF();
            onlineUsers[i] = onlineUser;
        }
        //3.更新到窗口
        chatRoomFrame.updateOnlineUsers(onlineUsers);
    }

}
