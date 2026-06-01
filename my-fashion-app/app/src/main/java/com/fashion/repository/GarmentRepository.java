package com.fashion.repository;

import com.fashion.model.Garment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarmentRepository extends JpaRepository<Garment, Long> {
    
    // Used to dynamically generate the "material" filter menu for the frontend
    @Query("SELECT DISTINCT g.material FROM Garment g WHERE g.material IS NOT NULL")
    List<String> findDistinctMaterials();

    // Used to dynamically generate the "garment type" filter menu for the frontend
    @Query("SELECT DISTINCT g.garmentType FROM Garment g WHERE g.garmentType IS NOT NULL")
    List<String> findDistinctGarmentTypes();
    
    // Basic fuzzy search (matches AI description or designer notes)
    List<Garment> findByDescriptionContainingIgnoreCaseOrDesignerNotesContainingIgnoreCase(String keyword1, String keyword2);
}