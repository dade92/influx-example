package webapp.controllers;

import adapters.repository.InfluxDBService;
import domain.measure.Measure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import utils.Fixtures;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InfluxDBController.class)
class InfluxDBControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InfluxDBService influxDBService;

    private static final String QUERY_RESPONSE = Fixtures.Companion.readJson("/queryResponse.json");

    @Test
    void writeData() throws Exception {
        doNothing().when(influxDBService).writeData("temperature", "reading", 11d);

        mockMvc.perform(post("/api/influx/write?measurement=temperature&field=reading&value=11")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(influxDBService).writeData("temperature", "reading", 11d);
    }

    @Test
    void readData() throws Exception {
        when(influxDBService.queryData("temperature", "reading", 24))
            .thenReturn(Arrays.asList(
                    new Measure(
                        LocalDateTime.of(2024, 12, 15, 10, 0, 0).toInstant(ZoneOffset.UTC),
                        "reading",
                        11d
                    ),
                    new Measure(
                        LocalDateTime.of(2024, 12, 16, 10, 0, 0).toInstant(ZoneOffset.UTC),
                        "reading",
                        12d
                    )
                )
            );

        mockMvc.perform(get("/api/influx/query?measurement=temperature&field=reading&timespan=24")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(QUERY_RESPONSE));
    }
}