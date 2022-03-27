package com.example.supermarket.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("sm_vipcard")
@ApiModel(value="Vipcard对象", description="")
public class Vipcard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vipcardid", type = IdType.AUTO)
    private Integer vipcardid;

    private Integer holderid;

    private LocalDateTime creationdate;

    private Integer integral;


}
