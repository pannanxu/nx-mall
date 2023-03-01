package net.nanxu.mall.application.security.authentication.username;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.nanxu.mall.infra.utils.Json;
import net.nanxu.mall.infra.utils.ServletUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

@Slf4j
public class UsernameAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        String body = ServletUtil.readRequestBody(request);
        Token token = Json.jsonToObject(body, Token.class);
        return UsernamePasswordAuthenticationToken.unauthenticated(token.username(), token.password());
    }


    private record Token(@Schema(title = "用户名")
                         String username,
                         @Schema(title = "密码")
                         String password) {
    }
}
