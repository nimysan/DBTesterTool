package top.cuteworld.show.cutestore.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Utils {

    public static final Date currentDate() {
        return new Date();
    }

    public static final Date toDate(Long from) {
        return new Date(from);
    }

    public static final Long currentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 识别IP
     *
     * @param domain
     * @return
     * @throws UnknownHostException
     */
    public static String parseIp(String domain) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(domain);
        return address.getHostAddress();
    }
}
