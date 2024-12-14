package adapters.configuration;

import adapters.repository.InfluxDBService;
import com.influxdb.client.InfluxDBClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBConfiguration {

    @Bean
    public InfluxDBService influxDBService(
        InfluxDBProperties influxDBProperties,
        InfluxDBClient client
    ) {
        return new InfluxDBService(
            client,
            influxDBProperties.bucket,
            influxDBProperties.org
        );
    }



}
