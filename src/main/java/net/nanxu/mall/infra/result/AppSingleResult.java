package net.nanxu.mall.infra.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.nanxu.mall.infra.exception.AppErrorCode;

import static net.nanxu.mall.infra.exception.AppErrorCode.TRY_LATER;

/**
 * @author: Pan
 **/
@Schema(description = "对象统一返回")
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AppSingleResult<T> extends AppResult {

    private T data;

    public static <T> AppSingleResult<T> ok(T data) {
        AppSingleResult<T> appSingleResult = new AppSingleResult<>();
        appSingleResult.setSuccess(true);
        appSingleResult.setData(data);
        return appSingleResult;
    }

    /**
     * 数据更新中，稍后再试
     * 客户端对此类错误不做页面刷新等其他操作
     */
    public static <T> AppSingleResult<T> tryLater() {
        AppSingleResult<T> appSingleResult = new AppSingleResult<>();
        appSingleResult.setSuccess(false);
        appSingleResult.setErrCode(TRY_LATER.getErrCode());
        appSingleResult.setErrMessage(TRY_LATER.getErrDesc());
        return appSingleResult;
    }

    public static <T> AppSingleResult<T> failed(String errCode, String errDesc) {
        AppSingleResult<T> appSingleResult = new AppSingleResult<>();
        appSingleResult.setSuccess(false);
        appSingleResult.setErrCode(errCode);
        appSingleResult.setErrMessage(errDesc);
        return appSingleResult;
    }

    public static <T> AppSingleResult<T> failed(String errCode, String errDesc, T data) {
        AppSingleResult<T> appSingleResult = new AppSingleResult<>();
        appSingleResult.setSuccess(false);
        appSingleResult.setData(data);
        appSingleResult.setErrCode(errCode);
        appSingleResult.setErrMessage(errDesc);
        return appSingleResult;
    }

    public static <T> AppSingleResult<T> failed(AppErrorCode appErrorCode) {
        AppSingleResult<T> appSingleResult = new AppSingleResult<>();
        appSingleResult.setSuccess(false);
        appSingleResult.setErrCode(appErrorCode.getErrCode());
        appSingleResult.setErrMessage(appErrorCode.getErrDesc());
        return appSingleResult;
    }
}
