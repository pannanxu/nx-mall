package net.nanxu.mall.infra.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.nanxu.mall.infra.utils.Json;

/**
 * @description:
 * @author: pan
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AppAuthResult<T> extends AppSingleResult<T> {

    @Schema(title = "Token")
    private String accessToken;
    
    @Schema(title = "授权类型(固定Bearer + 空格 + accessToken)", defaultValue = "Bearer")
    private String authType = "Bearer";

    public static <T> AppAuthResult<T> auth(String accessToken) {
        AppAuthResult<T> result = new AppAuthResult<>();
        result.setAccessToken(accessToken);
        result.setSuccess(true);
        return result;
    }


    @Override
    public String toString() {
        return Json.objectToJson(this);
    }
}