import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("准备运行端口：" + this.port);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b = b.group(bossGroup, workerGroup);
            b = (ServerBootstrap)b.channel(NioServerSocketChannel.class);
            b = b.childHandler(new ChannelInitializer<SocketChannel>() {
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelHandler[]{new DiscardServerHandler()});
                    ch.pipeline().addLast(new ChannelHandler[]{new TimeEncoder(), new TimeServerHandler()});
                }
            });
            b = (ServerBootstrap)b.option(ChannelOption.SO_BACKLOG, 128);
            b = b.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(this.port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }

        (new DiscardServer(port)).run();
        System.out.println("server:run()");
    }
}