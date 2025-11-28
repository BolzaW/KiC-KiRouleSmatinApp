package com.kicekiroule.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class PdfServiceTest {

    private final PdfService pdfService = new PdfService();

    @Test
    void testGeneratePdf_WithData() {
        // 1. Préparer des données fictives
        List<Object> row1 = Arrays.asList("Nom", "Prénom");
        List<Object> row2 = Arrays.asList("Doe", "John");
        List<List<Object>> data = Arrays.asList(row1, row2);

        // 2. Exécuter
        byte[] result = pdfService.generatePdfFromData(data);

        // 3. Vérifier
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.length > 0);
        
        // Petite astuce : tous les PDF commencent par "%PDF"
        String fileHeader = new String(Arrays.copyOfRange(result, 0, 4));
        Assertions.assertEquals("%PDF", fileHeader);
    }

    @Test
    void testGeneratePdf_Empty() {
        byte[] result = pdfService.generatePdfFromData(Collections.emptyList());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.length > 0);
    }
}