package com.example.supermarket.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.supermarket.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.supermarket.pojo.Supplier;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin> getAllAdmins();
    IPage<Admin> getPage(Page<Admin> page,
                                    @Param("keywords") String keywords,
                                    @Param("beginDateScope") LocalDate[] beginDateScope);
}
