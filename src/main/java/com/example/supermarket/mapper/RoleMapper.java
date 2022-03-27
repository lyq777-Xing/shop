package com.example.supermarket.mapper;

import com.example.supermarket.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRoles(@Param("adminId") Integer adminId);
}
