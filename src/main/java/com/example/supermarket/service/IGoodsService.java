package com.example.supermarket.service;

import com.example.supermarket.pojo.Goods;
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
public interface IGoodsService extends IService<Goods> {
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Goods goods, LocalDate[] beginDateScope);
    RespBean maxID();
    RespBean addGoods(Goods goods);
}
