// Source code is decompiled from a .class file using FernFlower decompiler.
package ipp.ipp.service;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ipp.ipp.client.OCRClient;
import ipp.ipp.util.ConversionUtil;

@Service
public class Extractor {

   private static final Logger logger = LoggerFactory.getLogger(Extractor.class);


   @Autowired
   OCRClient ocrConnector;

   public Extractor() {
   }

   

   public String imageToText(MultipartFile image) throws IOException {

      String imageText = ocrConnector.imageToText(image);
      logger.info("Extracted text from image: {}", imageText);
      String number = ConversionUtil.getIdFromString(imageText);
   
      logger.info("Extracted number from text: {}", number);
      // TODO Auto-generated method stub
      return number;
   }
}
