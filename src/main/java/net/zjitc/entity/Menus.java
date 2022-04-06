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
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Menus {
    @TableId(value = "ps_id",type = IdType.AUTO)
    private Integer id;
    @TableField("ps_name")
    private String authName;
//    @TableField(exist = false)
//    private String path;
    @TableField("ps_pid")
    @JsonIgnore
    private Integer pid;

}
