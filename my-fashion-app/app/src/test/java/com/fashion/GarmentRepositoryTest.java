package com.fashion;

import com.fashion.model.Garment;
import com.fashion.repository.GarmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class GarmentRepositoryTest {

    @Autowired
    private GarmentRepository repository;

    @Test
    public void testDynamicFilterGeneration() {
        // Prepare test data
        Garment g1 = new Garment();
        g1.setMaterial("Cotton");
        g1.setGarmentType("Shirt");
        repository.save(g1);

        Garment g2 = new Garment();
        g2.setMaterial("Denim");
        g2.setGarmentType("Pants");
        repository.save(g2);

        Garment g3 = new Garment();
        g3.setMaterial("Cotton"); // duplicate material
        g3.setGarmentType("Jacket");
        repository.save(g3);

        // Execute dynamic extraction query
        List<String> materials = repository.findDistinctMaterials();

        // Assert results are deduplicated and correctly extracted
        assertTrue(materials.contains("Cotton"));
        assertTrue(materials.contains("Denim"));
        assertTrue(materials.size() == 2, "Should return distinct materials only");
    }
}