package com.example.supermarket.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.supermarket.pojo.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
public interface SupplierMapper extends BaseMapper<Supplier> {
    IPage<Supplier> getSupplierPage(Page<Supplier> page,
                                 @Param("supplier") Supplier supplier,
                                 @Param("beginDateScope") LocalDate[] beginDateScope);
}
