package ipp.ipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ipp.ipp.client.GovernmentClient;
import ipp.ipp.client.ValidatorClient;
import ipp.ipp.model.User;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    GovernmentClient governmentService;

    @Autowired
    ValidatorClient validator;

    public ResponseEntity<User> getValidatedUserDetails(String id) {
        User user = governmentService.getUserDetails(id);
        logger.info("Retrieved user details for ID: {}", id);
        Boolean isValidUser = false;
        if (user == null) {
            logger.error("User not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        if (user.getPhoneNumber() != null) {
            isValidUser = validator.validatePhoneNumber(user.getPhoneNumber());
        }

        if (isValidUser) {
            return ResponseEntity.ok(user);
        } else {
            logger.error("Authenticated Failed: {}", id);
            return ResponseEntity.badRequest().body(null);
        }
    }

}
