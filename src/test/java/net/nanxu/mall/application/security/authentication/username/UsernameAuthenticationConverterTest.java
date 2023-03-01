package net.nanxu.mall.application.security.authentication.username;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
class UsernameAuthenticationConverterTest {

    @Mock
    HttpServletRequest request;

    @BeforeEach
    public void setup() throws IOException {
        byte[] bytes = """
                {
                  "username": "admin",
                  "password": "Hello"
                }
                """.getBytes(StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        Mockito.when(request.getReader()).thenReturn(reader);
    }

    @Test
    public void convertTest() {
        AuthenticationConverter converter = new UsernameAuthenticationConverter();
        Authentication authentication = converter.convert(request);
        Assertions.assertEquals("admin", authentication.getPrincipal());
    }
}