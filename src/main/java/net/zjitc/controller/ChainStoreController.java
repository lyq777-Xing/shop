package net.zjitc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import net.zjitc.common.ResponseResult;
import net.zjitc.entity.ChainStore;
import net.zjitc.service.ChainStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Api(tags = "连锁店相关接口", description = "提供连锁店相关的Rest API")
public class ChainStoreController {
    @Autowired
    private ChainStoreService chainStoreService;

    /**
     * 获取所有连锁店信息（不带分页）
     * @return
     */
    @ApiOperation(value = "获取所有连锁店信息",notes = "不带分页")
    @PreAuthorize("hasAuthority('getAllChainStore')")
    @GetMapping("/chainstoreall")
    public ResponseResult getAll(){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            List<ChainStore> all = chainStoreService.findAll();
            result.Success("查询成功",all);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败");
        }
        return result;
    }

    /**
     * 添加连锁店信息
     * @param chainStore
     * @return
     */
    @ApiOperation(value = "添加连锁店信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ChainStore", value = "ChainStore对象", required = true)
    })
    @PreAuthorize("hasAuthority('addChainStore')")
    @PostMapping("/chainstore/add")
    public ResponseResult add(@RequestBody ChainStore chainStore){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            ChainStore add = chainStoreService.add(chainStore);
            result.Success("添加连锁店信息成功",add);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("添加连锁店信息失败");
        }
        return result;
    }

    /**
     * 根据id删除连锁店信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除连锁店信息",notes = "id放在url中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "连锁店id", required = true)
    })
    @PreAuthorize("hasAuthority('deleteChainStore')")
    @DeleteMapping("/chainstore/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            chainStoreService.delete(id);
            result.Success("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("删除失败");
        }
        return result;
    }


    /**
     * 根据id更新连锁店信息
     * @param chainStore
     * @return
     */
    @ApiOperation(value = "根据id更新连锁店信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ChainStore", value = "ChainStore对象", required = true)
    })
    @PreAuthorize("hasAuthority('updateChainStore')")
    @PostMapping("/chainstore/update")
    public ResponseResult update(@RequestBody ChainStore chainStore){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            ChainStore update = chainStoreService.update(chainStore);
            result.Success("更新连锁店信息成功",update);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("更新失败");
        }
        return result;
    }
}
