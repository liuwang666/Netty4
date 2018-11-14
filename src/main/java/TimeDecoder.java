import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {
    public TimeDecoder() {
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() >= 4) {
            out.add(new UnixTime(in.readUnsignedInt()));
        }
    }
}
