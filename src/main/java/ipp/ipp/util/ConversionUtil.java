package ipp.ipp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import ipp.ipp.model.User;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

@UtilityClass
public class ConversionUtil {

    public static String getIdFromString(String jsonInput) {
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
        boolean hasGovTag = false, hasName = false, hasDOB = false, hasGender = false;
        String aadhaarRaw = null;

        for (String text : items) {
            if (text.toLowerCase().contains("government of india")) {
                hasGovTag = true;
            }
            if (text.matches("^[A-Z][a-zA-Z ]+$")) {
                hasName = true;
            }
            if (text.matches(".*DOB[:]?\\s?\\d{2}/\\d{2}/\\d{4}.*")) {
                hasDOB = true;
            }
            if (text.toLowerCase().contains("male") || text.toLowerCase().contains("female")) {
                hasGender = true;
            }
            if (text.matches("\\d{4}\\s\\d{4}\\s\\d{4}") || text.matches("\\d{12}")) {
                aadhaarRaw = text.replaceAll("\\s", "");
            }
        }

        if (hasGovTag && hasName && hasDOB && hasGender && aadhaarRaw != null) {
            return aadhaarRaw;
        } else {
            return null;
        }
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

        if(newUser.getProfession() != null) {
            user.setProfession(newUser.getProfession());
        }
        if(newUser.getAccountNumber() != null) {
            user.setAccountNumber(newUser.getAccountNumber());
        }
        if(newUser.getIfscCode() != null) {
            user.setIfscCode(newUser.getIfscCode());
        }
        if(newUser.getIncome() != null) {
            user.setIncome(newUser.getIncome());
        }
        return user;

    }




    

}
