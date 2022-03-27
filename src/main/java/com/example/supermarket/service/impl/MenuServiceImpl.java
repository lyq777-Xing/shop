package com.example.supermarket.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.supermarket.mapper.MenuMapper;
import com.example.supermarket.pojo.Admin;
import com.example.supermarket.pojo.Menu;
import com.example.supermarket.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public List<Menu> getMenusByAdmin() {

        Admin principal = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 根据用户id获取menus
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + principal.getId());

        if (CollectionUtils.isEmpty(menus)) {
            // redis缓存不存在，则在数据库中查询
            menus = menuMapper.getMenusByAdminId(principal.getId());
            // 将数据设置到redis中
            valueOperations.set("menu_" + principal.getId(), menus);
        }
        return menus;
    }
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
}
