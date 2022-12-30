package top.cuteworld.show.cutestore.endpoint.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cuteworld.show.cutestore.boot.MonitorSettings;
import top.cuteworld.show.cutestore.model.domain.metrics.DatabaseMetric;
import top.cuteworld.show.cutestore.model.domain.metrics.ESMetricsRepository;
import top.cuteworld.show.cutestore.utils.Utils;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class DbInfoTimer {
    private final MonitorSettings monitorSettings;
    private final JdbcTemplate jdbcTemplate;
    private final ESMetricsRepository esMetricsRepository;

    private static final String SQL = "show status where `variable_name` = 'Threads_connected'";

    public DbInfoTimer(MonitorSettings monitorSettings, JdbcTemplate jdbcTemplate, ESMetricsRepository ipParseResultRepository) {
        this.monitorSettings = monitorSettings;
        this.jdbcTemplate = jdbcTemplate;
        this.esMetricsRepository = ipParseResultRepository;
    }

    @Scheduled(cron = "0/1 * * ? * ?")
    public void test() {
        Integer query = jdbcTemplate.query(SQL, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if (resultSet != null && resultSet.next()) {
                    int activeThreadCount = resultSet.getInt(2);
                    return activeThreadCount;
                }
                return 0;
            }
        });

        DatabaseMetric metric = DatabaseMetric.builder().hostName(monitorSettings.getDbhost()).ip(getServerIP()).timestamp(Utils.currentTime()).threadCounts(query).build();
        esMetricsRepository.saveDBInfo(metric);
    }

    private String getServerIP() {
        try {
            return Utils.parseIp(monitorSettings.getDbhost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }
}
