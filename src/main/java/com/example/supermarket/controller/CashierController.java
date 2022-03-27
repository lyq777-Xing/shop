package com.example.supermarket.controller;

import com.example.supermarket.pojo.Admin;
import com.example.supermarket.service.IAdminService;
import com.example.supermarket.vo.RespBean;
import com.example.supermarket.vo.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/cashier")
public class CashierController {
    @Autowired
    private IAdminService adminService;
    @ApiOperation("获取所有收银员，分页查询")
    @GetMapping("/")
    public RespPageBean getCashierPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            LocalDate[] beginDateScope) {
        String keywords="收银员";
        return adminService.getByPage(currentPage, size, keywords, beginDateScope);
    }
    @ApiOperation("获取供应商编号")
    @GetMapping("/maxID")
    public RespBean maxId() {

        return adminService.maxID();
    }
    @ApiOperation("添加收银员")
    @PostMapping("/")
    public RespBean addCas(@RequestBody Admin admin) {

        return adminService.add(admin);
    }
    @ApiOperation("更新收银员")
    @PutMapping("/")
    public RespBean updateCas(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
    @ApiOperation("删除收银员")
    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id) {

        if (adminService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
