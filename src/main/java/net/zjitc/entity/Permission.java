package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@TableName("sp_permission")
public class Permission {
    @TableId(value = "ps_id",type = IdType.AUTO)
    private Integer id;
    @TableField("ps_name")
    private String authName;
    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    @TableField("ps_pid")
    private Integer pid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField("ps_level")
    private Integer level;
//    @TableField(exist = false)
//    private List<Permission> children;


}
