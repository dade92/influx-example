package webapp.controllers;

import adapters.repository.InfluxDBService;
import com.influxdb.query.FluxTable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/influx")
public class InfluxDBController {

    private final InfluxDBService influxDBService;

    public InfluxDBController(InfluxDBService influxDBService) {
        this.influxDBService = influxDBService;
    }

    @PostMapping("/write")
    public void writeData(
        @RequestParam String measurement,
        @RequestParam String field,
        @RequestParam Double value
    ) {
        influxDBService.writeData(measurement, field, value);
    }

    @GetMapping("/query")
    public List<FluxTable> queryData(
        @RequestParam String measurement,
        @RequestParam String field
    ) {
        return influxDBService.queryData(measurement, field);
    }
}
