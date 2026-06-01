package com.fashion.controller;

import com.fashion.model.Garment;
import com.fashion.model.GarmentAnalysisDTO;
import com.fashion.repository.GarmentRepository;
import com.fashion.service.AiClassificationService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/garments")
public class GarmentController {

    private final AiClassificationService aiService;
    private final GarmentRepository repository;
    private final String UPLOAD_DIR = "uploads/";

    public GarmentController(AiClassificationService aiService, GarmentRepository repository) {
        this.aiService = aiService;
        this.repository = repository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Garment> uploadAndClassify(@RequestParam("file") MultipartFile file) throws IOException {
        // 1. Save the image to the local uploads directory (simplified assumption)
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + filename);
        Files.copy(file.getInputStream(), filePath);

        // 2. Call AI to perform image classification and structured analysis
        ByteArrayResource imageResource = new ByteArrayResource(file.getBytes());
        GarmentAnalysisDTO aiResult = aiService.classifyGarment(imageResource);

        // 3. Persist the AI result and image path to the database
        Garment garment = new Garment();
        garment.setImageUrl("/" + UPLOAD_DIR + filename);
        garment.setDescription(aiResult.description());
        garment.setGarmentType(aiResult.garmentType());
        garment.setStyle(aiResult.style());
        garment.setMaterial(aiResult.material());
        garment.setSeason(aiResult.season());

        Garment savedGarment = repository.save(garment);
        return ResponseEntity.ok(savedGarment);
    }

    @GetMapping
    public List<Garment> getAllGarments() {
        return repository.findAll();
    }

    // 1. Full-text / fuzzy search endpoint
    @GetMapping("/search")
    public List<Garment> searchGarments(@RequestParam("keyword") String keyword) {
        return repository.findByDescriptionContainingIgnoreCaseOrDesignerNotesContainingIgnoreCase(keyword, keyword);
    }

    // 2. Get dynamically generated "garment type" filter list
    @GetMapping("/filters/types")
    public List<String> getTypeFilters() {
        return repository.findDistinctGarmentTypes();
    }

    // 3. Get dynamically generated "material" filter list
    @GetMapping("/filters/materials")
    public List<String> getMaterialFilters() {
        return repository.findDistinctMaterials();
    }

    // 4. Update designer custom annotation data (PUT contract)
    @PutMapping("/{id}/annotations")
    public ResponseEntity<Garment> updateAnnotations(
            @PathVariable("id") long id,
            @RequestBody Garment annotationRequest) {
        
        return repository.findById(id).map(garment -> {
            garment.setCustomTags(annotationRequest.getCustomTags());
            garment.setDesignerNotes(annotationRequest.getDesignerNotes());
            Garment updated = repository.save(garment);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}