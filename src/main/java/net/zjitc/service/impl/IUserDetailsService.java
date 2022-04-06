package net.zjitc.service.impl;

import net.zjitc.entity.Users;
import net.zjitc.model.ManageUserDetails;
import net.zjitc.service.UserRoleVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRoleVoService userRoleVoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        从数据库中查询用户
        Users users = userRoleVoService.findByName(username);
        if(users == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
//        查询当前用户权限
        //todo

        ManageUserDetails details = new ManageUserDetails(users.getUsername(), users.getPassword(), getUserAuthority(users.getId()));
        return details;
    }

    /**
     * 查询当前用户权限
     * @param id
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(int id){
        String authority = userRoleVoService.getAuthorityInfo(id);
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
        return authorityList;
    }
}
