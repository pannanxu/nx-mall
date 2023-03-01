package net.nanxu.mall.infra.exception;

/**
 * @author: Pan
 **/
public enum AppErrorCode {
    /**
     * 一般存在于请求参数中校验规则未通过。
     * <p>
     * 一般来说此错误码在前端就可以捕获。
     */
    INVALID_PARAMS("INVALID_PARAMS", "参数错误"),
    TRY_LATER("TRY_LATER", "稍后再试"),
    /**
     * 一般是收到限流规则的拦截。
     */
    FREQUENTLY_ERROR("FREQUENTLY_ERROR", "操作频繁，稍后再试"),
    /**
     * 其他未捕获的异常，一般代表系统错误，需要注意
     */
    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误"),
    /**
     * 通常业务处理失败时就抛出此异常
     */
    BIZ_ERROR("BIZ_ERROR", "业务异常"),
    /**
     * 一般表示没有登陆或者登陆状态已失效，需要进行登陆
     */
    UNAUTHORIZED("UNAUTHORIZED", "未授权"),
    /**
     * 认证得到的 token 已到达过期时间，需要重新登陆得到新的 token。
     * <p>
     * 得到此错误码可以在当前页面弹窗进行登陆，避免已填写的表单丢失，这样用户体验会好很多
     */
    AUTHORIZATION_EXPIRED("AUTHORIZATION_EXPIRED", "授权已过期"),
    /**
     * 登陆失败，可能是用户名或密码错误，也可能是用户不存在等
     */
    AUTHENTICATION_FAILURE("AUTHENTICATION_FAILURE", "认证失败"),
    /**
     * 一般表示已登陆的用户，对当前操作的权限不足。前端可以根据需求展示权限不足页面或者404等
     */
    PERMISSION_NOT_FOUND("PERMISSION_NOT_FOUND", "暂无权限"),
    VERIFICATION_FAILED("VERIFICATION_FAILED", "验证码错误"),
    OTHER_DEVICE_LOGIN("OTHER_DEVICE_LOGIN", "账号在其他设备登录"),
    ;

    private final String errCode;
    private final String errDesc;

    AppErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }
}
