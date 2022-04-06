package net.zjitc.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    void deleterightsid(Integer roleid, String rightid);
//    Page<RolePermissionVo> findAll(Page<RolePermissionVo> page, QueryWrapper<RolePermissionVo> onelist);
//    Page<RolePermissionVo> findAll(Page page, QueryWrapper<RolePermissionVo> wrapper);
}