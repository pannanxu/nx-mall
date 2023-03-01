package net.nanxu.mall.application.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtBearerTokenResolverTest {

    JwtBearerTokenResolver resolver;
    
    @Mock
    HttpServletRequest request;

    @BeforeEach
    void setUp() {
        resolver = new JwtBearerTokenResolver();
    }

    @Test
    void resolve() {
        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer x.y.z");
        String resolve = resolver.resolve(request);
        Assertions.assertNotNull(resolve);
        Assertions.assertEquals("x.y.z", resolve);
    }
}