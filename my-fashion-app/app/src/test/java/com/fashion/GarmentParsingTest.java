package com.fashion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fashion.model.GarmentAnalysisDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GarmentParsingTest {

    @Test
    public void testParseModelOutputToStructuredAttributes() throws Exception {
        // Simulated JSON string returned by the large model
        String aiJsonResponse = """
            {
              "description": "A stylish vintage denim jacket.",
              "garmentType": "Jacket",
              "material": "Denim",
              "season": "Autumn",
              "locationContext": {
                "continent": "North America",
                "country": "USA",
                "city": "New York"
              }
            }
            """;

        ObjectMapper mapper = new ObjectMapper();
        GarmentAnalysisDTO dto = mapper.readValue(aiJsonResponse, GarmentAnalysisDTO.class);

        // Assert parsed results
        assertEquals("Jacket", dto.garmentType());
        assertEquals("Denim", dto.material());
        assertEquals("New York", dto.locationContext().city());
        assertNull(dto.pattern(), "Missing fields should be silently handled as null");
    }
}