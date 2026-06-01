package com.fashion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GarmentControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadClassifyAndFilterEndToEnd() throws Exception {
        // 1. Simulate image upload and classification
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "dummy image content".getBytes()
        );

        mockMvc.perform(multipart("/api/garments/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.garmentType").exists());

        // 2. Verify that the uploaded image and dynamically generated filters can be retrieved via the API
        mockMvc.perform(get("/api/garments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].imageUrl").exists());

        mockMvc.perform(get("/api/garments/filters/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}