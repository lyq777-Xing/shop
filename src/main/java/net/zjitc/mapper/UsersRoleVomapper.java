package net.zjitc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Users;
import net.zjitc.entity.vo.UsersRoleVo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersRoleVomapper extends BaseMapper<Users> {
    Page<UsersRoleVo> findAll(Page<UsersRoleVo> page,String username);

    String findRoleNameById(Integer id);
    List<UsersRoleVo> findById(Integer id);

    void addUsers(Users users);
}
