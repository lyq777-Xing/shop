package com.example.supermarket.security;
import com.example.supermarket.vo.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override   
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");     
        PrintWriter writer = httpServletResponse.getWriter();
        RespBean respBean = RespBean.error("未登录，请重新登录");
        respBean.setCode(401);      
        writer.write(new ObjectMapper().writeValueAsString(respBean));
        writer.flush();     
        writer.close();  
    }
}