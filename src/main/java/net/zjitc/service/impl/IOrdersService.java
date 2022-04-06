package net.zjitc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Orders;
import net.zjitc.mapper.OrdersMapper;
import net.zjitc.service.OrdersService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
@Transactional
public class IOrdersService implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 查询所有订单
     * @param pagenum
     * @param pagesize
     * @return
     */
    @Override
    public Page<Orders> findAllOrders(Integer pagenum,Integer pagesize) {
        Page<Orders> ordersPage = new Page<>(pagenum,pagesize);
        ordersPage = ordersMapper.selectPage(ordersPage,null);
        return ordersPage;
    }

    /**
     * 根据id修改订单状态
     * @param orders
     * @return
     */
    @Override
    public Orders changeOrderStatus(Orders orders) {
        int i = ordersMapper.updateById(orders);
        Orders selectById = ordersMapper.selectById(orders.getId());
        return selectById;
    }

    /**
     * 根据id删除订单
     * @param id
     */
    @Override
    public void deleteOrderById(Integer id) {
        int i = ordersMapper.deleteById(id);
    }
}
