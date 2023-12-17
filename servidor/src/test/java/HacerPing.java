import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertTrue;

public class HacerPing {
    private InetAddress host = InetAddress.getByName("26.207.45.189");

    public HacerPing() throws UnknownHostException {
    }

    @Test
    public void realizarPingAlHost() throws IOException {
        boolean seHizoPing = host.isReachable(10000);
        assertTrue(seHizoPing);
        System.out.println(host.getHostName());
    }

}
