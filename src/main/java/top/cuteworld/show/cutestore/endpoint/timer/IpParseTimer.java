package top.cuteworld.show.cutestore.endpoint.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.cuteworld.show.cutestore.boot.MonitorSettings;
import top.cuteworld.show.cutestore.model.domain.metrics.IpParseResult;
import top.cuteworld.show.cutestore.model.domain.metrics.ESMetricsRepository;
import top.cuteworld.show.cutestore.utils.Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@Component
public class IpParseTimer {

    private final MonitorSettings monitorSettings;
    private final ESMetricsRepository ipParseResultRepository;

    public IpParseTimer(MonitorSettings monitorSettings, ESMetricsRepository ipParseResultRepository) {
        this.monitorSettings = monitorSettings;
        this.ipParseResultRepository = ipParseResultRepository;

        //启动一个线程发送
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    IpParseTimer.this.gatherMetrics();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public void gatherMetrics() {
        log.debug("----Start metric domain names");
        List<String> domains = this.monitorSettings.getDomains();
        if (domains != null) {
            domains.stream().forEach(t -> testDnsHost(t));
        }
        log.debug("----Finish metric domain names");
    }

    private void testDnsHost(String domain) {
        long start = System.currentTimeMillis();
        IpParseResult ipParseResult;
        try {
            InetAddress address = InetAddress.getByName(domain);
            ipParseResult = IpParseResult.builder().dns(domain).jvmIp(address.getHostAddress()).timestamp(Utils.currentTime()).build();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            ipParseResult = IpParseResult.builder().dns(domain).jvmIp(e.getMessage()).timestamp(Utils.currentTime()).build();
        }

        try {
            ipParseResult.setNslookupIp(Utils.parseIpByOS(domain));
        } catch (IOException e) {
            e.printStackTrace();
            ipParseResult.setNslookupIp("");
        }

        ipParseResultRepository.save(ipParseResult);
        log.debug("Cost {} ms to test host {}", System.currentTimeMillis() - start, domain);
    }

}
