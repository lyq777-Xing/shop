package com.example.supermarket.controller;

import com.example.supermarket.pojo.Admin;
import com.example.supermarket.pojo.Supplier;
import com.example.supermarket.service.IAdminService;
import com.example.supermarket.service.ISupplierService;
import com.example.supermarket.vo.RespBean;
import com.example.supermarket.vo.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IAdminService adminService;
    @ApiOperation("获取所有供应商，分页查询")
    @GetMapping("/")
    public RespPageBean getSupplierPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            Supplier supplier,
            LocalDate[] beginDateScope) {

        return supplierService.getSupplierByPage(currentPage, size, supplier, beginDateScope);
    }
    @ApiOperation("获取所有用户")
    @GetMapping("/admin")
    public List<Admin> getAllAdmin() {
        return adminService.getAllAdmins();
    }
    @ApiOperation("获取供应商编号")
    @GetMapping("/maxID")
    public RespBean maxId() {

        return supplierService.maxID();
    }
    @ApiOperation("添加供应商")
    @PostMapping("/")
    public RespBean addSup(@RequestBody Supplier supplier) {

        return supplierService.addSup(supplier);
    }
    @ApiOperation("更新供应商")
    @PutMapping("/")
    public RespBean updateSup(@RequestBody Supplier supplier) {
        if (supplierService.updateById(supplier)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
    @ApiOperation("删除供应商")
    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id) {

        if (supplierService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
