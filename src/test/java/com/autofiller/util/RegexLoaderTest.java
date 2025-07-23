package com.autofiller.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RegexLoaderTest {

    @Test
    public void testLoadRegexConfig_success() throws Exception {
        // Arrange
        RegexLoader regexLoader = new RegexLoader();

        // Act
        Map<String, Map<String, String>> regexConfig = regexLoader.loadRegexConfig();

        // Assert
        assertNotNull(regexConfig, "Regex config should not be null");
        assertFalse(regexConfig.isEmpty(), "Regex config should not be empty");

        // Example validation (assuming "aadhaar" exists in regex.json)
        assertTrue(regexConfig.containsKey("aadhaar"), "Should contain key 'aadhaar'");
        Map<String, String> aadhaarMap = regexConfig.get("aadhaar");

        assertEquals("government of india", aadhaarMap.get("contains1"));
        assertTrue(aadhaarMap.get("regexPattern3").matches("\\\\d\\{4}\\\\s\\\\d\\{4}\\\\s\\\\d\\{4}"));
    }
}
