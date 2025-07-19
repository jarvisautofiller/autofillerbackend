// Source code is decompiled from a .class file using FernFlower decompiler.
package ipp.ipp.service;

import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class Extractor {
   public Extractor() {
   }

   public String extractIdFromString(String inputText) {
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
