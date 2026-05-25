package com.crimereporting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CrimeReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void reportCrimeSuccessfully() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("crimeType", "robbery");
        request.put("dateOfCrime", "01-15-2026");
        request.put("timeOfCrime", "14:30");
        request.put("description", "A robbery occurred at the downtown bank.");

        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.incidentIdentifier").exists())
                .andExpect(jsonPath("$.crimeType").value("robbery"))
                .andExpect(jsonPath("$.dateOfCrime").value("01-15-2026"))
                .andExpect(jsonPath("$.timeOfCrime").value("14:30"))
                .andExpect(jsonPath("$.description").value("A robbery occurred at the downtown bank."));
    }

    @Test
    void reportCrimeWithAllCrimeTypes() throws Exception {
        String[] crimeTypes = {"robbery", "assault", "theft", "scam"};
        for (String crimeType : crimeTypes) {
            Map<String, String> request = new LinkedHashMap<>();
            request.put("crimeType", crimeType);
            request.put("dateOfCrime", "06-20-2026");
            request.put("timeOfCrime", "08:00");
            request.put("description", "Incident of type " + crimeType);

            mockMvc.perform(post("/api/crimes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.crimeType").value(crimeType));
        }
    }

    @Test
    void rejectInvalidCrimeType() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("crimeType", "arson");
        request.put("dateOfCrime", "01-15-2026");
        request.put("timeOfCrime", "14:30");
        request.put("description", "Invalid crime type test.");

        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectInvalidDateFormat() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("crimeType", "theft");
        request.put("dateOfCrime", "2026-01-15");
        request.put("timeOfCrime", "14:30");
        request.put("description", "Wrong date format test.");

        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectInvalidTimeFormat() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("crimeType", "assault");
        request.put("dateOfCrime", "01-15-2026");
        request.put("timeOfCrime", "25:00");
        request.put("description", "Wrong time format test.");

        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectMissingDescription() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("crimeType", "scam");
        request.put("dateOfCrime", "01-15-2026");
        request.put("timeOfCrime", "14:30");

        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectEmptyBody() throws Exception {
        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void reportCrimeWithEdgeTimeValues() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("crimeType", "theft");
        request.put("dateOfCrime", "12-31-2025");
        request.put("timeOfCrime", "00:00");
        request.put("description", "Midnight incident.");

        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.timeOfCrime").value("00:00"));

        request.put("timeOfCrime", "23:59");
        request.put("description", "Late night incident.");

        mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.timeOfCrime").value("23:59"));
    }

    @Test
    void incidentIdentifierIsUnique() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("crimeType", "robbery");
        request.put("dateOfCrime", "01-15-2026");
        request.put("timeOfCrime", "14:30");
        request.put("description", "First incident.");

        String response1 = mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        request.put("description", "Second incident.");

        String response2 = mockMvc.perform(post("/api/crimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id1 = objectMapper.readTree(response1).get("incidentIdentifier").asText();
        String id2 = objectMapper.readTree(response2).get("incidentIdentifier").asText();

        org.junit.jupiter.api.Assertions.assertNotEquals(id1, id2);
    }
}
