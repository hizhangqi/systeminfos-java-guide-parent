package com.systeminfos.nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient4 {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8081;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new StringDecoder())
                            .addLast(new StringEncoder())
                            .addLast(new ClientHandler(bootstrap));
                }
            });

            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class ClientHandler extends SimpleChannelInboundHandler<String> {

        private final Bootstrap bootstrap;

        public ClientHandler(Bootstrap bootstrap) {
            this.bootstrap = bootstrap;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 当连接建立时发送一条消息
            ctx.writeAndFlush("Hello, Server!");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            System.out.println("连接断开，尝试重新连接");
            connect(bootstrap, HOST, PORT, 0);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) {
            System.out.println("收到消息: " + msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int attempt) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else {
                System.out.println("连接失败，尝试重新连接");
                // 这里可以根据需要添加重试逻辑
                connect(bootstrap, host, port, attempt + 1);
            }
        });
    }
}
