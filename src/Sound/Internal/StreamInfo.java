package Sound.Internal;

import java.net.URL;

/**
 * Simply stores information about a Stream.
 *
 * @author carlos
 */
public class StreamInfo {

    public final URL URL;
    public final long NUM_BYTES_PER_CHANNEL;

    public StreamInfo(URL url, long numBytesPerChannel) {
        this.URL = url;
        this.NUM_BYTES_PER_CHANNEL = numBytesPerChannel;
    }
}
