package com.systeminfos.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component()
@Order(value = 3)
public class NettyServer implements CommandLineRunner {

    private final int port = 8081; // Set the Netty server port

    /**
     * 重写run方法以启动Netty服务器
     * 该方法配置并启动一个Netty服务器，使用指定的端口
     *
     * @param args 可变参数，这里未使用
     */
    @Override
    public void run(String... args) throws Exception {
        //线程安全：每个 Netty Server 需要独立的 EventLoopGroup，否则可能引发线程安全问题。
        new Thread(() -> {
            // 创建一个老板组，用于接收客户端连接请求
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            // 创建一个工作组，用于处理客户端连接后的IO操作
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                // 初始化服务器配置
                ServerBootstrap bootstrap = new ServerBootstrap();
                // 配置服务器的老板组、工作组和通道
                bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) {
                        // 在通道初始化时添加自定义处理器
                        channel.pipeline()
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new NettyServerHandler());
                    }
                });

                // 绑定端口并启动服务器
                ChannelFuture future = bootstrap.bind(port).sync();
                // 服务器启动成功后打印消息
                System.out.println("Netty server started on port " + port);
                // 等待服务器端口关闭
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                // 异常处理：打印中断异常堆栈跟踪
                e.printStackTrace();
            } finally {
                // 关闭老板组和工作组，释放资源
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    class NettyServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {

            String receivedMessage = "";
            if (msg instanceof ByteBuf) {
                // 将 ByteBuf 转换为字符串
                ByteBuf byteBuf = (ByteBuf) msg;
                receivedMessage = byteBuf.toString(StandardCharsets.UTF_8);
            }

            // Handle incoming message (msg)
            System.out.println("Received ByteBuf: " + msg);
            System.out.println("Received message: " + receivedMessage);

            Channel channel = ctx.channel();
            System.out.println("Channel ID: " + channel.id());
            // Respond to the client
            ctx.writeAndFlush("Hello from Netty server!");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.run();
    }

}

