package com.autofiller.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.util.Map;

public class RegexLoader {

    public Map<String, Map<String, String>> loadRegexConfig() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new ClassPathResource("regex.json").getInputStream();

        return objectMapper.readValue(inputStream, Map.class);
    }
}

