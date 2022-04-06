
package net.zjitc.entity.vo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import net.zjitc.entity.Permission;

import java.util.List;

@Data
public class PermissionVo extends Permission {
    @TableField("ps_api_path")
    private String path;
    @TableField(exist = false)
    private List<PermissionVo> children;
}
