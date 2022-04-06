package net.zjitc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Orders;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrdersService {
    Page<Orders> findAllOrders(Integer pagenum,Integer pagesize);

    Orders changeOrderStatus(Orders orders);

    void deleteOrderById(Integer id);

}
