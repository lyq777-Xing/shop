package com.example.supermarket.security;

import com.example.supermarket.pojo.Menu;
import com.example.supermarket.pojo.Role;
import com.example.supermarket.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;


/**
 * 权限控制
 * 根据请求url分析所需的角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;


    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        // 获取请求url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getMenusWithRole();

        for (Menu menu : menus) {
            // 判断请求url与菜单角色是否匹配
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {

                String[] strings = menu.getRoles().stream().map(Role::getRolename).toArray(String[]::new);
                return SecurityConfig.createList(strings);
            }
        }
        // 没匹配的url默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}