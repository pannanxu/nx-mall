package net.nanxu.mall.infra.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * ApplicationProperties.
 *
 * @author: pan
 **/
@Data
@Configuration
@ConfigurationProperties("app")
public class ApplicationProperties {
    
    private String workDir;
    
    private Security security;
    
    
    @Data
    public static class Security {

        private RSAPublicKey publicKey;

        private RSAPrivateKey privateKey;
    }
}
