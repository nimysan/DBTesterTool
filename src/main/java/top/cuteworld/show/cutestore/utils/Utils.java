package top.cuteworld.show.cutestore.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.config.Lookup;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

@Slf4j
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

    /**
     * 调用系统nslookup获取IP
     *
     * @param domain
     * @return
     * @throws IOException
     */
    public static String parseIpByOS(String domain) throws IOException {
        Process exec = Runtime.getRuntime().exec(new String[]{"nslookup", domain});
        List<String> strings = IOUtils.readLines(exec.getInputStream(), Charset.defaultCharset());
        if (strings.size() < 5) {
            return "";
        }

        StringBuffer ips = new StringBuffer();
        for (int i = 4; i < strings.size(); i++) {
            String line = strings.get(i);
            if (line.startsWith("Address:")) {
                ips.append(line.substring(8).trim()).append(",");
            }
//            log.info(line);
        }
        String result = ips.toString();
        if (result.endsWith(",")) {
            return result.substring(0, result.length() - 1);
        }
        return result;
    }

}
