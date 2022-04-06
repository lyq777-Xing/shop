package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import net.zjitc.entity.vo.PermissionVo;

import java.util.List;

@Data
@TableName("sp_role")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Role {
@TableId(value = "role_id",type = IdType.AUTO)
    private Integer id;
@TableField("role_name")
    private String roleName;
@TableField("role_desc")
    private String roleDesc;
@TableField("ps_ids")
@JsonIgnore
    private String pid;
@TableField(exist = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PermissionVo> children;
    @TableField(exist = false)
    private String rids;

}
