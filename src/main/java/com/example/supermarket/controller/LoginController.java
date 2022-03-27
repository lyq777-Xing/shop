package com.example.supermarket.controller;

import com.example.supermarket.pojo.Admin;
import com.example.supermarket.service.IAdminService;
import com.example.supermarket.vo.AdminLoginParam;
import com.example.supermarket.vo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Api(tags = "登录控制器")
public class LoginController {
    @Autowired
    private IAdminService adminService;

    /**
     * 参数中有个@RequestBody 会自动将请求的json字符串转换为实体类
     * 1.对于一个Controller方法来说,它的作用就是封装客户端传递过来的数据;
     * 2.调用service方法,执行业务逻辑
     * 3.封装响应客户端的数据
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public RespBean login(@RequestBody AdminLoginParam param, HttpServletRequest request){
        return adminService.login(param.getUsername(),
                param.getPassword(),
                param.getCode(),
                request);
    }
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.findByUsername(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功");
    }
}
