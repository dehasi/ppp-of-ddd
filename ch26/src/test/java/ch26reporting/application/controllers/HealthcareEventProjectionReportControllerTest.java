package ch26reporting.application.controllers;

import ch26reporting.ReportingApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;

import static ch26reporting.application.controllers.InsertTestData.diagnosisEvents;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ReportingApplication.class)
@AutoConfigureMockMvc
class HealthcareEventProjectionReportControllerTest {
    @Autowired
    private MockMvc mvc;
    private static final RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(ClientHttpResponse response) throws IOException {
                    //do nothing
                }
            })
            .build();

    private static final int port = 2113;


    @BeforeEach
    void setUpEventStore() throws JsonProcessingException {
        startProjection("$by_category");
        startProjection("$by_event_type");
        startProjection("$stream_by_category");


        createProjection(new Projection("DiagnosesByMonth", """
                fromStream('diagnoses')
                    .when({
                        "diagnosis" : function(state, ev) {
                             var date = ev.data.Date.replace('/', '');
                             var diagnosisId = ev.data.DiagnosisId;
                             linkTo('diagnoses-' + diagnosisId + '_' + date, ev);
                        }
                    });
                """));

        createProjection(new Projection("DiagnosesByMonthCounts", """
                fromCategory('diagnoses')
                .foreachStream()
                .when({
                    $init : function(s,e) {return {count : 0}},
                    "diagnosis" : function(s,e) { s.count += 1}
                });
                """));

        createProjection(new Projection("Months", """
                fromStream('diagnoses')
                .when({
                    "diagnosis": function(state, ev) {
                        var date = ev.data.Date.replace('/', '');
                        linkTo('month-' + date, ev);
                    }
                });
                """));

        createProjection(new Projection("MonthsCounts", """
                    fromCategory('month')
                    .foreachStream()
                    .when({
                        $init : function(s,e) {return {count : 0}},
                        "diagnosis" : function(s,e) { s.count += 1 }
                    });
                """));

        insertData();
    }

    @Test void index() throws Exception {
        mvc.perform(get("/healthcare-projection-report?start=2014-01-02&end=2014-04-01&ids=dg1,dg2,dg3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summaries").isNotEmpty())
                .andExpect(jsonPath("$.summaries[*].Amount",hasItem(2)))
                .andDo(print());
    }

    static void startProjection(String id) {
        try (HttpClient httpClient = HttpClient.newHttpClient();) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("http://localhost:%s/projection/%s/command/enable", port, id)))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(10))
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void createProjection(Projection projection) {
        String url = "http://localhost:" + port +
                     "/projections/continuous" +
                     "?name=" + projection.name() +
                     "&type=js" +
                     "&enabled=true" +
                     "&emit=true" +
                     "&trackemittedstreams=true";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentLength(projection.jsCode().length());
        HttpEntity<String> entity = new HttpEntity<>(projection.jsCode(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, POST, entity, String.class);

        int code = response.getStatusCode().value();
        if (code != 201 && code != 409)
            throw new IllegalStateException("Expected response 201 or 409, but was" + code);
    }

    private static void insertData() throws JsonProcessingException {
        String body = new ObjectMapper().writeValueAsString(diagnosisEvents);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.eventstore.events+json");
        headers.set("Accept", "*/*");
        headers.setContentLength(body.length());
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        final var url = "http://localhost:" + port + "/streams/diagnoses";
        ResponseEntity<String> response = restTemplate.exchange(url, POST, entity, String.class);
        int code = response.getStatusCode().value();
        if (code != 201 && code != 409)
            throw new IllegalStateException("Expected response 201 or 409, but was: " + code);
    }

    record Projection(
            String name,
            String jsCode
    ) {}
}
