package net.zjitc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Role;

import net.zjitc.entity.vo.PermissionVo;
import net.zjitc.mapper.RoleMapper;
import net.zjitc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleResult;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    //查询总数
//    @Override
//    public Page<RolePermissionVo> findAll(Integer pagenum, Integer pagesize) {
//        Page<RolePermissionVo> page=new Page<>(pagenum,pagesize);
//        //第一层
//        QueryWrapper<RolePermissionVo> onelist=new QueryWrapper<>();
//        onelist.select("role_id","role_name");
//        Page<RolePermissionVo> page1 = roleMapper.findAll(page, onelist);
//        //第二层
//
////
////        Page<RolePermissionVo> page = new Page<>(pagenum, pagesize);
////        page = roleMapper.findAll(page);
//        return page;
//    }

    /**
     * 添加角色
     * @param role
     */
    @Override
    public void addUser(Role role) {
        roleMapper.insert(role);


    }

    /**
     * 查询role
     * @return
     */
    @Override
    public List<Role> findSY() {
        List<Role> roleList = roleMapper.selectList(null);
        return roleList;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        Role role =  roleMapper.selectById(id);
        return role;

    }

    @Override
    public Role edit(Role role) {
        List<Role> roleList = roleMapper.selectList(null);
//        for (Role r:roleList) {
//            if(r.getId()==role.getId()){
//                r.getRoleName()
//            }
//        }
        roleMapper.updateById(role);
        return role;
    }

    @Override
    public Role deleteById(Integer id) {
        roleMapper.deleteById(id);
        Role byId = roleMapper.selectById(id);
        return byId;
    }

    @Override
    public List<Role> findName(Role role) {
        QueryWrapper<Role> wrapper=new QueryWrapper<>();
        wrapper.eq("role_name",role.getRoleName());
        List<Role> roles = roleMapper.selectList(wrapper);
        return roles;
    }

    @Override
    public void addRights(Integer id, String rights) {
        Role role = roleMapper.selectById(id);
        role.setPid(role.getPid()+","+rights);
        roleMapper.updateById(role);

    }

    @Override
    public List<Role> deleteByPid(Integer id, String pid) {
        Role role = roleMapper.selectById(id);
        String[] split = role.getPid().split(",");
        List<String> list = new ArrayList<>();
        for (int i=0;i< split.length;i++){

            if(split[i].equals(pid)){
                continue;
            }else {
                list.add(split[i]);
            }



        }
        String a="";
        for (int i=0;i< list.size();i++){
            if(i<list.size()-1) {
                a = a + list.get(i) + ",";

            }else {
                a = a + list.get(i);
            }


        }
        role.setPid(a);
        roleMapper.updateById(role);
        List<Role> r= roleMapper.selectList(null);
        return r;
    }

    @Override
    public void deleterightsid(Integer roleid, String rightid) {
        roleMapper.deleterightsid(roleid,rightid);
    }

    @Override
    public void setRight(Integer roleid, String rids) {
        Role role = roleMapper.selectById(roleid);
        role.setPid(rids);
        roleMapper.updateById(role);
    }
}
