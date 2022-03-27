package com.example.supermarket.mapper;

import com.example.supermarket.pojo.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenusByAdminId(@Param("id") Integer id);
    List<Menu> getMenusWithRole();
}
