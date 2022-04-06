package net.zjitc.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.zjitc.entity.Menus;
import net.zjitc.entity.vo.MenusVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MenusMapper extends BaseMapper<Menus> {
    List<MenusVo> findOne(QueryWrapper<Menus> wrapper1);

    List<MenusVo> findTwo(QueryWrapper<Menus> wrapper2);
}
