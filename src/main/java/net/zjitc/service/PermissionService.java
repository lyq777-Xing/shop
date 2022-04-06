package net.zjitc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.zjitc.entity.Permission;
import net.zjitc.entity.vo.PermissionVo;

import java.util.List;

public interface PermissionService {
    public List<PermissionVo> findPermissionList();
    public List<PermissionVo> findPermissionTree();
    public List<PermissionVo> findMenus();
    List<PermissionVo> findByRolePid(List<String> list);
}
