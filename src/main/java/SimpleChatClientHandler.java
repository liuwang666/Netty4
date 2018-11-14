import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
/*    @Override
    protected void channelRead0(ChannelHandlerContext , String s) throws Exception {

    }*/
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(s);
    }
}