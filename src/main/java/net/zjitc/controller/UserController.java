package net.zjitc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import net.zjitc.common.ResponseResult;

import net.zjitc.entity.Users;
import net.zjitc.entity.vo.MenusVo;
import net.zjitc.entity.vo.UsersRoleVo;
import net.zjitc.service.MenusService;
import net.zjitc.service.UserRoleVoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;



@CrossOrigin
@RestController
@Api(tags = "用户相关接口", description = "提供用户相关的Rest API")
public class UserController {
    @Autowired
    private UserRoleVoService userRoleVoService;

    @Autowired
    private MenusService menusService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 登录
     * @param username
     * @param password
     * @param code
     * @param key
     * @return
     */
    @ApiOperation(value = "登录", notes = "手机号、密码都是必输项、code(验证码)、key（验证码的key）")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "form"),
             @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "form"),
             @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "form"),
             @ApiImplicitParam(name = "key", value = "key", required = true, paramType = "form")
     })
    @PostMapping("/login")
    public ResponseResult findByUsername(String username,String password,String code,String key){
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        users.setCode(code);
        users.setKey(key);
        List<Users> byUsername = userRoleVoService.findByUsername(users);
        ResponseResult<Object> result=new ResponseResult<>();
        if(byUsername!=null){
            result.Success("登录成功",byUsername);
            return result;
        }else {
            result.Not_found("用户不存在");
            return result;
        }
    }

    /**
     * 获取菜单列表
     * @return
     */
    @ApiOperation(value = "获取菜单列表")
    @GetMapping("/menus")
    public ResponseResult findMenus(){
        List<MenusVo> menus = menusService.findMenus();
        ResponseResult<Object> result=new ResponseResult<>();
        result.Success("获取菜单列表成功",menus);
        return result;
    }


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
//    @ApiOperation(value = "根据id查询用户", notes = "参数为用户id")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户id", required = true)
//    })
//    @PreAuthorize("hasAuthority('getManager')")
//    @GetMapping("/users/{id}")
//    public ResponseResult findById(@PathVariable("id") Integer id){
//
//        List<UsersRoleVo> byId = userRoleVoService.findById(id);
//        ResponseResult<Object> result=new ResponseResult<>();
//        result.Success("查询成功",byId);
//        return result;
//
//
//    }

    /**
     * 添加用户
     * @param users
     * @return
     */
    @ApiOperation(value = "添加用户", notes = "username、password都是必输项、角色id非必须默认为普通用户）注意密码存入数据库需加密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "body"),
            @ApiImplicitParam(name = "role_id", value = "设置角色", required = false)
    })
    @PreAuthorize("hasAuthority('createManager')")
    @PostMapping("/users")
    public ResponseResult addUsers(@RequestBody Users users){
        System.out.println(users);
        ResponseResult<Object> result=new ResponseResult<>();
        List<Users> byname = userRoleVoService.findByname(users.getUsername());
        if(byname.size()!=0){
            result.Not_found("用户名已经存在");
            return result;
        }else {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            List<Users> users1 = userRoleVoService.addUsers(users);
            result.Create("用户创建成功",users1);
            return result;
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户",notes = "用户id放在url请求中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true)
    })
    @PreAuthorize("hasAuthority('deleteManager')")
    @DeleteMapping("/users/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Integer id){


        userRoleVoService.deleteUSer(id);
        ResponseResult<Object> result=new ResponseResult<>();
        result.Success("删除成功");
        return result;
    }

    /**
     * 分配用户角色
     * @param id
     * @param users
     * @return
     */
    @ApiOperation(value = "分配用户角色",notes = "用户id放在url请求中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "user", value = "users", required = true)
    })
    @PreAuthorize("hasAuthority('setRole')")
    @PutMapping("/users/{id}/role")
    public ResponseResult fp(@PathVariable("id") Integer id,@RequestBody UsersRoleVo users){

        System.out.println(id);
        System.out.println(users.getRid());

        ResponseResult<Object> result=new ResponseResult<>();
        if(id==null||users.getRid()==null){
            result.Not_found("参数错误");
            return result;
        }
        List<UsersRoleVo> list = userRoleVoService.setUsers(id, users.getRid());
        result.Success("设置角色成功",list);
        return result;
    }


    /**
     * 查询所有普通及会员用户
     * @return
     */
    @ApiOperation(value = "查询所有普通及会员用户")
    @PreAuthorize("hasAuthority('getAllManagers')")
    @GetMapping("/users/simple")
    public ResponseResult findAllUsers(){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            List<Users> allUsers = userRoleVoService.findAllUsers();
            result.Success("查询成功",allUsers);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败",e.getMessage());
        }
        return result;
    }

    /**
     * 查询所有收银员
     * @return
     */
    @ApiOperation(value = "查询所有收银员")
    @PreAuthorize("hasAuthority('getAllCashier')")
    @GetMapping("/users/cashier")
    public ResponseResult getAllCashier(){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            List<Users> all = userRoleVoService.findAllCashier();
            result.Success("查询成功",all);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败",e.getMessage());
        }
        return result;
    }

    /**
     * 查询所有供货商
     * @return
     */
    @ApiOperation(value = "查询所有供货商")
    @PreAuthorize("hasAuthority('getAllSupplier')")
    @GetMapping("/users/supplier")
    public ResponseResult getAllSupplier(){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            List<Users> all = userRoleVoService.findAllSupplier();
            result.Success("查询成功",all);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败",e.getMessage());
        }
        return result;
    }
    /**
     * 查询所有管理员
     * @return
     */
    @ApiOperation(value = "查询所有管理员")
    @PreAuthorize("hasAuthority('getAllAdministrators')")
    @GetMapping("/users/administrators")
    public ResponseResult getAllAdministrators(){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            List<Users> all = userRoleVoService.findAllAdmins();
            result.Success("查询成功",all);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败",e.getMessage());
        }
        return result;
    }

    /**
     * 根据id查询管理员
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
    })
    @PreAuthorize("hasAuthority('getAllAdministrators')")
    @GetMapping("/users/findadminbyid")
    public ResponseResult findAdminById(Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            Users admin = userRoleVoService.findAdminById(id);
            if(admin == null){
                result.BadRequest("该管理员不存在");
            }else {
                List<Users> list=new ArrayList<>();
                list.add(admin);
                result.Success("查询成功",list);
            }
        }catch (Exception e) {
            e.printStackTrace();
            result.BadRequest("查询失败");
        }
        return result;
    }


    /**
     * 根据id查询收银员
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询收银员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
    })
    @PreAuthorize("hasAuthority('getAllCashier')")
    @GetMapping("/users/findCashierById")
    public ResponseResult findCashierById(Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            Users cashier = userRoleVoService.findCashierById(id);
            if(cashier == null){
                result.BadRequest("该收银员不存在");
            }else {
                List<Users> list=new ArrayList<>();
                list.add(cashier);
                result.Success("查询成功",list);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败");
        }
        return result;
    }

    /**
     * 根据id查询普通用户和会员用户
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询普通用户和会员用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
    })
    @PreAuthorize("hasAuthority('getAllManagers')")
    @GetMapping("/users/findSimpleById")
    public ResponseResult findSimpleById(Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            Users users = userRoleVoService.findSimpleById(id);
            if(users == null){
                result.BadRequest("该用户不存在");
            }else {
                List<Users> list=new ArrayList<>();
                list.add(users);
                result.Success("查询成功",list);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败");
        }
        return result;
    }

    /**
     * 根据id查询供货商
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询供货商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
    })
    @PreAuthorize("hasAuthority('getAllSupplier')")
    @GetMapping("/users/findSupplierById")
    public ResponseResult findSupplierById(Integer id){
        ResponseResult<Object> result = new ResponseResult<>();
        try{
            Users users = userRoleVoService.findSupplierById(id);
            if(users == null){
                result.BadRequest("该供货商不存在");
            }else {
                result.Success("查询成功",users);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("查询失败");
        }
        return result;
    }

    /**
     * 更新用户
     * @param
     * @return
     */
    @ApiOperation(value = "根据id修改用户")
    @PreAuthorize("hasAuthority('updateManager')")
    @PutMapping("/users/update")
    public ResponseResult update(@RequestBody Users users){
        ResponseResult<Object> result = new ResponseResult<>();
        try {
            Users update = userRoleVoService.update(users);
            result.Success("更新成功",update);
        }catch (Exception e){
            e.printStackTrace();
            result.BadRequest("更新失败");
        }
        return result;
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @ApiOperation(value = "删除管理员",notes = "用户id放在url请求中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true)
    })
    @PreAuthorize("hasAuthority('deleteAdmin')")
    @DeleteMapping("/admin/{id}")
    public ResponseResult deleteAdmin(@PathVariable("id") Integer id){
        userRoleVoService.deleteUSer(id);
        ResponseResult<Object> result=new ResponseResult<>();
        result.Success("删除成功");
        return result;
    }
}
