package com.fashion.model;

import java.util.List;

// The field names here must match the JSON keys you expect the large model to return
public record GarmentAnalysisDTO(
    String description,
    String garmentType,
    String style,
    String material,
    List<String> colorPalette,
    String pattern,
    String season,
    String occasion,
    LocationContext locationContext
) {
    public record LocationContext(String continent, String country, String city) {}
}