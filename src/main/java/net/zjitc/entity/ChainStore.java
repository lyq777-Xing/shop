package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sp_chain_store")
public class ChainStore {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("address")
    private String address;
    @TableField("chreif_name")
    private String chreif_name;
    @TableField("chreif_phone")
    private String chreif_phone;

    public void setChreifName(String chreif_name) {
        this.chreif_name = chreif_name;
    }

    public void setChreifPhone(String chreif_phone) {
        this.chreif_phone = chreif_phone;
    }
}
