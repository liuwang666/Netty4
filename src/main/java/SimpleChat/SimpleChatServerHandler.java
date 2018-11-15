package SimpleChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.Iterator;

public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup channels;

    public SimpleChatServerHandler() {
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        Iterator var3 = channels.iterator();

        while(var3.hasNext()) {
            Channel channel = (Channel)var3.next();
            channel.writeAndFlush("聊天室 ：欢迎" + incoming.remoteAddress() + "加入");
        }

        channels.add(ctx.channel());
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        Iterator var3 = channels.iterator();

        while(var3.hasNext()) {
            Channel channel = (Channel)var3.next();
            channel.writeAndFlush("聊天室 ：" + incoming.remoteAddress() + "离开");
        }

        channels.remove(ctx.channel());
    }

    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel incoming = ctx.channel();
        Iterator var4 = channels.iterator();

        while(var4.hasNext()) {
            Channel channel = (Channel)var4.next();
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
            } else {
                channel.writeAndFlush("我 ：" + s + "\n");
            }
        }

    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("聊天室 :" + incoming.remoteAddress() + "在线....");
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("聊天室 :" + incoming.remoteAddress() + "掉线....");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();
        System.out.println("聊天室 :" + incoming.remoteAddress() + "异常....");
       // cause.printStackTrace();
        ctx.close();
    }

    static {
        channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }
}
