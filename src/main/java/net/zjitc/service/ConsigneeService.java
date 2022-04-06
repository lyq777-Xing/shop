package net.zjitc.service;

import net.zjitc.entity.Consignee;

import java.util.List;

public interface ConsigneeService {

    /**
     * 通过id修改订单地址
     * @param id
     * @param addr
     * @return
     */
    Consignee updateById(Integer id,String addr);

    /**
     * 通过id查询收货人信息是否存在
     * @param id
     * @return
     */
    Consignee findConsigneeById(Integer id);
}
