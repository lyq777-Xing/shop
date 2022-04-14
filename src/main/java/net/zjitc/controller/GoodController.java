package net.zjitc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.zjitc.common.ResponseResult;
import net.zjitc.entity.Good;
import net.zjitc.entity.Users;
import net.zjitc.exception.CatException;
import net.zjitc.service.GoodService;
import net.zjitc.service.UserRoleVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Api(tags = "商品相关接口", description = "提供商品相关的Rest API")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @Autowired
    private UserRoleVoService userRoleVoService;

    /**
     * 获取所有商品信息（带分页）
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    @ApiOperation(value = "获取所有商品信息",notes = "带分页")
    @PreAuthorize("hasAuthority('getAllGoods')")
    @GetMapping("/goods")
    public ResponseResult findGoods(
            @RequestParam(required = false) String query,
            @RequestParam(required = true) Integer pagenum,
            @RequestParam(required = true) Integer pagesize
    ){
        ResponseResult<Object> result = new ResponseResult<>();
        if(pagenum == null && pagesize == null){
            result.BadRequest("请输入正确的分页参数",null);
        }else if(pagenum != null && pagesize != null && query == null){
            Page<Good> goodsPage = goodService.findGoodsPage(pagenum, pagesize);
            result.Success("获取成功",goodsPage);
        }else if(pagenum != null && pagesize != null && query != null){
            Page<Good> goodPage = goodService.selectLikeGoodByGoodName(query, pagenum, pagesize);
            result.Success("获取成功",goodPage);
        }else {
            result.BadRequest("请输入正确的参数",null);
        }
        return result;
    }

    /**
     * 通过商品id查询商品
     * @param id
     * @return
     */
    @ApiOperation(value = "通过商品id查询商品",notes = "id存放于url中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true)
    })
    @PreAuthorize("hasAuthority('getGoodById')")
    @GetMapping("/goods/{id}")
    public ResponseResult findGoodById(@PathVariable("id") Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        Good good = goodService.selectGoodById(id);
        if(good == null){
            result.BadRequest("该商品不存在，请输入正确的id",null);
        }else {
            result.Success("查询成功",good);
        }
        return result;
    }

    /**
     * 添加商品
     * @param good
     * @return
     */
    @ApiOperation(value = "添加商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Good", value = "Goodd对象", required = true)
    })
    @PreAuthorize("hasAuthority('createGood')")
    @PostMapping("/goods")
    public ResponseResult addGood(@RequestBody Good good){
        ResponseResult<Object> result = new ResponseResult<>();
        if(good.getGoods_name() == null || good.getGoods_name().equals("") ||
            good.getGoods_price() == null || good.getGoods_price().equals("")||
            good.getGoods_number() == null || good.getGoods_number().equals("")
        ){
            result.BadRequest("请输入正确的参数",null);
        }
        else {
            try {
//                判断用户名是否已经存在
                Good selectGoodByGoodName = goodService.selectGoodByGoodName(good.getGoods_name());
                if(selectGoodByGoodName != null){
                    result.BadRequest("请输入正确的name");
                }else {
//                    判断供货商是否存在
                    Users supplier = userRoleVoService.findSupplierById(good.getSupplierService_id());
                    if(supplier == null){
                        result.BadRequest("请输入正确的供货商id");
                    }else {
                        Good good1 = goodService.addGood(good);
                        result.Create("创建商品成功",good1);
                    }
                }
            } catch (CatException e) {
                e.printStackTrace();
                result.BadRequest("创建商品失败");
            }
        }
        return result;
    }


    /**
     * 更新商品信息
     * @param id
     * @param good
     * @return
     */
    @ApiOperation(value = "更新商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Good", value = "Good对象", required = true)
    })
    @PreAuthorize("hasAuthority('updateGood')")
    @PutMapping("/goods/{id}")
    public ResponseResult updateGoodById(@PathVariable("id") Integer id,@RequestBody Good good){
        ResponseResult<Object> result = new ResponseResult<>();
        if(good.getGoods_name() == null || good.getGoods_name().equals("")||
        good.getGoods_price() == null || good.getGoods_price().equals("")||
        good.getGoods_number() == null || good.getGoods_number().equals("")){
            result.BadRequest("请输入正确的参数",null);
        }
        else {
            Good good1 = goodService.selectGoodById(id);
            if(good1 == null){
                result.BadRequest("请输入正确的id，该id对应的商品不存在",null);
            }else {
//                判断用户是否修改了商品名
                if(good1.getGoods_name().equals(good.getGoods_name())){
//                    表明未修改
                    Good good2 = goodService.updateGoodById(id, good);
                    result.Success("修改商品信息成功",good2);
                }else {
                    Good good3 = goodService.selectGoodByGoodName(good.getGoods_name());
                    if(good3 != null){
                        result.BadRequest("该商品名称已经存在",null);
                    }else {
                        Good good2 = goodService.updateGoodById(id, good);
                        result.Success("修改商品信息成功",good2);
                    }
                }

            }
        }
        return result;
    }

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true)
    })
    @PreAuthorize("hasAuthority('deleteGood')")
    @DeleteMapping("/goods/{id}")
    public ResponseResult deleteGoodById(@PathVariable("id") Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        Good good = goodService.selectGoodById(id);
        if(good == null){
            result.BadRequest("请输入正确的id",null);
        }else {
            goodService.deleteGoodById(id);
            Good good1 = goodService.selectGoodById(id);
            if(good1 == null){
                result.Delete("删除成功",null);
            }else {
                result.BadRequest("删除失败",null);
            }
        }
        return result;
    }


}
