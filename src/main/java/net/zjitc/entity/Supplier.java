package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@TableName("sp_supplier")
public class Supplier {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("supplier_name")
    private String supplier_name;
}
