package ipp.ipp.util;

import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import ipp.ipp.model.User;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;
@UtilityClass
public class ConversionUtil {


    public String getIdFromString(String inputText) {
        Pattern aadhaarPattern = Pattern.compile("^\\d{12}$");
        inputText = inputText.replaceAll("[\\[\\]{}\"]", "");
        String[] tokens = inputText.split(",");
        String[] var4 = tokens;
        int var5 = tokens.length;
  
        for(int var6 = 0; var6 < var5; ++var6) {
           String token = var4[var6];
           String digitsOnly = token.replaceAll("\\D", "");
           if (aadhaarPattern.matcher(digitsOnly).matches()) {
              return digitsOnly;
           }
        }
  
        return "No valid Aadhaar number found";
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
     
    
}
