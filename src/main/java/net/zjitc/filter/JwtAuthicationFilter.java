package net.zjitc.filter;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import net.zjitc.entity.Users;
import net.zjitc.service.UserRoleVoService;
import net.zjitc.service.impl.IUserDetailsService;
import net.zjitc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt拦截器
 */
public class JwtAuthicationFilter extends BasicAuthenticationFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IUserDetailsService userDetailsService;

    @Autowired
    private UserRoleVoService userRoleVoService;

    public JwtAuthicationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(jwtUtils.getHeader());
        if(StrUtil.isBlankOrUndefined(header)){
            chain.doFilter(request,response);
            return;
        }

        Claims claimByToken = jwtUtils.getClaimByToken(header);
        if(claimByToken == null){
            throw new JwtException("token 异常");
        }

        if(jwtUtils.isTokenExpired(claimByToken)){
            throw new JwtException("token已过期");
        }

        String username = claimByToken.getSubject();

//        获取userid
        Users serviceByName = userRoleVoService.findByName(username);
        int id = serviceByName.getId();

//        获取用户的权限信息
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, userDetailsService.getUserAuthority(id));
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request,response);
    }
}
