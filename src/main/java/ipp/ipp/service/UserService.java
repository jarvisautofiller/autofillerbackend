package ipp.ipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ipp.ipp.client.GovernmentClient;
import ipp.ipp.client.LbgClient;
import ipp.ipp.client.ValidatorClient;
import ipp.ipp.model.User;
import ipp.ipp.util.ConversionUtil;
import reactor.core.publisher.Mono;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    GovernmentClient governmentService;

    @Autowired
    ValidatorClient validator;

    @Autowired
    LbgClient lbgClient;

    public ResponseEntity<User> getValidatedUserDetails(String id) {
        
        Mono<String> userString = governmentService.getUserDetails(id,"Aadhar");
        User user = ConversionUtil.convertToUser(userString);
        logger.info("Retrieved user details for ID: {}", id);

        Mono<String> lbgDetails = lbgClient.getLbgDetails(user.getPhoneNumber());
        logger.info("Retrieved LBG details for phone number: {}", user.getPhoneNumber());
        user = ConversionUtil.addUser(user, ConversionUtil.convertToUser(lbgDetails));

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
