package com.autofiller.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.autofiller.model.User;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

@UtilityClass
public class ConversionUtil {

    private String validationMethod(Map<String, String> regexMap, List<String> items) {
        String aadhaarRaw = null;
        boolean hasGovTag = false, hasName = false, hasDOB = false;
        for (String text : items) {
            if (text.toLowerCase().contains(regexMap.get("contains1"))) {
                hasGovTag = true;
            }
            if (text.matches(regexMap.get("regexPattern1"))) {
                hasName = true;
            }
            if (text.matches(regexMap.get("regexPattern2"))) {
                hasDOB = true;
            }
            if (text.matches(regexMap.get("regexPattern3")) || text.matches(regexMap.get("regexPattern4"))) {
                aadhaarRaw = text.replaceAll("\s", "");
            }
        }

        if (hasGovTag && hasName && hasDOB  && aadhaarRaw != null) {
            return aadhaarRaw;
        } else {
            return null;
        }

    }

    public static String getIdFromString(String jsonInput) throws Exception {
        // Step 1: Extract strings from `"text":[...`] using regex
        Pattern arrayPattern = Pattern.compile("\"text\"\\s*:\\s*\\[(.*?)\\]");
        Matcher arrayMatcher = arrayPattern.matcher(jsonInput);
        List<String> items = new ArrayList<>();

        if (arrayMatcher.find()) {
            String content = arrayMatcher.group(1);
            // Split by "," but remove quotes and trim
            for (String item : content.split(",")) {
                items.add(item.replaceAll("\"", "").trim());
            }
        } else {
            return "Text array not found in input";
        }

        // Flags for Aadhaar format validation

        RegexLoader loader = new RegexLoader();
        Map<String, Map<String, String>> regexMap = loader.loadRegexConfig();

        for (Map.Entry<String, Map<String, String>> entry : regexMap.entrySet()) {
            String key = entry.getKey();
            Map<String, String> value = entry.getValue();

            String result = validationMethod(value, items);
            if (result != null) {
                return result; // ✅ This will return from the enclosing method
            }
        }


        return null;
    }

    public User convertToUser(Mono<String> responseMono) {
        ObjectMapper mapper = new ObjectMapper();
        String json = responseMono.block(); // Blocking call to get the JSON string
        try {
            return mapper.readValue(json, User.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    public User addUser(User user, User newUser) {

        if (newUser.getProfession() != null) {
            user.setProfession(newUser.getProfession());
        }
        if (newUser.getAccountNumber() != null) {
            user.setAccountNumber(newUser.getAccountNumber());
        }
        if (newUser.getIfscCode() != null) {
            user.setIfscCode(newUser.getIfscCode());
        }
        if (newUser.getIncome() != null) {
            user.setIncome(newUser.getIncome());
        }
        return user;

    }

}
