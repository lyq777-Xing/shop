package net.zjitc.service.impl;

import net.zjitc.entity.Consignee;
import net.zjitc.mapper.ConsigneeMapper;
import net.zjitc.service.ConsigneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class IConsigneeService implements ConsigneeService {
    @Autowired
    private ConsigneeMapper consigneeMapper;
    @Override
    public Consignee updateById(Integer id, String addr) {
        Consignee consignee = consigneeMapper.selectById(id);
        consignee.setCgn_address(addr);
        consigneeMapper.updateById(consignee);
        return consignee;
    }

    @Override
    public Consignee findConsigneeById(Integer id) {
        Consignee consignee = consigneeMapper.selectById(id);
        return consignee;
    }
}
