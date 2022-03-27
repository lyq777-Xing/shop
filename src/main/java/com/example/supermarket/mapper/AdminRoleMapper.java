package com.example.supermarket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.supermarket.pojo.AdminRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
