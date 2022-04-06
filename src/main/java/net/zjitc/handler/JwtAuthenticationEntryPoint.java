package net.zjitc.handler;

import cn.hutool.json.JSONUtil;
import net.zjitc.common.ResponseCode;
import net.zjitc.common.ResponseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(ResponseCode.UNAUTHORIZED.getCode());
        ServletOutputStream outputStream = response.getOutputStream();
        ResponseResult<Object> result = new ResponseResult<>();
        result.BadRequest("请先登录");
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
