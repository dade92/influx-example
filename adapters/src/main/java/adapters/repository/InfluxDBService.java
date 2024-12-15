package adapters.repository;

import adapters.utils.InfluxDBDateFormatter;
import adapters.utils.InstantProvider;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import domain.measure.Measure;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InfluxDBService {

    public static final String QUERY_TEMPLATE = """
        from(bucket: ":bucket")
        |> range(start: :startTime, stop: :endTime)
        |> filter(fn: (r) => r._measurement == ":measurement")
        |> filter(fn: (r) => r._field == ":field")
        """;

    private final InfluxDBClient influxDBClient;
    private final InstantProvider instantProvider;
    private final InfluxDBDateFormatter influxDBDateFormatter;
    private final String bucket;
    private final String org;

    public InfluxDBService(
        InfluxDBClient influxDBClient,
        InstantProvider instantProvider,
        InfluxDBDateFormatter influxDBDateFormatter,
        String bucket,
        String org
    ) {
        this.influxDBClient = influxDBClient;
        this.instantProvider = instantProvider;
        this.influxDBDateFormatter = influxDBDateFormatter;
        this.bucket = bucket;
        this.org = org;
    }

    public void writeData(WriteDataRequest request) {
        Point point = Point
            .measurement(request.measurement())
            .addField(request.field(), request.value())
            .time(instantProvider.get(), WritePrecision.S);

        influxDBClient.getWriteApiBlocking().writePoint(bucket, org, point);
    }

    public List<Measure> queryData(String measurement, String field, int timespan) {
        Instant now = instantProvider.get();
        String query = QUERY_TEMPLATE
            .replace(":bucket", bucket)
            .replace(":measurement", measurement)
            .replace(":startTime", influxDBDateFormatter.format(now.minus(timespan, ChronoUnit.HOURS)))
            .replace(":endTime", influxDBDateFormatter.format(now))
            .replace(":field", field);

        System.out.println(query);

        List<FluxTable> tables = influxDBClient.getQueryApi().query(query, org);

        List<Measure> results = new ArrayList<>();
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                Instant time = record.getTime();
                String outputField = record.getField();
                Double value = (Double) record.getValue();

                results.add(new Measure(time, outputField, value));
            }
        }

        return results;
    }
}
