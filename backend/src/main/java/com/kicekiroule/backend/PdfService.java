package com.kicekiroule.backend;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generatePdfFromData(List<List<Object>> data) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Titre
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Rapport Synthèse Données", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            if (data == null || data.isEmpty()) {
                document.add(new Paragraph("Aucune donnée trouvée dans le Google Sheet."));
            } else {
                // Création dynamique du tableau
                // On assume que la première ligne détermine le nombre de colonnes
                int maxCol = data.get(0).size();
                PdfPTable table = new PdfPTable(maxCol);
                table.setWidthPercentage(100);

                for (List<Object> row : data) {
                    // On remplit les cellules. Si une ligne est plus courte, on complète
                    for (int i = 0; i < maxCol; i++) {
                        String cellText = (i < row.size()) ? String.valueOf(row.get(i)) : "";
                        table.addCell(new Phrase(cellText, FontFactory.getFont(FontFactory.HELVETICA, 10)));
                    }
                }
                document.add(table);
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du PDF", e);
        }
    }
}