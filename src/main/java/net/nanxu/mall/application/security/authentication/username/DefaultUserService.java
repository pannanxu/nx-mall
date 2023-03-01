package net.nanxu.mall.application.security.authentication.username;

import lombok.RequiredArgsConstructor;
import net.nanxu.mall.domain.user.repository.enums.IdentityType;
import net.nanxu.mall.domain.user.service.IdentityService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * DefaultUserService.
 *
 * @author: pan
 **/
@Component
@RequiredArgsConstructor
public class DefaultUserService implements UserDetailsService, UserDetailsPasswordService {

    private final IdentityService identityService;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return identityService.fetchIdentity(username, IdentityType.username)
                .map(identity -> User.withUsername(identity.getIdentifier())
                        .password(identity.getCredential())
                        .authorities("users")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
    }
}
