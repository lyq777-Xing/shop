package net.zjitc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sp_permission_api")
@Data
public class PermissionApi {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer ps_id;
    private String ps_api_service;
    private String ps_api_action;
    private String ps_api_path;
    private Integer ps_api_order;

    public void setPsId(Integer ps_id) {
        this.ps_id = ps_id;
    }

    public void setPsApiService(String ps_api_service) {
        this.ps_api_service = ps_api_service;
    }

    public void setPsApiAction(String ps_api_action) {
        this.ps_api_action = ps_api_action;
    }

    public void setPsApiPath(String ps_api_path) {
        this.ps_api_path = ps_api_path;
    }

    public void setPsApiOrder(Integer ps_api_order) {
        this.ps_api_order = ps_api_order;
    }
}
