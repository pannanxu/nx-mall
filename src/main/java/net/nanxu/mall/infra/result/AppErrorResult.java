package net.nanxu.mall.infra.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.nanxu.mall.infra.exception.AppErrorCode;

/**
 * @description:
 * @author: Pan
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AppErrorResult extends AppResult {

    private String debugMsg;

    public static AppErrorResult buildFailure(AppErrorCode code) {
        AppErrorResult result = new AppErrorResult();
        result.setErrCode(code.getErrCode());
        result.setErrMessage(code.getErrCode());
        return result;
    }

    public static AppErrorResult buildFailure(String code, String desc) {
        AppErrorResult result = new AppErrorResult();
        result.setErrCode(code);
        result.setErrMessage(desc);
        return result;
    }

    public static AppErrorResult buildFailure(AppErrorCode code, String desc) {
        return buildFailure(code.getErrCode(), desc);
    }

    public AppErrorResult debug(String err) {
        this.debugMsg = err;
        return this;
    }

}
