package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Value;

@Data
@TableName("sp_orders")
public class Orders {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("supplier_id")
    private Integer supplier_id;
    @TableField("order_status")
    private String order_status;
    @TableField("order_title")
    private String order_title;
    @TableField("order_id")
    private String order_id;

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSupplierId(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public void setOrderStatus(String order_status) {
        this.order_status = order_status;
    }

    public void setOrderTitle(String order_title) {
        this.order_title = order_title;
    }
}
