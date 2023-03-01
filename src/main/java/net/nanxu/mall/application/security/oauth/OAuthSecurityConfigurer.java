package net.nanxu.mall.application.security.oauth;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import net.nanxu.mall.application.security.SecurityConfigurer;
import net.nanxu.mall.infra.config.ApplicationProperties;
import net.nanxu.mall.infra.exception.AppErrorCode;
import net.nanxu.mall.infra.result.AppAuthResult;
import net.nanxu.mall.infra.result.AppResult;
import net.nanxu.mall.infra.utils.Json;
import net.nanxu.mall.infra.utils.ServletUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.SupplierJwtDecoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;

/**
 * OAuthSecurityConfigurer.
 *
 * @author: pan
 **/
@Component
public class OAuthSecurityConfigurer implements SecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) {
        try {
            http.oauth2ResourceServer(configurer -> configurer
                    .authenticationEntryPoint((request, response, authException) -> {
                        if (authException instanceof InvalidBearerTokenException) {
                            AppResult result = AppAuthResult.buildFailure(AppErrorCode.AUTHORIZATION_EXPIRED);
                            ServletUtil.renderJson(response, Json.objectToJson(result));
                            return;
                        }
                        AppResult result = AppAuthResult.buildFailure(AppErrorCode.UNAUTHORIZED);
                        ServletUtil.renderJson(response, Json.objectToJson(result));
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        System.out.println("accessDeniedHandler");
                        AppResult result = AppAuthResult.buildFailure(AppErrorCode.PERMISSION_NOT_FOUND);
                        ServletUtil.renderJson(response, Json.objectToJson(result));
                    })
//                    .bearerTokenResolver(new DefaultBearerTokenResolver())
                    .jwt()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    JwtDecoder jwtDecoder(ApplicationProperties properties) {
        return new SupplierJwtDecoder(() -> NimbusJwtDecoder
                .withPublicKey(properties.getSecurity().getPublicKey())
                .signatureAlgorithm(SignatureAlgorithm.RS512)
                .build());
    }

    @Bean
    JwtEncoder jwtEncoder(ApplicationProperties properties) {
        RSAKey rsaKey = new RSAKey.Builder(properties.getSecurity().getPublicKey())
                .privateKey(properties.getSecurity().getPrivateKey())
                .algorithm(JWSAlgorithm.parse(SignatureAlgorithm.RS512.getName()))
                .build();
        ImmutableJWKSet<SecurityContext> jwk = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwk);
    }

}
