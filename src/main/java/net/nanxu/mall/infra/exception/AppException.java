package net.nanxu.mall.infra.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Pan
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {

    private String errCode;
    private String errDesc;

    public AppException() {
        super(AppErrorCode.BIZ_ERROR.getErrCode());
        this.errCode = AppErrorCode.BIZ_ERROR.getErrCode();
        this.errDesc = AppErrorCode.BIZ_ERROR.getErrDesc();
    }

    public AppException(String msg) {
        super(msg);
        this.errCode = AppErrorCode.BIZ_ERROR.getErrCode();
        this.errDesc = msg;
    }

    public AppException(AppErrorCode errorCode) {
        super(errorCode.getErrDesc());
        this.errCode = errorCode.getErrCode();
        this.errDesc = errorCode.getErrDesc();
    }

    public AppException(AppErrorCode errorCode, String msg) {
        super(msg);
        this.errCode = errorCode.getErrCode();
        this.errDesc = msg;
    }


}
