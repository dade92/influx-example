package webapp.controllers;

import adapters.repository.InfluxDBService;
import adapters.repository.WriteDataRequest;
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
    private static final String WRITE_REQUEST = Fixtures.Companion.readJson("/writeRequest.json");

    @Test
    void writeData() throws Exception {
        WriteDataRequest expectedRequest = new WriteDataRequest("temperature", "reading", 50d);
        doNothing().when(influxDBService).writeData(expectedRequest);

        mockMvc.perform(post("/api/influx/write")
            .contentType(MediaType.APPLICATION_JSON)
            .content(WRITE_REQUEST)
        ).andExpect(status().isNoContent());

        verify(influxDBService).writeData(expectedRequest);
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