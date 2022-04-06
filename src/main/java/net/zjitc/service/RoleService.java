package net.zjitc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Role;
import net.zjitc.entity.vo.PermissionVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
//    Page<RolePermissionVo> findAll(Integer pagenum, Integer pagesize);

    void addUser(Role role);

    List<Role> findSY();

    Role findById(Integer id);

    Role edit(Role role);

    Role deleteById(Integer id);

    List<Role> findName(Role role);

    void addRights(Integer id, String rights);

    List<Role> deleteByPid(Integer id, String pid);

    void deleterightsid(Integer roleid, String rightid);

    void setRight(Integer roleid, String rids);
}
