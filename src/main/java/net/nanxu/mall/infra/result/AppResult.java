package net.nanxu.mall.infra.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import net.nanxu.mall.infra.exception.AppErrorCode;
import net.nanxu.mall.infra.utils.Json;

/**
 * @author: Pan
 **/
@Schema(description = "统一响应")
@Data
@Accessors(chain = true)
public class AppResult {
    private static final long serialVersionUID = 1L;

    @Schema(description = "成功")
    private boolean success;
    @Schema(description = "失败code")
    private String errCode;
    @Schema(description = "失败原因")
    private String errMessage;

    public static AppResult buildFailure(String errCode, String errMessage) {
        AppResult appResult = new AppResult();
        appResult.setSuccess(false);
        appResult.setErrCode(errCode);
        appResult.setErrMessage(errMessage);
        return appResult;
    }

    public static AppResult buildSuccess() {
        AppResult appResult = new AppResult();
        appResult.setSuccess(true);
        return appResult;
    }

    public static AppResult buildFailure(AppErrorCode appErrorCode) {
        AppResult appResult = new AppResult();
        appResult.setSuccess(false);
        appResult.setErrCode(appErrorCode.getErrCode());
        appResult.setErrMessage(appErrorCode.getErrDesc());
        return appResult;
    }

    @Override
    public String toString() {
        return Json.objectToJson(this);
    }
}
