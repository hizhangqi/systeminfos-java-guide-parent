package com.systeminfos.nio.apdu;

import javax.smartcardio.*;
import java.util.Arrays;
import java.util.List;

public class APDUExample {

    public static void main(String[] args) {
        try {
            // 获取与智能卡通信的TerminalFactory
            TerminalFactory terminalFactory = TerminalFactory.getDefault();

            // 获取可用的智能卡终端
            CardTerminals cardTerminals = terminalFactory.terminals();
            List<CardTerminal> terminals = cardTerminals.list();

            if (!terminals.isEmpty()) {
                // 选择第一个终端
                CardTerminal cardTerminal = terminals.get(0);

                // 连接智能卡
                Card card = cardTerminal.connect("*");

                // 获取与智能卡通信的通道
                CardChannel cardChannel = card.getBasicChannel();

                // 发送C-APDU命令
                byte[] commandAPDU = {(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x07, (byte) 0xD2, (byte) 0x76, (byte) 0x00, (byte) 0x00, (byte) 0x85, (byte) 0x01, (byte) 0x00, (byte) 0x00};
                ResponseAPDU responseAPDU = cardChannel.transmit(new CommandAPDU(commandAPDU));

                // 处理R-APDU响应
                byte[] responseData = responseAPDU.getBytes();
                System.out.println("Response Data: " + Arrays.toString(responseData));

                // 断开连接
                card.disconnect(true);
            } else {
                System.out.println("No card terminals found.");
            }
        } catch (CardException e) {
            e.printStackTrace();
        }
    }
}
