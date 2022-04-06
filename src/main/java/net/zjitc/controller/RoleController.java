package net.zjitc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.zjitc.common.ResponseResult;
import net.zjitc.entity.Role;
import net.zjitc.entity.vo.PermissionVo;
import net.zjitc.service.PermissionService;

import net.zjitc.service.RoleService;
import net.zjitc.service.UserRoleVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.ObjectView;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@Api(tags = "角色相关接口", description = "提供角色相关的Rest API")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleVoService userRoleVoService;

    /**
     * 展示角色列表
     * @return
     */
    @ApiOperation(value = "展示角色列表")
    @PreAuthorize("hasAuthority('getAllRoles')")
    @GetMapping("/roles")
    public ResponseResult findSY(){
        List<Role> roleList = roleService.findSY();
        for (Role r:roleList) {
            List<String> arrayList = new ArrayList<>();
            String[] split = r.getPid().split(",");
            for (int i = 0; i < split.length; i++) {
                arrayList.add(split[i]);
            }
            List<PermissionVo> byRolePid = permissionService.findByRolePid(arrayList);
            r.setChildren(byRolePid);
        }
        ResponseResult<Object> result=new ResponseResult<>();
        result.Success("获取成功",roleList);
        return result;

    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @ApiOperation(value = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "RoleName", value = "角色名称", required = true),
    })
    @PreAuthorize("hasAuthority('createRole')")
    @PostMapping("/roles")
    public ResponseResult addUser(@RequestBody Role role){
        System.out.println(role.getRoleName());
        ResponseResult<Object> result =new ResponseResult<>();
        if(role.getRoleName()==null||role.getRoleName()==""||role.getRoleName()==" "){
            result.BadRequest("参数有误");
            return result;
        }
        List<Role> roleList = roleService.findName(role);
        if(roleList.size()!=0){
            result.Forbidden("用户名存在");
            return result;
        }else {
            roleService.addUser(role);
            result.Create("创建成功",role);
            return result;
        }


    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
    })
    @PreAuthorize("hasAuthority('getRoleById')")
    @GetMapping("/roles/{id}")
    public ResponseResult findById(@PathVariable("id") Integer id){
        ResponseResult<Object> result=new ResponseResult<>();
        Role byId = roleService.findById(id);
        if(byId==null){
            result.Success("失败");
            return result;
        }else {
            result.Success("成功",byId);
            return result;

        }

    }

    /**
     * 修改用户
     * @param role
     * @return
     */
    @ApiOperation(value = "修改角色",notes = "rolename不能为空")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色对象", required = true),
    })
    @PreAuthorize("hasAuthority('updateRole')")
    @PutMapping("/roles/{id}")
    public ResponseResult edit(@RequestBody Role role){
        ResponseResult<Object> result=new ResponseResult<>();
        if(role.getRoleName()==null||role.getRoleName()==""||role.getRoleName()==" "){
            result.BadRequest("参数有误");
            return result;
        }
        List<Role> roleList = roleService.findName(role);
        if(roleList.size()!=0){
            result.Forbidden("用户名存在");
            return result;
        }else {
//            清除redis缓存
            userRoleVoService.claerAuthorityByRoleId(role.getId());
            roleService.edit(role);
            result.Success("获取成功",role);
            return result;
        }
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
    })
    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('deleteRole')")
    public ResponseResult deleteById(@PathVariable("id") Integer id){
        ResponseResult<Object> result=new ResponseResult<>();
        Role byId = roleService.findById(id);
        if(byId==null||id==null){
            result.Not_found("参数错误");
            return result;
        }
        Role roles = roleService.deleteById(id);
        result.Success("删除成功");
        return result;
    }

    /**
     * 角色授权
     * @param roleid
     * @param rids
     * @return
     */
    @ApiOperation(value = "角色授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "role", value = "角色对象", required = true)
    })
    @PostMapping("/roles/{roleid}/rights")
    @PreAuthorize("hasAuthority('updateRoleRight')")
    public ResponseResult setRight(@PathVariable("roleid") Integer roleid,
                                   @RequestBody Role role){
        ResponseResult<Object> result=new ResponseResult<>();
//        清除redis缓存
        userRoleVoService.claerAuthorityByRoleId(roleid);
        roleService.setRight(roleid,role.getRids());
        result.Success("更新成功");
        return result;
    }

    /**
     * 删除角色指定权限
     * @param roleid
     * @param rightid
     * @return
     */
    @ApiOperation(value = "删除角色指定权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "rightid", value = "权限id", required = true)
    })
    @DeleteMapping("/roles/{roleid}/rights/{rightid}")
    @PreAuthorize("hasAuthority('deleteRoleRight')")
    public ResponseResult deleteRights(@PathVariable("roleid") Integer roleid,
                                       @PathVariable("rightid") String rightid){
        System.out.println(roleid);
        System.out.println(rightid);
        roleService.deleterightsid(roleid,rightid);
        ResponseResult<Object> result=new ResponseResult<>();
        List<Role> roleList = roleService.findSY();
        List<PermissionVo> byRolePid=new ArrayList<>();
        for (Role r:roleList) {
            if (r.getId().intValue() == roleid.intValue()) {
                List<String> arrayList = new ArrayList<>();
                String[] split = r.getPid().split(",");
                for (int i = 0; i < split.length; i++) {
                    arrayList.add(split[i]);
                }
                byRolePid = permissionService.findByRolePid(arrayList);
            }
//            r.setChildren(byRolePid);
        }
//        清除redis缓存
        userRoleVoService.claerAuthorityByRoleId(roleid);
        result.Success("取消权限成功",byRolePid);
        return result;
    }



}
