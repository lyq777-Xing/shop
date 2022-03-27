package com.example.supermarket.security;

import com.example.supermarket.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer ×××××××××
        String header = request.getHeader(tokenHeader);
        if(header!=null&&header.startsWith(tokenHead)){
            //去掉Bearer的前缀,获取jwt
            String token = header.substring(tokenHead.length());
            //使用工具类解析token，获取荷载中保存的用户名信息
            String userNameFormToken = jwtTokenUtils.getUserNameFormToken(token);
            if (null != userNameFormToken && null == SecurityContextHolder.getContext().getAuthentication()){
                //通过从token里面解析的用户名加载用户信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFormToken);
                //验证token是否有效,验证userDetails是否有效
                if (jwtTokenUtils.validateToken(token,userDetails)){
                    //进行登录
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //设置本次请求对象
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    //类似session.setAttribute()向session会话设置用户信息
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        /**
         * filter 过滤器,能够拦截请求,如果需要放行本次请求,调用filterChain.doFilter
         * 将请求传递给下一个filter，如果filterChain没有下一个filter了,将请求传递给servlet DispatcherServlet
         * ->根据请求地址 ->Controller
         */
        filterChain.doFilter(request,response);
    }
}
