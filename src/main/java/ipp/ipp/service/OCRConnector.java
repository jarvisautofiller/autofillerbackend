// Source code is decompiled from a .class file using FernFlower decompiler.
package ipp.ipp.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OCRConnector {
   private final WebClient webClient;

   public OCRConnector(@Value("${ocr.url}") String ocrURL) {
      System.out.println("OCR URL: " + ocrURL);
      this.webClient = WebClient.builder().baseUrl(ocrURL).build();
   }
   public String imageToText(MultipartFile image) throws IOException {
    ByteArrayResource resource = new ByteArrayResource(image.getBytes()) {
        @Override
        public String getFilename() {
            return image.getOriginalFilename(); // Needed for multipart headers
        }
    };

    MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
    formData.add("image", resource);

    String result = webClient.post()
        .uri("/ocr")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(formData))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    return result;
}

   
}
