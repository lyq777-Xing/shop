package net.zjitc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.zjitc.common.ResponseResult;
import net.zjitc.entity.vo.PermissionVo;
import net.zjitc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "权限相关接口", description = "提供权限相关的Rest API")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取权限列表
     * @param type
     * @return
     */
    @ApiOperation(value = "获取权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "排列方式（list or 树）", required = true)
    })
    @PreAuthorize("hasAuthority('getAllRights')")
    @GetMapping("/rights/{type}")
    public ResponseResult findPermissions(@PathVariable("type") String type){
        ResponseResult<Object> result = new ResponseResult<>();
        if(type.equals("list")){
            List<PermissionVo> list = permissionService.findPermissionList();
            result.Success("获取权限列表成功",list);
        }
        else if(type.equals("tree")){
            List<PermissionVo> tree = permissionService.findPermissionTree();
            result.Success("获取权限列表成功",tree);
        }
        else if(type == null){
            result.BadRequest("请输入参数",null);
        }
        else {
            result.BadRequest("请输入正确参数",null);
        }
        return result;
    }

//    @GetMapping("/menus")
//    public ResponseResult findMenus(){
//        ResponseResult<Object> result = new ResponseResult<>();
//        List<PermissionVo> menus = permissionService.findMenus();
//        result.Success("获取菜单列表成功",menus);
//        return result;
//    }
}
