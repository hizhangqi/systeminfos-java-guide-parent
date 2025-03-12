package com.systeminfos.nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient2 {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8081;
    private static final int MAX_RECONNECT_ATTEMPTS = 5;
    private static final long RECONNECT_DELAY_MS = 5000; // 5 seconds

    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public static void main(String[] args) throws Exception {
        new NettyClient2().start();
    }

    public void start() throws Exception {
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new ClientHandler(b));
            }
        });

        connect(b, HOST, PORT, 0);
    }

    private void connect(Bootstrap b, String host, int port, int attempt) {
        b.connect(host, port).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                channel = future.channel();
                channel.writeAndFlush("Hello, Server!\n");
            } else {
                if (attempt < MAX_RECONNECT_ATTEMPTS) {
                    System.out.println("连接失败，尝试重新连接 (尝试次数: " + (attempt + 1) + ")");
                    future.channel().eventLoop().schedule(() -> connect(b, host, port, attempt + 1), RECONNECT_DELAY_MS, java.util.concurrent.TimeUnit.MILLISECONDS);
                } else {
                    System.out.println("达到最大重连次数，放弃连接");
                }
            }
        });
    }

    public void stop() {
        if (channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
    }

    class ClientHandler extends ChannelInboundHandlerAdapter {

        private final Bootstrap bootstrap;

        public ClientHandler(Bootstrap bootstrap) {
            this.bootstrap = bootstrap;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println("收到服务器的响应: " + msg);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            System.out.println("连接断开，尝试重新连接");
            connect(bootstrap, HOST, PORT, 0);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
