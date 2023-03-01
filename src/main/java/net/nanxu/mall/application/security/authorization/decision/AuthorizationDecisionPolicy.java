package net.nanxu.mall.application.security.authorization.decision;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * AuthorizationDecisionPolicy.
 *
 * @author: pan
 **/
public interface AuthorizationDecisionPolicy {
    
    boolean supports(Authentication authentication);
    
    boolean check(Authentication authentication, RequestAuthorizationContext context);
    
}
