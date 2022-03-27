package com.example.supermarket.controller;


import com.example.supermarket.pojo.Menu;
import com.example.supermarket.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/system")
public class MenuController {
    @Autowired
    private IMenuService menuService;;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId() {

        return menuService.getMenusByAdmin();
    }
}
