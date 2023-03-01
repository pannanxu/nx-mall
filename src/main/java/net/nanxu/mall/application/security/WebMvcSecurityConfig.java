package net.nanxu.mall.application.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * WebMvcSecurityConfig.
 *
 * @author: pan
 **/
@Slf4j
@Configuration
@EnableWebSecurity
public class WebMvcSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    JwtDecoder decoder,
                                    ObjectProvider<SecurityConfigurer> configurers)
            throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .logout(withDefaults())
        ;
        configurers.orderedStream().forEach(e -> e.configure(http));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    public static class DynamicUrlBasedCorsConfigurationSource
//            extends UrlBasedCorsConfigurationSource {
//        @Override
//        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//            System.out.println(request.getRequestURI());
//            return super.getCorsConfiguration(request);
//        }
//    }

}