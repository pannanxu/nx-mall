package net.nanxu.mall.application.security.authorization;

import lombok.RequiredArgsConstructor;
import net.nanxu.mall.application.security.SecurityConfigurer;
import net.nanxu.mall.application.security.authorization.decision.AuthorizationDecisionPolicyFactory;
import net.nanxu.mall.infra.AnonymousUserConst;
import net.nanxu.mall.infra.exception.AppErrorCode;
import net.nanxu.mall.infra.result.AppAuthResult;
import net.nanxu.mall.infra.result.AppResult;
import net.nanxu.mall.infra.utils.Json;
import net.nanxu.mall.infra.utils.ServletUtil;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.stereotype.Component;

/**
 * AuthorizationSecurityConfigurer.
 *
 * @author: pan
 **/
@Component
@RequiredArgsConstructor
public class AuthorizationSecurityConfigurer implements SecurityConfigurer {

    private final JwtDecoder decoder;
    private final AuthorizationDecisionPolicyFactory policyFactory;

    @Override
    public void configure(HttpSecurity http) {
        try {
            RequestAuthorizationManager authorizationManager = new RequestAuthorizationManager(policyFactory);

            Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingCustomizer = configurer -> {
                // 认证用户无权限
                configurer.accessDeniedHandler((request, response, exception) -> {
                    AppResult result = AppAuthResult.buildFailure(AppErrorCode.PERMISSION_NOT_FOUND);
                    ServletUtil.renderJson(response, Json.objectToJson(result));
                });
                // 匿名用户无权限
                configurer.authenticationEntryPoint((request, response, exception) -> {
                    AppResult result = AppAuthResult.buildFailure(AppErrorCode.UNAUTHORIZED);
                    ServletUtil.renderJson(response, Json.objectToJson(result));
                });
            };

            Customizer<AnonymousConfigurer<HttpSecurity>> anonymousCustomizer = configurer ->
                    configurer.principal(AnonymousUserConst.PRINCIPAL)
                            .authorities(AnonymousUserConst.Role);

            JwtSecurityContextHolderFilter contextHolderFilter = new JwtSecurityContextHolderFilter(decoder);

            http.authorizeHttpRequests(registry -> registry
                            .requestMatchers("/error", "/static/**").permitAll()
                            .requestMatchers("/**", "/actuator/**").access(authorizationManager)
                    )
                    .exceptionHandling(exceptionHandlingCustomizer)
                    .anonymous(anonymousCustomizer)
                    .addFilterAfter(contextHolderFilter, SecurityContextHolderFilter.class)
            ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
