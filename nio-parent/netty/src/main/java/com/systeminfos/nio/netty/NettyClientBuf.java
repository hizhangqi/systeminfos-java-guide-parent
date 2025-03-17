package com.systeminfos.nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * ByteBuf 客户端
 */
public class NettyClientBuf {

    private final String host;
    private final int port;

    public NettyClientBuf(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientHandler());
                }
            });

            // 连接到服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 等待连接关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    class ClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 当连接建立时发送一条消息
            ChannelManager.bindRequestId(ctx.channel(), ctx.channel().id().asLongText());
            ByteBuf buffer = ctx.alloc().buffer();

            byte[] requestIdBytes = "123456".getBytes();
            buffer.writeInt(requestIdBytes.length); // 写入 requestId 长度
            buffer.writeBytes(requestIdBytes);      // 写入 requestId 内容

            // 写入 Body
            byte[] dataBytes = "body".getBytes();
            buffer.writeInt(dataBytes.length);      // 写入消息体长度
            buffer.writeBytes(dataBytes);           // 写入消息体内容

            // 发送数据
            ctx.writeAndFlush(buffer);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 接收服务器返回的消息
            String receivedMessage = "";
            if (msg instanceof ByteBuf) {
                // 将 ByteBuf 转换为字符串
                ByteBuf byteBuf = (ByteBuf) msg;
                receivedMessage = byteBuf.toString(StandardCharsets.UTF_8);
            }
            System.out.println("收到服务器消息: " + receivedMessage);
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // 异常处理
            cause.printStackTrace();
            ctx.close();
        }

    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8081;
        new NettyClientBuf(host, port).start();
    }

}