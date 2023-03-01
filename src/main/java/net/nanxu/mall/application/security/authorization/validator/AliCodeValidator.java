package net.nanxu.mall.application.security.authorization.validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

/**
 * AliCodeValidatorFilter.
 *
 * @author: pan
 **/
public class AliCodeValidator implements AuthenticationValidator {

    @Override
    public boolean valid(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        // TODO 阿里云验证码处理
        return true;
    }
}
