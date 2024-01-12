package ch26reporting.application.controllers;

import ch26reporting.ReportingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ReportingApplication.class)
@AutoConfigureMockMvc
class DenormalizedLoyaltyReportControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test void index() throws Exception {
        mvc.perform(get("/denormalized-loyalty-report?start=2023-01-01&end=2024-12-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summaries").isNotEmpty())
                .andDo(print());
    }
}