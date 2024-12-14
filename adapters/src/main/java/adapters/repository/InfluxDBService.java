package adapters.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class InfluxDBService {

    private final InfluxDBClient influxDBClient;

    private final String bucket;

    private final String org;

    public static final String QUERY_TEMPLATE = """
        from(bucket: ":bucket")
        |> range(start: -1h)
        |> filter(fn: (r) => r._measurement == ":measurement")
        |> filter(fn: (r) => r._field == ":field")
        """;

    public InfluxDBService(InfluxDBClient influxDBClient, String bucket, String org) {
        this.influxDBClient = influxDBClient;
        this.bucket = bucket;
        this.org = org;
    }

    public void writeData(String measurement, String field, Double value) {
        // Create a point without specifying precision explicitly
        Point point = Point
            .measurement(measurement)
            .addField(field, value)
            .time(Instant.now(), WritePrecision.NS);

        // Write the point to InfluxDB
        influxDBClient.getWriteApiBlocking().writePoint(bucket, org, point);
    }

    public List<Measure> queryData(String measurement, String field) {
        String query = QUERY_TEMPLATE
            .replace(":bucket", bucket)
            .replace(":measurement", measurement)
            .replace(":field", field);

        List<FluxTable> tables = influxDBClient.getQueryApi().query(query, org);

        List<Measure> results = new ArrayList<>();
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                Instant time = record.getTime();
                String outputField = record.getField();
                Object value = record.getValue();

                results.add(new Measure(time, outputField, value));
            }
        }

        return results;
    }
}
