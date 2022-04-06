package net.zjitc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.zjitc.entity.Orders;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
