package com.macro.mall.tiny.modules.ums.controller;

import com.macro.mall.tiny.modules.ums.dto.UmsAdminParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UmsAdminControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + ":" + port + "/admin";
    }

    @Test
    public void register_SuccessfulRegistration_ReturnsSuccess() {
        String url = baseUrl + "/register";
        UmsAdminParam adminParam = new UmsAdminParam();
        adminParam.setUsername("integrationUser");
        adminParam.setPassword("password123");

        HttpEntity<UmsAdminParam> requestEntity = new HttpEntity<>(adminParam);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("\"code\":200"));
    }
}
