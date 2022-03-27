package com.example.supermarket.controller;
import com.example.supermarket.pojo.Role;
import com.example.supermarket.service.IAdminService;
import com.example.supermarket.service.IRoleService;
import com.example.supermarket.vo.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IRoleService roleService;
    @Autowired
    private IAdminService adminService;
    @ApiOperation("获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {

        return roleService.list();
    }
    @ApiOperation("更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {

        return adminService.updateAdminRole(adminId, rids);
    }
}
