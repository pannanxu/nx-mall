package net.nanxu.mall.application.security.authorization.decision;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

/**
 * AnonymousAuthorizationDecisionPolicy.
 *
 * @author: pan
 **/
@Component
public class AnonymousAuthorizationDecisionPolicy implements AuthorizationDecisionPolicy {

    @Override
    public boolean supports(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

    @Override
    public boolean check(Authentication authentication, RequestAuthorizationContext context) {
        return true;
    }
}
