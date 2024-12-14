package adapters.configuration;

import adapters.repository.InfluxDBService;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(InfluxDBProperties.class)
@Configuration
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

    @Bean
    public InfluxDBClient influxDBClient(
        InfluxDBProperties influxDBProperties
    ) {
        return InfluxDBClientFactory.create(
            influxDBProperties.url,
            influxDBProperties.token.toCharArray(),
            influxDBProperties.org
        );
    }


}
