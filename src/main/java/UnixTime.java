import java.util.Date;

public class UnixTime {
    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }

    public String toString() {
        return (new Date((this.value() - 2208988800L) * 1000L)).toString();
    }
}
