package webapp.controllers;

import adapters.repository.InfluxDBService;
import adapters.repository.WriteDataRequest;
import domain.measure.Measure;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> writeData(
        @RequestBody WriteDataJsonRequest request
    ) {
        try {
            influxDBService.writeData(request.toRequest());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/query")
    public ResponseEntity<MeasuresResponse> queryData(
        @RequestParam String measurement,
        @RequestParam String field,
        @RequestParam String timespan
    ) {
        return ResponseEntity.ok(
            new MeasuresResponse(
                influxDBService.queryData(measurement, field, Integer.parseInt(timespan))
            )
        );
    }
}

record MeasuresResponse(List<Measure> measures) {
}

record WriteDataJsonRequest(
    String measurement,
    String field,
    Double value
) {
    public WriteDataRequest toRequest() {
        return new WriteDataRequest(
            this.measurement,
            this.field,
            this.value
        );
    }
}