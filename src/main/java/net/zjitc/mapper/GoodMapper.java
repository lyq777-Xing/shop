package net.zjitc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.zjitc.entity.Good;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    //    根据id查询商品
//    Good selectGoodById(Integer id);
}
