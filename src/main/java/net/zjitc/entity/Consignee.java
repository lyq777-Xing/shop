package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.lang.ref.PhantomReference;

@Data
@TableName("sp_consignee")
public class Consignee {
    @TableId(value = "cgn_id",type = IdType.AUTO)
    private Integer cgn_id;
    private Integer user_id;
    private String cgn_name;
    private String cgn_address;
    private String cgn_tel;
    private String cgn_code;
    private Integer delete_time;

    public void setCgnId(Integer cgn_id) {
        this.cgn_id = cgn_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public void setCgnName(String cgn_name) {
        this.cgn_name = cgn_name;
    }

    public void setCgnAddress(String cgn_address) {
        this.cgn_address = cgn_address;
    }

    public void setCgnTel(String cgn_tel) {
        this.cgn_tel = cgn_tel;
    }

    public void setCgnCode(String cgn_code) {
        this.cgn_code = cgn_code;
    }

    public void setDeleteTime(Integer delete_time) {
        this.delete_time = delete_time;
    }
}
