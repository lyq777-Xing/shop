package com.example.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.supermarket.pojo.Supplier;
import com.example.supermarket.mapper.SupplierMapper;
import com.example.supermarket.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.supermarket.vo.RespBean;
import com.example.supermarket.vo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {
    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public RespPageBean getSupplierByPage(Integer currentPage, Integer size, Supplier supplier, LocalDate[] beginDateScope) {
        Page<Supplier> page = new Page<>(currentPage, size);
        IPage<Supplier> result = supplierMapper.getSupplierPage(page, supplier, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(result.getTotal(), result.getRecords());
        return respPageBean;
    }

    @Override
    public RespBean maxID() {
        List<Map<String, Object>> maps = supplierMapper.selectMaps(new QueryWrapper<Supplier>().select("max(id)"));
        String format = String.format("%d", Integer.parseInt(maps.get(0).get("max(id)").toString()) + 1);
        return RespBean.success(null, format);
    }

    @Override
    public RespBean addSup(Supplier supplier) {
        if (supplierMapper.insert(supplier) == 1) {
            return RespBean.success("插入成功");
        }
        return RespBean.error("插入失败");
    }
}
