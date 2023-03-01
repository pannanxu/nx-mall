package net.nanxu.mall.application.security.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nanxu.mall.application.security.authorization.decision.AuthorizationDecisionPolicy;
import net.nanxu.mall.application.security.authorization.decision.AuthorizationDecisionPolicyFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.List;
import java.util.function.Supplier;

/**
 * RequestAuthorizationManager.
 *
 * @author: pan
 **/
@Slf4j
@RequiredArgsConstructor
public class RequestAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final AuthorizationDecisionPolicyFactory policyFactory;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationDecision decision = check(authentication, object);
        if (decision != null && !decision.isGranted()) {
            throw new AccessDeniedException("暂无访问权限");
        }
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        Authentication authentication = authenticationSupplier.get();
        List<AuthorizationDecisionPolicy> policies = policyFactory.getPolicies(authentication);
        for (AuthorizationDecisionPolicy policy : policies) {
            if (!policy.check(authentication, context)) {
                return new AuthorizationDecision(false);
            }
        }
        return new AuthorizationDecision(true);
    }
}
