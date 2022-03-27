package com.example.supermarket.security;
import com.example.supermarket.vo.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override 
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");   
        PrintWriter writer = httpServletResponse.getWriter();
        RespBean respBean = RespBean.error("权限不足，请联系管理员！");
        respBean.setCode(403);       
        writer.write(new ObjectMapper().writeValueAsString(respBean));
        writer.flush();    
        writer.close(); 
    }
}