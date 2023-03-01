package net.nanxu.mall.application.security.authentication;

import net.nanxu.mall.application.security.authentication.username.DefaultUserService;
import net.nanxu.mall.domain.user.repository.entity.TIdentity;
import net.nanxu.mall.domain.user.repository.enums.IdentityType;
import net.nanxu.mall.domain.user.service.IdentityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @InjectMocks
    DefaultUserService userService;

    @Mock
    IdentityService identityService;

    @BeforeEach
    void setUp() {
        TIdentity identity = new TIdentity();
        identity.setIdentifier("admin");
        identity.setCredential("123456");
        identity.setIdentityType(IdentityType.username.getType());
        identity.setId(1L);
        identity.setNickname("some");
        Mockito.when(identityService.fetchIdentity("admin", IdentityType.username))
                .thenReturn(Optional.of(identity));
    }

    @Test
    void loadUserByUsername() {
        UserDetails user = userService.loadUserByUsername("admin");
        Assertions.assertEquals(user.getUsername(), "admin");
    }
}