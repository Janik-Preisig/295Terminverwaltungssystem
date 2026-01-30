package com.example.terminsystem.controller; // Dein Paketname

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Diese beiden statischen Imports sind extrem wichtig für die Lesbarkeit (post, status, etc.)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectShortCode() throws Exception {
        // Negativ-Test: Code ist nur 3-stellig
        String json = "{\"title\":\"Friseur\", \"accessCode\":\"123\"}";

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest()); // Erwartet Fehler 400
    }
}
