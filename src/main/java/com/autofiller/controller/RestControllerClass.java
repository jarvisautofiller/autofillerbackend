package com.autofiller.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.autofiller.model.User;
import com.autofiller.service.Extractor;
import com.autofiller.service.UserService;

@RestController
public class RestControllerClass {

    

    /**
     * This class is a REST controller that handles HTTP requests.
     * It provides endpoints for testing, user details, and document upload.
     */

    @Autowired
    Extractor extractor;


    @Autowired
    UserService userService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/helloWorld", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMethod() throws Exception {
        String response = "hello world 2.0";
        return ResponseEntity.ok(response);

    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getDetails(@RequestParam("id") String id) throws Exception {

        return userService.getValidatedUserDetails(id);

        

    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam("image") MultipartFile image) throws Exception {

        String number = extractor.imageToText(image);
        if(number == null || number.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid ID found in the document.");
        }

        return ResponseEntity.ok(number);
    }
}
