package net.zjitc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Good;
import net.zjitc.exception.CatException;

import java.util.List;

public interface GoodService {

    /**
     * 商品列表数据（list）
     * @return
     */
    List<Good> findGoodsList();

    /**
     * 商品列表数据（page）
     * @param pagenum
     * @param pagesize
     * @return
     */
    Page<Good> findGoodsPage(Integer pagenum,Integer pagesize);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    Good selectGoodById(Integer id);

    /**
     * 添加商品
     * @param good
     * @return
     * @throws CatException
     */
    Good addGood(Good good) throws CatException;

    /**
     * 根据name查询good
     * @param name
     * @return
     */
    Good selectGoodByGoodName(String name);

    /**
     * 编辑用户
     * @param id
     * @param good
     * @return
     */
    Good updateGoodById(Integer id,Good good);

    /**
     * 根据id删除商品
     * @param id
     */
    void deleteGoodById(Integer id);


    /**
     * 根据商品name模糊查询
     * @param name
     * @param pagenum
     * @param pagesize
     * @return
     */
    Page<Good> selectLikeGoodByGoodName(String name,Integer pagenum,Integer pagesize);

}
