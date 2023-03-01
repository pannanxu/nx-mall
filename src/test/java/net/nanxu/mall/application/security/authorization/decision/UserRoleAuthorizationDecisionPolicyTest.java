package net.nanxu.mall.application.security.authorization.decision;

import net.nanxu.mall.infra.AnonymousUserConst;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class UserRoleAuthorizationDecisionPolicyTest {

    UserRoleAuthorizationDecisionPolicy policy;

    @Mock
    Function<String, Authentication> authenticationFunction;

    @BeforeEach
    void setUp() {
        policy = new UserRoleAuthorizationDecisionPolicy();
    }

    @Test
    void supports() {
        Authentication anonymous = new AnonymousAuthenticationToken("key",
                AnonymousUserConst.PRINCIPAL,
                AuthorityUtils.createAuthorityList(AnonymousUserConst.Role));
        Mockito.when(authenticationFunction.apply("anonymous")).thenReturn(anonymous);
        
        boolean result = policy.supports(authenticationFunction.apply("anonymous"));
        Assertions.assertFalse(result);
    }

    @Test
    void check() {
        Jwt jwt = new Jwt("tokenValue",
                Instant.now(),
                null,
                Map.of("iss", ""),
                Map.of("roles", "admin,user",
                        JwtClaimNames.SUB, "admin")
        );
        JwtAuthenticationToken token = new JwtAuthenticationToken(jwt);
        token.setAuthenticated(true);
        Mockito.when(authenticationFunction.apply("admin")).thenReturn(token);

        boolean result = policy.check(authenticationFunction.apply("admin"), null);
        Assertions.assertTrue(result);
    }
}