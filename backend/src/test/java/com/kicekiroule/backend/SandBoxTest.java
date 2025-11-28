package com.kicekiroule.backend;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SandBoxTest {

    @Autowired
    private GoogleSheetService googleSheetService;

    @Test
    void testProperties() {
        System.out.println(googleSheetService.getApiKey());
        System.out.println(googleSheetService.getSpreadsheetId());
        try {
            System.out.println(googleSheetService.getSheetData());
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
}
