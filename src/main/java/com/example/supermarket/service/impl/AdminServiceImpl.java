package com.example.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.supermarket.mapper.AdminMapper;
import com.example.supermarket.mapper.AdminRoleMapper;
import com.example.supermarket.mapper.RoleMapper;
import com.example.supermarket.pojo.Admin;
import com.example.supermarket.pojo.AdminRole;
import com.example.supermarket.pojo.Role;
import com.example.supermarket.service.IAdminService;
import com.example.supermarket.util.AdminUtils;
import com.example.supermarket.util.JwtTokenUtils;
import com.example.supermarket.vo.RespBean;
import com.example.supermarket.vo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lsh
 * @since 2022-03-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 基于jwt的工具类,能够生成token,需要搜集username
     */
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    /**
     * 请求头:Header
     * Authentication Bearer ×××××××××××××××××××××××××××
     */
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Override
    public RespBean login(String username, String password,String code, HttpServletRequest request) {
        // 获取在生成图片的时候,保存的验证码答案
        String captcha = (String) request.getSession().getAttribute("captcha");
        // 比较客户端传递的验证码和之前保存的验证码是否一致
        if (StringUtils.isEmpty(code)||!captcha.equals(code)){
            return RespBean.error("验证码不正确!");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails==null||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或者密码不匹配!");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用,请联系管理员");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String token = jwtTokenUtils.generateToken(userDetails);
        Map<String,Object> params=new HashMap<>();
        params.put("token",token);
        params.put("tokenHead",tokenHead);
        return RespBean.success("登陆成功",params);
    }

    @Override
    public Admin findByUsername(String username) {
        /**
         * select * from
         */
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", username)
                .eq("enabled", true)
        );
        return admin;
    }
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }
    @Override
    public List<Admin> getAllAdmins() {

        Admin principal = AdminUtils.getCurrentAdmin();

        return adminMapper.getAllAdmins();
    }
    @Override
    public RespBean maxID() {
        List<Map<String, Object>> maps = adminMapper.selectMaps(new QueryWrapper<Admin>().select("max(id)"));
        String format = String.format("%d", Integer.parseInt(maps.get(0).get("max(id)").toString()) + 1);
        return RespBean.success(null, format);
    }
    @Override
    public RespPageBean getByPage(Integer currentPage, Integer size, String keywords, LocalDate[] beginDateScope) {
        Page<Admin> page = new Page<>(currentPage, size);
        IPage<Admin> result = adminMapper.getPage(page, keywords, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(result.getTotal(), result.getRecords());
        return respPageBean;
    }
    @Override
    public RespBean add(Admin admin) {
        if (adminMapper.insert(admin) == 1) {
            return RespBean.success("插入成功");
        }
        return RespBean.error("插入失败");
    }
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {

        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));

        Integer result = adminRoleMapper.addAdminRole(adminId, rids);

        if (rids.length == result) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败");

    }
}
