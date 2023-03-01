package net.nanxu.mall.application.security;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@ExtendWith(MockitoExtension.class)
class WebMvcSecurityConfigTest {

    WebMvcSecurityConfig config;

    @BeforeEach
    public void setup() {
        config = new WebMvcSecurityConfig();
    }

    @Test
    public void generator() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();

        FileUtils.write(ResourceUtils.getFile("src/test/resources/app.key"),
                """
                        -----BEGIN PRIVATE KEY-----
                        %s
                        -----END PRIVATE KEY-----
                        """.formatted(new String(Base64.getEncoder().encode(aPrivate.getEncoded()))),
                "UTF-8");
        FileUtils.write(ResourceUtils.getFile("src/test/resources/app.pub"),
                """
                        -----BEGIN PUBLIC KEY-----
                        %s
                        -----END PUBLIC KEY-----
                        """.formatted(new String(Base64.getEncoder().encode(aPublic.getEncoded()))),
                "UTF-8");
    }

    @Test
    public void passwordEncoder() {
        String pwd = "123456";
        PasswordEncoder encoder = config.passwordEncoder();
        String encodePwd = encoder.encode(pwd);
        boolean matches = encoder.matches(pwd, encodePwd);
        Assertions.assertTrue(matches);
    }
}