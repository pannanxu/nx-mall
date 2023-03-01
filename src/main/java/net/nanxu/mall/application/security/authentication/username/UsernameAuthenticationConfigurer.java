package net.nanxu.mall.application.security.authentication.username;

import lombok.RequiredArgsConstructor;
import net.nanxu.mall.application.security.SecurityConfigurer;
import net.nanxu.mall.application.security.authorization.validator.ValidatorFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * JwtAuthenticationConfigurer.
 *
 * @author: pan
 **/
@Component
@RequiredArgsConstructor
public class UsernameAuthenticationConfigurer implements SecurityConfigurer {

    private final ValidatorFilter validatorFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = new UsernameAuthenticationManager(userDetailsService, passwordEncoder);
        AuthenticationConverter authenticationConverter = new UsernameAuthenticationConverter();

        UsernameAuthenticationFilter filter = new UsernameAuthenticationFilter(authenticationManager, authenticationConverter, jwtEncoder);
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(validatorFilter, UsernameAuthenticationFilter.class);
    }
}
