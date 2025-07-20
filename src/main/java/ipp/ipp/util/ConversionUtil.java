package ipp.ipp.util;

import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;
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
    
}
