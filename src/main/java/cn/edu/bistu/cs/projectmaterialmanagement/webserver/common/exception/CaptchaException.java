package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception;

import javax.security.sasl.AuthenticationException;

/**
 * 验证码异常，如果用户 输入的验证码不对，则抛出此此异常
 */
public class CaptchaException extends AuthenticationException {
    private final Integer code;
    private final String message;

    public CaptchaException(Integer code,
                            String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
