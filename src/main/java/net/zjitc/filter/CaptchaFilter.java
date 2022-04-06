package net.zjitc.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import net.zjitc.common.Const;
import net.zjitc.exception.CaptchaException;
import net.zjitc.handler.LoginFailtureHandler;
import net.zjitc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验验证码是否正确
 */
@Component
public class CaptchaFilter  extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private LoginFailtureHandler loginFailtureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if("/a1/private/v1/login".equals(uri) && request.getMethod().equals("POST")){
            try{
//              校验验证码
                validate(request);
            }catch (CaptchaException e){
//                捕获到错误 交给失败处理器
                loginFailtureHandler.onAuthenticationFailure(request,response,e);
            }
        }
//        验证成功则继续往下走
        filterChain.doFilter(request,response);
    }

    /**
     * 校验验证码的逻辑
     * @param request
     */
    private void validate(HttpServletRequest request) {
        String code = request.getParameter("code");
        String key = request.getParameter("key");

        if(StringUtils.isBlank(code) || StringUtils.isBlank(key)){
            throw new CaptchaException("验证码错误");
        }

        if(!code.equals(redisUtils.hmGet(Const.CAPTCHA_KEY,key))){
            throw new CaptchaException("验证码错误");
        }
        redisUtils.hdel(Const.CAPTCHA_KEY,key);
    }
}
