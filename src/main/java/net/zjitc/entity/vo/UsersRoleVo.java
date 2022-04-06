package net.zjitc.entity.vo;

import lombok.Data;
import net.zjitc.entity.Users;

@Data
public class UsersRoleVo extends Users {
    private String role_name;

    public void setRoleName(String role_name) {
        this.role_name = role_name;
    }

}
