package net.zjitc.service.impl;

import net.zjitc.service.UserRoleVoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserRoleVoServiceImplTest {
//    @Qualifier("userRoleVoServiceImpl")
    @Autowired
    private UserRoleVoService userRoleVoServicel;
    @Autowired
    private IUserDetailsService userDetailsService;

    @Test
    public void test(){
        String authorityInfo = userRoleVoServicel.getAuthorityInfo(509);
        System.out.println(authorityInfo);
    }

    @Test
    public void test1(){
        List<GrantedAuthority> userAuthority = userDetailsService.getUserAuthority(509);
        System.out.println(userAuthority);
    }

}
