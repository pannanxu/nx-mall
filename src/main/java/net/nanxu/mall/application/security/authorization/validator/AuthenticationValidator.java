package net.nanxu.mall.application.security.authorization.validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

/**
 * AuthenticationValidator.
 *
 * @author: pan
 **/
public interface AuthenticationValidator {
    boolean valid(@NonNull HttpServletRequest request,
                  @NonNull HttpServletResponse response);

}
