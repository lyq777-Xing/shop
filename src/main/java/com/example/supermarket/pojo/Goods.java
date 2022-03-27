package com.example.supermarket.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sm_goods")
@ApiModel(value="Goods对象", description="")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField("purchasing_price")
    private Float purchasingPrice;

    @TableField("selling_price")
    private Float sellingPrice;

    private Integer supplierid;

    private LocalDateTime produceddate;

    private LocalDateTime incomingdate;

    private Integer baozhiqi;

    @TableField(exist = false)
    private Supplier supplier;

}
