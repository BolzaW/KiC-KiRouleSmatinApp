package com.kicekiroule.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private GoogleSheetService sheetService;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReport() {
        try {
            // 1. Récupérer les données du Sheet
            List<List<Object>> data = sheetService.getSheetData();

            // 2. Générer le PDF
            byte[] pdfBytes = pdfService.generatePdfFromData(data);

            // 3. Renvoyer le fichier binaire au navigateur
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}