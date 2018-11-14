import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class DiscardServerHandler extends ChannelHandlerAdapter {
    public DiscardServerHandler() {
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.writeAndFlush(msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
