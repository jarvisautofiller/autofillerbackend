package ipp.ipp.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ipp.ipp.domain.User;

@RestController
public class RestControllerClass {


    @CrossOrigin(origins = "*")
    @GetMapping(value = "/helloWorld", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMethod() throws Exception {
        String response="hello world";
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
       return ResponseEntity.ok("12345");
    }
}

