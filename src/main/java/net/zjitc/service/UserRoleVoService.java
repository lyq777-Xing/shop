package net.zjitc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import net.zjitc.entity.Users;
import net.zjitc.entity.vo.UsersRoleVo;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserRoleVoService {
    Page<UsersRoleVo> findAll(Integer pagenum, Integer pagesize,String username);

    Users findByName(String name);

    void changeState(Integer id, Boolean state);

    void clearAuthorityInfo(String username);

    List<Users> findByUsername(Users users);

//    List<UsersRoleVo> findById(Integer id);

    List<Users> addUsers(Users users);

    List<Users> findByname(String username);

//    List<Users> update(Users users);

    void deleteUSer(Integer id);

    List<Users> fp(Integer id, Integer rid);

    List<UsersRoleVo> setUsers(Integer id, Integer rid);

    String getAuthorityInfo(int id);

    void claerAuthorityByRoleId(Integer roleId);

    List<Users> findAllUsers();

    List<Users> findAllCashier();

    List<Users> findAllSupplier();

    List<Users> findAllAdmins();

    Users findAdminById(Integer id);

    Users findCashierById(Integer id);

    Users findSimpleById(Integer id);

    Users findSupplierById(Integer id);

    Users update(Users users);


}
