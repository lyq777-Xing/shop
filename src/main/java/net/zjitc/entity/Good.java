package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("sp_goods")
public class Good {
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goods_id;
    //    @JsonProperty("goods_name")
    @TableField("goods_name")
    private String goods_name;
    @TableField("goods_price")
    private Double goods_price;
    @TableField("goods_number")
    private Integer goods_number;
    @TableField("goods_in_price")
    private Integer goods_in_price;
    @TableField("manufacture_date")
    private Date manufacture_date;
    @TableField("quality_date")
    private String quality_date;
    @TableField("goods_in_date")
    private Date goods_in_date;
    @TableField("SupplierService_id")
    private Integer SupplierService_id;

    public void setGoodsId(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoodsName(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoodsPrice(Double goods_price) {
        this.goods_price = goods_price;
    }

    public void setGoodsNumber(Integer goods_number) {
        this.goods_number = goods_number;
    }

    public void setGoodsInPrice(Integer goods_in_price) {
        this.goods_in_price = goods_in_price;
    }

    public void setManufactureDate(Date manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public void setQualityDate(String quality_date) {
        this.quality_date = quality_date;
    }

    public void setGoodsInDate(Date goods_in_date) {
        this.goods_in_date = goods_in_date;
    }

    public void setSupplierServiceId(Integer supplierService_id) {
        SupplierService_id = supplierService_id;
    }
}
