package com.autofiller.service;

import com.autofiller.client.GovernmentClient;
import com.autofiller.client.LbgClient;
import com.autofiller.client.ValidatorClient;
import com.autofiller.model.User;
import com.autofiller.util.ConversionUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private GovernmentClient governmentService;

    @Mock
    private ValidatorClient validator;

    @Mock
    private LbgClient lbgClient;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); // Not needed with @ExtendWith(MockitoExtension.class)
    }

    @Test
    public void testGetValidatedUserDetails_ValidUser() {
        String id = "12345";

        // Mock input and expected output
        User baseUser = new User();
        baseUser.setFirstName("John");
        baseUser.setLastName("Doe");
        baseUser.setPhoneNumber("9876543210");
        baseUser.setDob("2000-01-01");

        User lbgUser = new User();
        lbgUser.setEmail("john.doe@example.com");

        User combinedUser = baseUser;
        combinedUser.setEmail("john.doe@example.com");

        // Mock external service responses
        when(governmentService.getUserDetails(eq(id), eq("Aadhar")))
                .thenReturn(Mono.just("{\"phoneNumber\":\"9876543210\", \"dob\":\"2000-01-01\"}"));

        when(lbgClient.getLbgDetails(eq("9876543210")))
                .thenReturn(Mono.just("{\"email\":\"john.doe@example.com\"}"));

        when(validator.validatePhoneNumber("+919876543210")).thenReturn(true);

        // Mock static methods of ConversionUtil
        try (var mockedConversionUtil = Mockito.mockStatic(ConversionUtil.class)) {
            mockedConversionUtil.when(() -> ConversionUtil.convertToUser(any(Mono.class)))
                    .thenReturn(baseUser)
                    .thenReturn(lbgUser);

            mockedConversionUtil.when(() -> ConversionUtil.addUser(eq(baseUser), eq(lbgUser)))
                    .thenReturn(combinedUser);

            // Act
            ResponseEntity<User> response = userService.getValidatedUserDetails(id);

            // Assert
            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
            assertEquals("9876543210", response.getBody().getPhoneNumber());
            assertEquals("john.doe@example.com", response.getBody().getEmail());
        }
    }
}
