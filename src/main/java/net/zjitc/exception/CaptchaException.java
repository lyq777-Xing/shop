package net.zjitc.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码校验失败的异常
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }
}
