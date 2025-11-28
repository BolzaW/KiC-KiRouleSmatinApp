package com.kicekiroule.backend;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetService {

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.sheet.id}")
    private String spreadsheetId;

    private static final String APPLICATION_NAME = "KiCeKiRouleParser";

    public List<List<Object>> getSheetData() throws GeneralSecurityException, IOException {
        // Initialisation du transport HTTP et du parser JSON
        Sheets service = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                null)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Appel à l'API Google : On récupère toutes les colonnes (A à AX)
        // La clé API est passée via .setKey()
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, "A:AX")
                .setKey(apiKey) 
                .execute();

        return response.getValues();
    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public String getApiKey() {
        return apiKey;
    }
}