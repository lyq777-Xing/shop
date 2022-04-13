package net.zjitc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.PermissionApi;
import net.zjitc.entity.Role;
import net.zjitc.entity.Users;
import net.zjitc.entity.vo.UsersRoleVo;
import net.zjitc.mapper.PermissionApiMapper;
import net.zjitc.mapper.RoleMapper;
import net.zjitc.mapper.UsersRoleVomapper;
import net.zjitc.service.UserRoleVoService;
import net.zjitc.utils.RedisUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class UserRoleVoServiceImpl implements UserRoleVoService {
    @Autowired
    private UsersRoleVomapper usersRoleVomapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionApiMapper permissionApiMapper;

    @Autowired
    private RedisUtils redisUtils;

//    @Autowired
//    private PasswordEncoder bCryptPasswordEncoder;


    @Override
    public Page<UsersRoleVo> findAll(Integer pagenum, Integer pagesize,String username) {
        Page<UsersRoleVo> page = new Page<>(pagenum,pagesize);
        QueryWrapper<UsersRoleVo> wrapper=new QueryWrapper<>();

        Page<UsersRoleVo> all = usersRoleVomapper.findAll(page,username);


        for (UsersRoleVo f:all.getRecords()) {

            f.setPassword(null);

        }


        return all;
    }

    @Override
    public Users findByName(String name) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("mg_name",name);
        Users users = usersRoleVomapper.selectOne(wrapper);
        return users;
    }

    @Override
    public List<Users> findByUsername(Users users) {
        QueryWrapper<Users> wrapper=new QueryWrapper<>();
        wrapper.eq("mg_name",users.getUsername());
        List<Users> users1 = usersRoleVomapper.selectList(wrapper);
        String j=users.getPassword();

        for (Users a:users1) {
            if(a.getUsername()!=null){
                if(a.getPassword().equals(j)){

                    return users1;
                }

            }
        }
        users1=null;
        return users1;
    }

//    @Override
//    public List<UsersRoleVo> findById(Integer id) {
//        List<UsersRoleVo> byId = usersRoleVomapper.findById(id);
//        return byId;
//    }

    @Override
    public List<Users> addUsers(Users users) {
        users.setCreateTime(new Date());
//        对密码进行加密
//        String encode = bCryptPasswordEncoder.encode(users.getPassword());
//        users.setPassword(encode);
        usersRoleVomapper.insert(users);
        List<Users> list=new ArrayList<>();
        list.add(users);
        return list;
    }

    @Override
    public List<Users> findByname(String username) {
        QueryWrapper<Users> wrapper=new QueryWrapper<>();
        wrapper.eq("mg_name",username);
        List<Users> users = usersRoleVomapper.selectList(wrapper);
        return users;
    }

    @Override
    public Users update(Users users) {
//        usersRoleVomapper.updateById(users);
        int i = usersRoleVomapper.updateById(users);
        return users;
    }

    @Override
    public void deleteUSer(Integer id) {
        usersRoleVomapper.deleteById(id);
    }

    @Override
    public List<Users> fp(Integer id, Integer rid) {
        Users users = usersRoleVomapper.selectById(id);
        users.setRid(rid);
        usersRoleVomapper.updateById(users);
        List<Users> list=new ArrayList<>();
        users.setPassword(null);
        users.setCreateTime(null);
        list.add(users);
        return list;
    }

    @Override
    public List<UsersRoleVo> setUsers(Integer id, Integer rid) {
        Users users = usersRoleVomapper.selectById(id);
        users.setRid(rid);
        usersRoleVomapper.updateById(users);
        List<UsersRoleVo> byId = usersRoleVomapper.findById(id);
        return byId;
    }

    @Override
    public String getAuthorityInfo(int id) {
        String authority = "";
//        获取用户name
        Users selectById = usersRoleVomapper.selectById(id);
        String username = selectById.getUsername();

//        判断是否有缓存
//        if(redisUtils.exists("GrantedAuthority:"+username)){
//            String test = (String) redisUtils.get("GrantedAuthority:"+username);
//            if(!test.equals("")){
//                authority = (String) redisUtils.get("GrantedAuthority:"+username);
//            }
//        }
//        else {
//        获取角色
            String roleNameById = usersRoleVomapper.findRoleNameById(id);
            if(roleNameById != null){
                String s = "ROLE_" + roleNameById;
                authority = s;
            }
            Users users = usersRoleVomapper.selectById(id);
//        获取菜单操作权限
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",users.getRid());
            Role role = roleMapper.selectOne(queryWrapper);
            if(role != null){
                String[] strings = role.getPid().split(",");
                if(strings.length > 0){
                    for (int i = 0; i < strings.length; i++) {
                        QueryWrapper<PermissionApi> wrapper = new QueryWrapper<>();

                        wrapper.eq("ps_id",strings[i]);
                        PermissionApi permissionApi = permissionApiMapper.selectOne(wrapper);
                        if(permissionApi != null){
                            if(permissionApi.getPs_api_action() != null){
                                authority = authority + "," + permissionApi.getPs_api_action();
                            }
                        }
                    }
                }
            }

            redisUtils.set("GrantedAuthority:"+username ,authority, (long) (60*60));
//        }
        return authority;
    }

    @Override
    public void claerAuthorityByRoleId(Integer roleId) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        List<Users> users = usersRoleVomapper.selectList(wrapper);
        for (Users user:users) {
            this.clearAuthorityInfo(user.getUsername());
        }
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    public List<Users> findAllUsers() {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",43).or().eq("role_id",44);
        List<Users> users = usersRoleVomapper.selectList(wrapper);
        for (Users user:users) {
            user.setPassword(null);
        }
        return users;
    }

    /**
     * 获取所有收银员
     * @return
     */
    @Override
    public List<Users> findAllCashier() {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",45);
        List<Users> users = usersRoleVomapper.selectList(wrapper);
        for (Users user:users) {
            user.setPassword(null);
        }
        return users;
    }

    /**
     * 获取所有供货商
     * @return
     */
    @Override
    public List<Users> findAllSupplier() {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",46);
        List<Users> users = usersRoleVomapper.selectList(wrapper);
        for (Users user:users) {
            user.setPassword(null);
        }
        return users;
    }

    /**
     * 获取所有管理员
     * @return
     */
    @Override
    public List<Users> findAllAdmins() {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",30).or().eq("role_id",50);
        List<Users> users = usersRoleVomapper.selectList(wrapper);
        for (Users user:users) {
            user.setPassword(null);
        }
        return users;
    }

    /**
     * 通过id查询管理员
     * @param id
     * @return
     */
    @Override
    public Users findAdminById(Integer id) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",30).eq("mg_id",id);
        Users users = usersRoleVomapper.selectOne(wrapper);
        return users;
    }

    /**
     * 通过id查询收银员
     * @param id
     * @return
     */
    @Override
    public Users findCashierById(Integer id) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",45).eq("mg_id",id);
        Users one = usersRoleVomapper.selectOne(wrapper);
        return one;
    }

    /**
     * 通过id查询普通用户或会员
     * @param id
     * @return
     */
    @Override
    public Users findSimpleById(Integer id) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",43).eq("mg_id",id).or().eq("role_id",44).eq("mg_id",id);
        Users users = usersRoleVomapper.selectOne(wrapper);
        return users;
    }

    /**
     * 通过id查询供货商
     * @param id
     * @return
     */
    @Override
    public Users findSupplierById(Integer id) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",46).eq("mg_id",id);
        Users users = usersRoleVomapper.selectOne(wrapper);
        return users;
    }

    @Override
    public void changeState(Integer id, Boolean state) {
        Users users = usersRoleVomapper.selectById(id);
//        Integer g=0;
//        if(state.equals("true")){
//            g=1;
//        }else {
//            g=0;
//        }

        usersRoleVomapper.updateById(users);
    }

    @Override
    public void clearAuthorityInfo(String username) {
        redisUtils.remove("GrantedAuthority" + username);
    }
//
//    @Override
//    public Page<UsersRoleVo> findByUserName(String username, Integer pagenum, Integer pagesize) {
//        Page<UsersRoleVo> page = new Page<>(pagenum,pagesize);
//        Page<UsersRoleVo> all = usersRoleVomapper.findAll(page,username);
//        List<UsersRoleVo> list = new ArrayList<>();
//        for (UsersRoleVo g : all.getRecords()) {
//            boolean aa=g.getUsername().contains(username);
//       if(aa==true){
//           list.add(g);
//       }
//        }
//        all.setRecords(list);
//        return all;
//    }


}
