package net.zjitc.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import net.zjitc.entity.Menus;

import java.util.List;


@Data
public class MenusVo extends Menus {
    private String path;
    @TableField(exist = false)
    private List<MenusVo> children;
    public void setPath(String path) {
        this.path = path;
    }
}
