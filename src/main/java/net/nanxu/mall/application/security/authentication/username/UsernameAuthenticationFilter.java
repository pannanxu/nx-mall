package net.nanxu.mall.application.security.authentication.username;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nanxu.mall.infra.exception.AppErrorCode;
import net.nanxu.mall.infra.result.AppAuthResult;
import net.nanxu.mall.infra.result.AppResult;
import net.nanxu.mall.infra.utils.ServletUtil;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * JwtAuthenticationFilter.
 *
 * @author: pan
 **/
@Slf4j
public class UsernameAuthenticationFilter extends AuthenticationFilter {

    public UsernameAuthenticationFilter(AuthenticationManager authenticationManager,
                                        AuthenticationConverter authenticationConverter,
                                        JwtEncoder jwtEncoder) {
        super(authenticationManager, authenticationConverter);
        setRequestMatcher(new AntPathRequestMatcher("/api/auth/token", HttpMethod.POST.name()));
        setSuccessHandler(new JwtAuthenticationSuccessHandler(jwtEncoder));
        setFailureHandler(new JwtAuthenticationFailureHandler());
//        setSecurityContextRepository(RedisSecurityContextRepository.REPOSITORY);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        super.doFilterInternal(request, response, filterChain);
    }

    @Slf4j
    public static class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            AppResult result = AppAuthResult.buildFailure(AppErrorCode.AUTHENTICATION_FAILURE);
            ServletUtil.renderJson(response, result.toString());
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    public static class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        private final JwtEncoder jwtEncoder;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
            onAuthenticationSuccess(request, response, authentication);
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String token = accessTokenEncode(authentication).getTokenValue();
            AppAuthResult<String> result = AppAuthResult.auth(token);
            ServletUtil.renderJson(response, result.toString());
        }

        Jwt accessTokenEncode(Authentication authentication) {
            Instant issuedAt = Instant.now();
            // TODO Make the expiresAt configurable
            Instant expiresAt = issuedAt.plus(1, ChronoUnit.MINUTES);
            JwsHeader headers = JwsHeader.with(SignatureAlgorithm.RS512).build();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("nx-mall")
                    .issuedAt(issuedAt)
                    .expiresAt(expiresAt)
                    // the principal is the username
                    .subject(authentication.getPrincipal().toString())
                    .claim("roles", "admin,user")
                    .build();
            return jwtEncoder.encode(JwtEncoderParameters.from(headers, claims));
        }
    }

}
