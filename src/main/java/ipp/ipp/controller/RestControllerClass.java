package ipp.ipp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ipp.ipp.domain.User;
import ipp.ipp.service.Extractor;
import ipp.ipp.service.OCRConnector;

@RestController
public class RestControllerClass {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerClass.class);

    

    /**
     * This class is a REST controller that handles HTTP requests.
     * It provides endpoints for testing, user details, and document upload.
     */


    @Autowired
    OCRConnector ocrConnector;

    @Autowired
    Extractor extractor;;


    @CrossOrigin(origins = "*")
    @GetMapping(value = "/helloWorld", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMethod() throws Exception {
        String response="hello world 2.0";
        return ResponseEntity.ok(response);

    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getDetails() throws Exception {
        User user = new User();
        user.setFirstName("Jarvis");
        user.setLastName("Doe");
        user.setPhoneNumber("123-456-7890");
        user.setAge(30);
        user.setAddress("123 Main St, Springfield");
        user.setEmail("jarvis@gmail.com");

        return ResponseEntity.ok(user);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam("image") MultipartFile image) throws Exception {

        String imageText = ocrConnector.imageToText(image);
        logger.info("Extracted text from image: {}", imageText);
        String number = extractor.extractIdFromString(imageText);
        logger.info("Extracted number from text: {}", number);


    
       return ResponseEntity.ok(number);
    }
}

