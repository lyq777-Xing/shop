package net.zjitc.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Good;

import net.zjitc.exception.CatException;
import net.zjitc.mapper.GoodMapper;
import net.zjitc.mapper.SupplierMapper;
import net.zjitc.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class IGoodService implements GoodService {
    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public List<Good> findGoodsList() {
        List<Good> goodList = goodMapper.selectList(null);
        return goodList;
    }

    @Override
    public Page<Good> findGoodsPage(Integer pagenum,Integer pagesize) {
        Page<Good> page = new Page<>(pagenum, pagesize);
        Page<Good> goodPage = goodMapper.selectPage(page, null);
        return goodPage;
    }

    @Override
    public Good selectGoodById(Integer id) {
        Good good = goodMapper.selectById(id);
        return good;
    }

    @Override
    public Good addGood(Good good) throws CatException {
        goodMapper.insert(good);
        return good;
    }

    @Override
    public Good selectGoodByGoodName(String name) {
        QueryWrapper<Good> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_name",name);
        Good good = goodMapper.selectOne(wrapper);
        return good;
    }

    @Override
    public Good updateGoodById(Integer id, Good good) {
        good.setGoods_id(id);
        goodMapper.updateById(good);
        Good selectById = goodMapper.selectById(id);
        return selectById;
    }

    @Override
    public void deleteGoodById(Integer id) {
        goodMapper.deleteById(id);
    }


    @Override
    public Page<Good> selectLikeGoodByGoodName(String name,Integer pagenum,Integer pagesize) {
        QueryWrapper<Good> wrapper = new QueryWrapper<>();
        wrapper.like("goods_name",name);
        Page<Good> goodPage = new Page<>(pagenum,pagesize);
        Page<Good> goods = goodMapper.selectPage(goodPage, wrapper);
        return goods;

    }


}
