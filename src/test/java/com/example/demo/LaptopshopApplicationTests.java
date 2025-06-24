package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopshopApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(true).isTrue(); // Kiểm tra context load thành công
    }

    @Test
    void testHelloEndpoint() {
        String response = restTemplate.getForObject("/hello", String.class);
        assertThat(response).contains("Hello from Spring Boot!"); // Kiểm tra nội dung trả về
    }
}