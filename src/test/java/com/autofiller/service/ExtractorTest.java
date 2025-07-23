package com.autofiller.service;

import com.autofiller.client.OCRClient;
import com.autofiller.util.ConversionUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExtractorTest {

    @Autowired
    private Extractor extractor;

    @MockBean
    private OCRClient ocrConnector;

    @Test
    public void testImageToText() throws Exception {
        // Arrange
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image".getBytes());

        String mockExtractedText = "My Aadhaar Number is 1234 5678 9012";
        String mockExtractedId = "1234 5678 9012";

        // Mock OCRClient response
        Mockito.when(ocrConnector.imageToText(mockFile)).thenReturn(mockExtractedText);

        // Mock static method ConversionUtil.getIdFromString
        try (MockedStatic<ConversionUtil> utilities = Mockito.mockStatic(ConversionUtil.class)) {
            utilities.when(() -> ConversionUtil.getIdFromString(mockExtractedText))
                     .thenReturn(mockExtractedId);

            // Act
            String result = extractor.imageToText(mockFile);

            // Assert
            assertEquals(mockExtractedId, result);
        }
    }
}
