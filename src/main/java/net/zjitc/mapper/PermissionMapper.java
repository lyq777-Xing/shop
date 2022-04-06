package net.zjitc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.zjitc.entity.Permission;
import net.zjitc.entity.vo.PermissionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 权限管理(List)
     * @return
     */
    List<PermissionVo> findPermissionList();

    /**
     * 权限管理（tree）
     * @param level
     * @return
     */
    List<PermissionVo> findPermissionTree(Integer level);

}
