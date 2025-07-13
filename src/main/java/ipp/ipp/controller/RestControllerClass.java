package ipp.ipp.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestControllerClass {

   

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/helloWorld", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMethod() throws Exception {
        String response="hello world";
        return ResponseEntity.ok(response);

     
    }


   


}

