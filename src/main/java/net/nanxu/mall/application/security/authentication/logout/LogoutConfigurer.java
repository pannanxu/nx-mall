package net.nanxu.mall.application.security.authentication.logout;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.nanxu.mall.application.security.SecurityConfigurer;
import net.nanxu.mall.infra.result.AppResult;
import net.nanxu.mall.infra.utils.ServletUtil;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * LogoutConfigurer.
 *
 * @author: pan
 **/
@Slf4j
@Component
public class LogoutConfigurer implements SecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) {
        try {
            http.logout(configurer -> configurer
                    .addLogoutHandler((request, response, authentication) ->
                            log.debug("user logout: {}", authentication)
                    )
                    .logoutSuccessHandler((request, response, authentication) -> {
                        AppResult result = AppResult.buildSuccess();
                        ServletUtil.renderJson(response, result.toString());
                    })
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class DefaultLogoutFilter extends LogoutFilter {

        public DefaultLogoutFilter() {
            super(new LogoutSuccessHandler() {
                @Override
                public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                    System.out.println("onLogoutSuccess");
                }
            }, new LogoutHandler() {
                @Override
                public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                    System.out.println("logout");
                }
            });
            setFilterProcessesUrl("/logout-new");
        }
    }
}
