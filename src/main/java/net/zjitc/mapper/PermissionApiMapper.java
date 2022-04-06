package net.zjitc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.zjitc.entity.PermissionApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionApiMapper extends BaseMapper<PermissionApi> {
/*    *//**
     * 通过ps_id查询用户的课操作权限名称
     *//*
    List<String> findPsApiActionById(Integer id);*/
}
