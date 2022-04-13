package net.zjitc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.zjitc.entity.Orders;
import net.zjitc.entity.Users;
import net.zjitc.mapper.OrdersMapper;
import net.zjitc.service.OrdersService;
import net.zjitc.service.UserRoleVoService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
@Transactional
public class IOrdersService extends ServiceImpl<OrdersMapper,Orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserRoleVoService userRoleVoService;


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
//        根据供货商id查询供货商
        for (int i = 0; i < ordersPage.getRecords().size(); i++) {
            Orders orders = ordersPage.getRecords().get(i);
            Integer supplier_id = orders.getSupplier_id();
            Users supplierById = userRoleVoService.findSupplierById(supplier_id);
            if(supplierById != null){
                ordersPage.getRecords().get(i).setSupplier(supplierById.getUsername());
            }
        }
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

    /**
     * 添加订单
     * @param orders
     */
    @Override
    public Orders addOrder(Orders orders) {
        Users supplier = userRoleVoService.findSupplierById(orders.getSupplier_id());
        if(supplier == null){
            return null;
        }
        int insert = ordersMapper.insert(orders);
        return orders;
    }

    @Override
    public Orders findById(Integer id) {
        Orders orders = ordersMapper.selectById(id);
        return orders;
    }
}
