import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient3 {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8081;

    private final EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap;

    public static void main(String[] args) throws Exception {
        new NettyClient3().start();
    }

    public void start() throws Exception {
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new ClientHandler(bootstrap));
            }
        });

        connect(HOST, PORT);
    }

    private void connect(String host, int port) {
        bootstrap.connect(host, port).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                future.channel().writeAndFlush("Hello, Server!\n");
            } else {
                System.out.println("连接失败，尝试重新连接");
                future.channel().eventLoop().schedule(() -> connect(host, port), 5000, java.util.concurrent.TimeUnit.MILLISECONDS);
            }
        });
    }

    public void stop() {
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
            connect(HOST, PORT);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

        private void connect(String host, int port) {
            bootstrap.connect(host, port).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("重新连接成功");
                } else {
                    System.out.println("重新连接失败，尝试重新连接");
                    future.channel().eventLoop().schedule(() -> connect(host, port), 5000, java.util.concurrent.TimeUnit.MILLISECONDS);
                }
            });
        }
    }
}
