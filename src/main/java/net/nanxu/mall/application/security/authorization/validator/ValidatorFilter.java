package net.nanxu.mall.application.security.authorization.validator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.nanxu.mall.infra.exception.AppErrorCode;
import net.nanxu.mall.infra.result.AppResult;
import net.nanxu.mall.infra.utils.Json;
import net.nanxu.mall.infra.utils.ServletUtil;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * ValidatorFilter.
 *
 * @author: pan
 **/
@Component
@RequiredArgsConstructor
public class ValidatorFilter extends OncePerRequestFilter {

    private final Set<RequestMatcher> matchers = Set.of(
            new AntPathRequestMatcher("/api/auth/token", HttpMethod.POST.name())
    );

    private final List<AuthenticationValidator> authenticationValidators;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        if (matchers.stream().anyMatch(e -> e.matches(request))) {

            for (AuthenticationValidator validator : authenticationValidators) {
                if (!validator.valid(request, response)) {
                    AppResult result = AppResult.buildFailure(AppErrorCode.VERIFICATION_FAILED);
                    ServletUtil.renderJson(response, Json.objectToJson(result));
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }


}
