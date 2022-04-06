package net.zjitc.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.zjitc.entity.Menus;
import net.zjitc.entity.vo.MenusVo;
import net.zjitc.mapper.MenusMapper;
import net.zjitc.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class MenusServiceImpl implements MenusService {
    @Autowired
    private MenusMapper menusMapper;
    @Override
    public List<MenusVo> findMenus() {
        //找第一层
        QueryWrapper<Menus> wrapper1=new QueryWrapper<>();
//        wrapper1.eq("ps_level",1);
        List<MenusVo> menus1 = menusMapper.findOne(null);
        //找第二层
        QueryWrapper<Menus> wrapper2=new QueryWrapper<>();
//        wrapper2.eq("ps_level",3);
        List<MenusVo> menus2 = menusMapper.findTwo(null);
        for (MenusVo one :menus1) {
            List<MenusVo> List = new ArrayList<>();
//            List<Menus> List1 = new ArrayList<>();
//            List1.get(0).setAuthName("[]");

            for (MenusVo two:menus2) {
                if(one.getId().intValue()==two.getPid().intValue()){

                    List.add(two);
                }

            }
            one.setChildren(List);
        }
        return menus1;
    }
}
