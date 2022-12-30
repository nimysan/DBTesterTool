package top.cuteworld.show.cutestore.endpoint.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cuteworld.show.cutestore.boot.MonitorSettings;
import top.cuteworld.show.cutestore.model.domain.metrics.IpParseResult;
import top.cuteworld.show.cutestore.model.domain.metrics.ESMetricsRepository;
import top.cuteworld.show.cutestore.utils.Utils;

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
    }

    @Scheduled(cron = "0/1 * * ? * ?")
    public void cron() {
        List<String> domains = this.monitorSettings.getDomains();
        if (domains != null) {
            domains.stream().forEach(t -> testDnsHost(t));
        }
    }

    private void testDnsHost(String domain) {
        IpParseResult ipParseResult;
        try {
            InetAddress address = InetAddress.getByName(domain);
            ipParseResult = IpParseResult.builder().dns(domain).ip(address.getHostAddress()).timestamp(Utils.currentTime()).build();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ipParseResult = IpParseResult.builder().dns(domain).ip(e.getMessage()).timestamp(Utils.currentTime()).build();
        }
        ipParseResultRepository.save(ipParseResult);
    }

}
