package com.systeminfos.nio.netty.window;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * netty 发送工具测试
 */
public class NettyWindow extends JPanel {

    private NettyWindow() {
        this.setSize(400, 800);
        this.init();
    }

    private JButton sendButton1 = new JButton("发送");
    private JButton closeButton1 = new JButton("关闭");
    private JButton clearButton1 = new JButton("清空");

    private JButton sendButton2 = new JButton("发送");
    private JButton closeButton2 = new JButton("关闭");
    private JButton clearButton2 = new JButton("清空");

    private JTextField portText1 = new JTextField();
    private JTextField portText2 = new JTextField();

    private JTextArea sourceText = new JTextArea(35, 30);
    private JTextArea targetText = new JTextArea(35, 30);
    private JTextArea bottomText = new JTextArea(5, 30);
    private JScrollPane sourceScroll = new JScrollPane(sourceText);
    private JScrollPane targetScroll = new JScrollPane(targetText);
    private JScrollPane bottomScroll = new JScrollPane(bottomText);

    private JPanel getMainJPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setSize(new Dimension(400, 800));
        mainPanel.add(getButtonPanel(), BorderLayout.NORTH);
        JPanel centerPanel = new JPanel();
//        centerPanel.setLayout(new GridLayout(1, 2));
        JPanel leftCenterPane = new JPanel();
        leftCenterPane.setLayout(new BorderLayout());
        portText1.setSize(810, 50);
        leftCenterPane.add(portText1, BorderLayout.NORTH);
        leftCenterPane.add(sourceScroll, BorderLayout.CENTER);
        JPanel rightCenterPane = new JPanel();
        rightCenterPane.setLayout(new BorderLayout());
        rightCenterPane.add(portText2, BorderLayout.NORTH);
        rightCenterPane.add(targetText, BorderLayout.CENTER);

        centerPanel.add(leftCenterPane);
        centerPanel.add(rightCenterPane);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomScroll, BorderLayout.SOUTH);
        return mainPanel;
    }

    private void init() {
        sourceText.setLineWrap(true);
        bottomText.setLineWrap(true);
        bottomText.setSelectedTextColor(Color.RED);

        //加密
        sendButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = sourceText.getText();
                if (StringUtils.isEmpty(text)) {
                    bottomText.setText("请输入明文");
                    sourceText.requestFocus();
                    return;
                }
                String textTemp = "";
                try {
                    byte[] encrypt = null;
                    byte[] base64Bytes3 = Base64.encodeBase64(encrypt);
                    textTemp = new String(base64Bytes3, StandardCharsets.UTF_8);
                    bottomText.setText(textTemp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    String exMessage = ex.getMessage();
                    bottomText.setText(exMessage);
                }
                targetText.setText(textTemp);
            }
        });
        //解密
        closeButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = sourceText.getText();
                if (StringUtils.isEmpty(text)) {
                    bottomText.setText("请输入密文");
                    sourceText.requestFocus();
                    return;
                }
                String textTemp = "";
                try {
                    byte[] base64Bytes3 = Base64.decodeBase64(text);
                    byte[] decipherBytes = null;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    String exMessage = ex.getMessage();
                    bottomText.setText(exMessage);
                }
                targetText.setText(textTemp);
            }
        });
        //UrlDecode
        clearButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = sourceText.getText();
                if (StringUtils.isEmpty(text)) {
                    bottomText.setText("请输入URL密文");
                    sourceText.requestFocus();
                    return;
                }
                String textTemp = "";
                try {
                    String decodeText = URLDecoder.decode(text, "utf-8");
                    System.out.println("URL密文Decode:" + decodeText);
                    byte[] base64Bytes3 = Base64.decodeBase64(decodeText);
                    byte[] decipherBytes = null;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    String exMessage = ex.getMessage();
                    bottomText.setText(exMessage);
                }
                targetText.setText(textTemp);
            }
        });

    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.add(sendButton1);
        leftPanel.add(closeButton1);
        leftPanel.add(clearButton1);

        JPanel rightPanel = new JPanel();
        rightPanel.add(sendButton2);
        rightPanel.add(closeButton2);
        rightPanel.add(clearButton2);

        buttonPanel.add(leftPanel);
        buttonPanel.add(rightPanel);
        return buttonPanel;
    }

    /**
     * @param src 16进制字符串转 byte[]
     * @return 字节数组
     * @throws
     * @Title:hexString2Bytes
     * @Description:16进制字符串转 byte[]
     */
    private static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Systeminfos网络发送工具");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);
        frame.setLocation(600, 300);

        NettyWindow swingDecryptPanel = new NettyWindow();
        swingDecryptPanel.setOpaque(true);
        frame.setResizable(true);

        frame.setContentPane(swingDecryptPanel.getMainJPanel());

        frame.setVisible(true);
        frame.pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NettyWindow().createAndShowGUI();
            }
        });
    }


}
