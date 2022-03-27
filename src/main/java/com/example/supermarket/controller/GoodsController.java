package com.example.supermarket.controller;


import com.example.supermarket.pojo.Goods;
import com.example.supermarket.pojo.Supplier;
import com.example.supermarket.service.IGoodsService;
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
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISupplierService supplierService;
    @ApiOperation("获取所有商品，分页查询")
    @GetMapping("/")
    public RespPageBean getEmployeePage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size,
            Goods goods,
            LocalDate[] beginDateScope) {

        return goodsService.getEmployeeByPage(currentPage, size, goods, beginDateScope);
    }
    @ApiOperation("获取所有供应商")
    @GetMapping("/supplier")
    public List<Supplier> getAllSupplier() {
        return supplierService.list();
    }
    @ApiOperation("获取商品编号")
    @GetMapping("/maxID")
    public RespBean maxId() {

        return goodsService.maxID();
    }
    @ApiOperation("添加商品")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Goods goods) {

        return goodsService.addGoods(goods);
    }
    @ApiOperation("更新商品")
    @PutMapping("/")
    public RespBean updateGoods(@RequestBody Goods goods) {
        if (goodsService.updateById(goods)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id) {

        if (goodsService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
