package com.kicekiroule.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simule le navigateur

    @MockBean
    private GoogleSheetService googleSheetService; // Faux service Google

    @MockBean
    private PdfService pdfService; // Faux service PDF

    @Test
    void testGenerateReport_Success() throws Exception {
        // 1. Définir le comportement des Mocks
        // Quand on appelle getSheetData, on renvoie une liste vide
        when(googleSheetService.getSheetData()).thenReturn(Collections.emptyList());
        
        // Quand on appelle generatePdf, on renvoie un faux tableau d'octets
        byte[] fakePdf = new byte[]{1, 2, 3};
        when(pdfService.generatePdfFromData(Collections.emptyList())).thenReturn(fakePdf);

        // 2. Simuler l'appel HTTP POST
        mockMvc.perform(post("/api/generate-pdf")
                .param("sheetId", "test-sheet-id"))
                .andExpect(status().isOk()) // Vérifie code 200
                .andExpect(content().contentType(MediaType.APPLICATION_PDF)) // Vérifie le type MIME
                .andExpect(header().string("Content-Disposition", "attachment; filename=rapport.pdf"));
    }
}