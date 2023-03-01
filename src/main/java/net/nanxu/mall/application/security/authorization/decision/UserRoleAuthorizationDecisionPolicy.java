package net.nanxu.mall.application.security.authorization.decision;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserRoleAuthorizationDecisionPolicy.
 *
 * @author: pan
 **/
@Slf4j
@Component
public class UserRoleAuthorizationDecisionPolicy implements AuthorizationDecisionPolicy {

    @Override
    public boolean supports(Authentication authentication) {
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public boolean check(Authentication authentication, RequestAuthorizationContext context) {
        if (!authentication.isAuthenticated()) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authentication instanceof JwtAuthenticationToken token) {
            Set<SimpleGrantedAuthority> roles = Arrays.stream(token.getToken().getClaim("roles")
                            .toString()
                            .split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
            authorities = roles;
        }

        String name = authentication.getName();
        log.debug("UserRoleAuthorizationDecisionPolicy.check(name: {}, authorities: {})",
                name, authorities);

        Set<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        if (roles.contains("admin")) {
            return true;
        }

        return false;
    }
}
