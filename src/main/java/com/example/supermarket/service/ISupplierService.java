package com.example.supermarket.service;

import com.example.supermarket.pojo.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.supermarket.vo.RespBean;
import com.example.supermarket.vo.RespPageBean;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
public interface ISupplierService extends IService<Supplier> {
    RespPageBean getSupplierByPage(Integer currentPage, Integer size, Supplier supplier, LocalDate[] beginDateScope);
    RespBean maxID();
    RespBean addSup(Supplier supplier);
}
