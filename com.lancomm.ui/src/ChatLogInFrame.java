import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ChatLogInFrame extends JFrame {
    private JTextField nicknameField;
    private JButton enterButton;
    private JButton cancelButton;
    private Socket socket;//当前登录的客户端的通信管道

    public ChatLogInFrame() {
        // 设置窗口标题
        setTitle("Welcome to LanComm");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // 窗口居中

        // 设置背景颜色
        getContentPane().setBackground(Color.decode("#F0F0F0"));

        // 创建主面板并设置布局
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#F0F0F0"));
        add(mainPanel);

        // 创建顶部面板
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBackground(Color.decode("#F0F0F0"));

        // 标签和文本框
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nicknameField = new JTextField(15);
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nicknameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        topPanel.add(nicknameLabel);
        topPanel.add(nicknameField);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.decode("#F0F0F0"));

        enterButton = new JButton("Enter");
        cancelButton = new JButton("Cancel");

        // 设置按钮样式
        enterButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setPreferredSize(new Dimension(100, 35));
        enterButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        enterButton.addActionListener(e -> {
            String nickname = nicknameField.getText().trim();
            nicknameField.setText("");
            if (!nickname.isEmpty()) {
                try {
                    logIn(nickname);
                    new ChatRoomFrame(nickname,socket);//启动聊天界面
                    this.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dispose();
            }else {
                JOptionPane.showMessageDialog(this, "Nickname cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> System.exit(0));

        // 设置窗口可见
        setVisible(true);
    }

    private void logIn(String nickname) throws Exception {
        //创建socket管道 请求于服务端socket连接
        socket = new Socket(Constants.SERVER_IP,Constants.SERVER_PORT);
        //发送消息类型和昵称
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeInt(1);
        dataOutputStream.writeUTF(nickname);
        dataOutputStream.flush();

    }

}