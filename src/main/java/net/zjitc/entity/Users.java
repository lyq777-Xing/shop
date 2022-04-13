package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@TableName("sp_manager")
//@JsonInclude(JsonInclude.Include.ALWAYS)
public class Users {
    @TableId(value = "mg_id",type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private Integer id;
    @TableField("mg_name")
    private String username;
    @TableField("mg_mobile")
    private String mobile;
    @TableField("mg_pwd")
    private String password;
    @TableField("mg_email")
    private String email;
    @TableField("role_id")
    private Integer rid;
    @TableField("mg_time")
    private Date create_time;
    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String key;
    @TableField(exist = false)
    private String token;
    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

}

