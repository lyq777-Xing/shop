package com.example.supermarket.service;

import com.example.supermarket.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.supermarket.pojo.Role;
import com.example.supermarket.vo.RespBean;
import com.example.supermarket.vo.RespPageBean;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
public interface IAdminService extends IService<Admin> {
    RespBean login(String username, String password, String code, HttpServletRequest request);
    Admin findByUsername(String username);
    List<Role> getRoles(Integer adminId);
    List<Admin> getAllAdmins();
    RespPageBean getByPage(Integer currentPage, Integer size, String keywords, LocalDate[] beginDateScope);
    RespBean maxID();
    RespBean add(Admin admin);
    RespBean updateAdminRole(Integer adminId, Integer[] rids);
}
