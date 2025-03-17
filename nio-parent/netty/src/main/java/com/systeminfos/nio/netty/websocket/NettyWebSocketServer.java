package com.systeminfos.nio.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(5)
public class NettyWebSocketServer implements CommandLineRunner {

    private final int port = 8083; // WebSocket 服务端端口

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            // BossGroup 负责接收客户端连接
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            // WorkerGroup 负责处理数据读写
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        // HTTP 编解码
                        pipeline.addLast(new HttpServerCodec());
                        // 支持大数据流
                        pipeline.addLast(new ChunkedWriteHandler());
                        // 聚合 HTTP 请求
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        // WebSocket 协议处理器，指定 WebSocket 路径
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                        // 自定义处理器
                        pipeline.addLast(new WebSocketServerHandler());
                    }
                });

                ChannelFuture future = bootstrap.bind(port).sync();
                System.out.println("WebSocket Server started on port " + port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            // 接收客户端消息
            String clientMsg = msg.text();
            System.out.println("Received: " + clientMsg);

            // 回复消息给客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame("Server time: " + LocalDateTime.now() + " | Message: " + clientMsg));
            // 广播消息给所有连接的客户端
            ConnectionManager.getInstance().broadcast("Broadcast: " + clientMsg);
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client connected: " + ctx.channel().id().asLongText());
            ConnectionManager.getInstance().addConnection(ctx.channel());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client disconnected: " + ctx.channel().id().asLongText());
            ConnectionManager.getInstance().removeConnection(ctx.channel());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}