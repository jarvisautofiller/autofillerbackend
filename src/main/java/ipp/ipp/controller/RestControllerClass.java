package ipp.ipp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ipp.ipp.model.User;
import ipp.ipp.service.Extractor;
import ipp.ipp.service.UserService;

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

        return ResponseEntity.ok(number);
    }
}
