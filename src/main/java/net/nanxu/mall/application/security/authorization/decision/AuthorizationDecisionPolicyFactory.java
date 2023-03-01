package net.nanxu.mall.application.security.authorization.decision;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AuthorizationDecisionPolicyFactory.
 *
 * @author: pan
 **/
@Component
@RequiredArgsConstructor
public class AuthorizationDecisionPolicyFactory {

    private final ObjectProvider<AuthorizationDecisionPolicy> policies;

    public List<AuthorizationDecisionPolicy> getPolicies(Authentication authentication) {
        return policies.orderedStream()
                .filter(e -> e.supports(authentication))
                .toList();
    }

}
