package com.example.supermarket.service;

import com.example.supermarket.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> getMenusByAdmin();
    List<Menu> getMenusWithRole();
}
