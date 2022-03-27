package com.example.supermarket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.supermarket.pojo.Goods;
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
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<Goods> getGoodsPage(Page<Goods> page,
                                 @Param("goods") Goods goods,
                                 @Param("beginDateScope") LocalDate[] beginDateScope);
}
