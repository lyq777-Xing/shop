package net.zjitc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.zjitc.entity.Orders;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrdersService extends IService<Orders> {
    Page<Orders> findAllOrders(Integer pagenum,Integer pagesize);

    Orders changeOrderStatus(Orders orders);

    void deleteOrderById(Integer id);

    Orders addOrder(Orders orders);

    Orders findById(Integer id);

}
