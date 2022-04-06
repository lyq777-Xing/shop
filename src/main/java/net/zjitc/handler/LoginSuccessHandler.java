package net.zjitc.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import net.zjitc.common.ResponseResult;
import net.zjitc.model.AuthenticationToken;
import net.zjitc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        ResponseResult<Object> result = new ResponseResult<>();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        获取认证信息
        AuthenticationToken token = new AuthenticationToken();
//        生成token的值
//        生成jwt 放入请求头中
        String jwt = jwtUtils.generateToken(authentication.getName());
        response.setHeader(jwtUtils.getHeader(),jwt);
        token.setToken(jwt);
        token.setAuthorities(authorities);
        result.Success("登录成功",token);

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
//        response.getWriter().write(result);
    }
}
