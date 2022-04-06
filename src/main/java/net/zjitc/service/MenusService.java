package net.zjitc.service;

import net.zjitc.entity.Menus;
import net.zjitc.entity.vo.MenusVo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MenusService {
    List<MenusVo> findMenus();
}
