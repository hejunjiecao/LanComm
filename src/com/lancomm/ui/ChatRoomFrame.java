package com.lancomm.ui;

import javax.swing.*;
import java.awt.*;

public class ChatRoomFrame extends JFrame {

    private JTextArea messageDisplayArea; // 消息显示区域
    private JTextArea messageInputArea; // 消息输入区域
    private JList<String> userList; // 用户列表
    private JButton sendButton; // 发送按钮

    public ChatRoomFrame() {
        // 设置窗口标题
        setTitle("LanComm - Chat Room");
        setSize(700, 500); // 调整窗口大小，更加协调
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // 窗口居中

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // 设置边距
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 增加整体留白
        add(mainPanel);

        // 消息显示区域
        messageDisplayArea = new JTextArea();
        messageDisplayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageDisplayArea.setEditable(false);
        messageDisplayArea.setLineWrap(true);
        messageDisplayArea.setWrapStyleWord(true);
        JScrollPane messageDisplayScrollPane = new JScrollPane(messageDisplayArea);
        messageDisplayScrollPane.setBorder(BorderFactory.createTitledBorder("Messages"));
        mainPanel.add(messageDisplayScrollPane, BorderLayout.CENTER);

        // 用户列表
        userList = new JList<>();
        userList.setFont(new Font("Arial", Font.PLAIN, 14));
        userList.setBackground(Color.decode("#EEEEEE"));
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(150, 0));
        userScrollPane.setBorder(BorderFactory.createTitledBorder("Online Users"));
        mainPanel.add(userScrollPane, BorderLayout.EAST);

        // 底部面板
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10)); // 设置边距
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 消息输入区域
        messageInputArea = new JTextArea(3, 40);
        messageInputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageInputArea.setLineWrap(true);
        messageInputArea.setWrapStyleWord(true);
        JScrollPane messageInputScrollPane = new JScrollPane(messageInputArea);
        messageInputScrollPane.setBorder(BorderFactory.createTitledBorder("Enter your message"));
        bottomPanel.add(messageInputScrollPane, BorderLayout.CENTER);

        // 发送按钮
        // 创建发送按钮
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setForeground(Color.WHITE); // 字体颜色为白色
        sendButton.setBackground(Color.decode("#009688")); // 背景为绿色
        sendButton.setFocusPainted(false); // 去掉按钮焦点虚线
        sendButton.setBorder(BorderFactory.createLineBorder(Color.decode("#00695C"), 1)); // 添加边框以确保可见
        sendButton.setOpaque(true); // 确保背景绘制
        sendButton.setPreferredSize(new Dimension(100, 50)); // 设置按钮大小

// 鼠标悬停效果
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                sendButton.setBackground(Color.decode("#00796B")); // 鼠标悬停时背景变深
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                sendButton.setBackground(Color.decode("#009688")); // 恢复默认绿色
            }
        });

// 将按钮添加到底部面板
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // 设置窗口可见
        setVisible(true);
    }

}