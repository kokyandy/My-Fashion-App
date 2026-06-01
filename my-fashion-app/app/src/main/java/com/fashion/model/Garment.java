package com.fashion.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Garment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String imageUrl; // Local image path, e.g. "/uploads/img1.jpg"
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String garmentType;
    private String style;
    private String material;
    private String season;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGarmentType() {
        return this.garmentType;
    }

    public void setGarmentType(String garmentType) {
        this.garmentType = garmentType;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMaterial() {
        return this.material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<String> getColorPalette() {
        return this.colorPalette;
    }

    public void setColorPalette(List<String> colorPalette) {
        this.colorPalette = colorPalette;
    }

    public String getDesignerNotes() {
        return this.designerNotes;
    }

    public void setDesignerNotes(String designerNotes) {
        this.designerNotes = designerNotes;
    }

    public String getCustomTags() {
        return this.customTags;
    }

    public void setCustomTags(String customTags) {
        this.customTags = customTags;
    }
    
    @ElementCollection
    private List<String> colorPalette; // Store as a join table or serialize to JSON
    
    // Designer custom annotations
    @Column(columnDefinition = "TEXT")
    private String designerNotes;
    private String customTags;
}