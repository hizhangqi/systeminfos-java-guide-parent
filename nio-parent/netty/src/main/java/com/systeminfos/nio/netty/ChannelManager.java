package com.systeminfos.nio.netty;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class ChannelManager {
    // 定义一个 AttributeKey，用于存储 requestId
    private static final AttributeKey<String> REQUEST_ID_KEY = AttributeKey.valueOf("REQUEST_ID");

    /**
     * 将 requestId 绑定到 Channel 上
     */
    public static void bindRequestId(Channel channel, String requestId) {
        channel.attr(REQUEST_ID_KEY).set(requestId);
    }

    /**
     * 从 Channel 中获取 requestId
     */
    public static String getRequestId(Channel channel) {
        return channel.attr(REQUEST_ID_KEY).get();
    }

    /**
     * 获取 Channel 的唯一 ID
     */
    public static String getChannelId(Channel channel) {
        return channel.id().asLongText(); // 唯一且全局唯一的 ID
    }
}
