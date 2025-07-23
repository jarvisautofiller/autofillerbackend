package com.autofiller.util;

import com.autofiller.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConversionUtilTest {

    @Test
    public void testAddUser() {
        User user = new User();
        user.setFirstName("John");

        User newUser = new User();
        newUser.setProfession("Engineer");
        newUser.setAccountNumber("1234567890");
        newUser.setIfscCode("IFSC001");
        newUser.setIncome("1000000");

        User result = ConversionUtil.addUser(user, newUser);

        assertEquals("Engineer", result.getProfession());
        assertEquals("1234567890", result.getAccountNumber());
        assertEquals("IFSC001", result.getIfscCode());
        assertEquals("1000000", result.getIncome());
    }

    @Test
    public void testConvertToUser_ValidJson() {
        String json = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"1234567890\" }";
        Mono<String> mono = Mono.just(json);

        User user = ConversionUtil.convertToUser(mono);
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("1234567890", user.getPhoneNumber());
    }


}
