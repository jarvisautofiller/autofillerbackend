package com.autofiller.controller;

import com.autofiller.model.User;
import com.autofiller.service.Extractor;
import com.autofiller.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestControllerClass.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestControllerClassTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Extractor extractor;

    @MockBean
    private UserService userService;

    @Test
    public void testHelloWorldEndpoint() throws Exception {
        mockMvc.perform(get("/helloWorld"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world 2.0"));
    }

    @Test
    public void testGetDetailsEndpoint() throws Exception {
        String id = "123";

        User mockUser = new User();
        mockUser.setDob("2000-01-01");
        mockUser.setEmail("test@example.com");

        Mockito.when(userService.getValidatedUserDetails(id))
                .thenReturn(ResponseEntity.ok(mockUser));

        mockMvc.perform(get("/id").param("id", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.age").value(mockUser.getAge()));
    }

    @Test
    public void testUploadDocument_Valid() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "dummy".getBytes());

        Mockito.when(extractor.imageToText(Mockito.any())).thenReturn("1234 5678 9012");

        mockMvc.perform(multipart("/document").file(mockFile))
                .andExpect(status().isOk())
                .andExpect(content().string("1234 5678 9012"));
    }

    @Test
    public void testUploadDocument_Invalid() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "dummy".getBytes());

        Mockito.when(extractor.imageToText(Mockito.any())).thenReturn("");

        mockMvc.perform(multipart("/document").file(mockFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No valid ID found in the document."));
    }
}
