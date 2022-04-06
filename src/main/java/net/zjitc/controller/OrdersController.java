package net.zjitc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.zjitc.common.ResponseResult;
import net.zjitc.entity.Orders;
import net.zjitc.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@Api(tags = "订单相关接口", description = "提供订单相关的Rest API")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 查询所有订单
     * @return
     */
    @ApiOperation(value = "查询所有订单",notes = "为分页类型")
    @PreAuthorize("hasAuthority('getAllOrders')")
    @GetMapping("/orders")
    public ResponseResult findAll(Integer pagenum,Integer pagesize){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            Page<Orders> allOrders = ordersService.findAllOrders(pagenum, pagesize);
            result.Success("查询成功",allOrders);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败");
            return  result;
        }
    }

    /**
     * 根据id删除订单
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true)
    })
    @PreAuthorize("hasAuthority('deleteOrder')")
    @DeleteMapping("/orders/delete/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            ordersService.deleteOrderById(id);
            result.Success("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("删除失败");
        }
        return result;
    }

    /**
     * 根据id修改订单状态
     * @param orders
     * @return
     */
    @ApiOperation(value = "根据id修改订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Orders", value = "Orders对象", required = true)
    })
    @PreAuthorize("hasAuthority('updateOrder')")
    @PostMapping("/orders/updatestatus")
    public ResponseResult updatestatus(@RequestBody Orders orders){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            Orders status = ordersService.changeOrderStatus(orders);
            result.Success("更改订单状态成功",status);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("更改订单状态失败");
        }
        return result;
    }
}
