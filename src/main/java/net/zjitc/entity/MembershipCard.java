package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@TableName("sp_membership_card")
public class MembershipCard {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("manager_id")
    private Integer manager_id;
    @TableField("point")
    private Integer point;
    @TableField("create_time")
    private Date create_time;

    public void setManagerId(Integer manager_id) {
        this.manager_id = manager_id;
    }

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }
}
