package net.nanxu.mall.application.security.authorization;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nanxu.mall.infra.AnonymousUserConst;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * JwtSecurityContextHolderFilter.
 *
 * @author: pan
 **/
@Slf4j
@RequiredArgsConstructor
public class JwtSecurityContextHolderFilter extends GenericFilterBean {
    private final JwtBearerTokenResolver resolver = new JwtBearerTokenResolver();

    private final JwtDecoder decoder;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest request && servletResponse instanceof HttpServletResponse response) {
            String resolve = resolver.resolve(request);
            if (null != resolve) {
                SecurityContextHolder.setDeferredContext(() -> {
                    try {
                        Jwt jwt = decoder.decode(resolve);
                        JwtAuthenticationToken token = new JwtAuthenticationToken(jwt);
                        return new SecurityContextImpl(token);
                    } catch (Exception ex) {
                        Authentication anonymous = new AnonymousAuthenticationToken("key",
                                AnonymousUserConst.PRINCIPAL,
                                AuthorityUtils.createAuthorityList(AnonymousUserConst.Role));
                        return new SecurityContextImpl(anonymous);
                    }
                });
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
