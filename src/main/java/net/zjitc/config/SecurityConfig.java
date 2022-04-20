package net.zjitc.config;

import net.zjitc.filter.CaptchaFilter;
import net.zjitc.filter.JwtAuthicationFilter;
import net.zjitc.handler.*;
import net.zjitc.service.impl.IUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailtureHandler loginFailtureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private IUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;


    //    不需要拦截的静态资源
    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/upload",
            "/favicon.ico",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/doc.html" ,
    };

    /**
     * 注入密码编码方式的bean
     * @return
     */
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtAuthicationFilter jwtAuthicationFilter() throws Exception {
        JwtAuthicationFilter filter = new JwtAuthicationFilter(authenticationManager());
        return filter;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
//        解决安全框架的跨域问题
        http.cors().and().csrf().disable();

//        配置在线api
        http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/*","/swagger-resources/configuration/ui").permitAll();

//        登录配置
        http.formLogin()
//                登录成功处理器
                .successHandler(loginSuccessHandler)
//                登录失败处理器
                .failureHandler(loginFailtureHandler)
                .and()
//                设置退出处理器
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
                .and()
//                禁用session规则 即不产生session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                配置拦截器
                .authorizeHttpRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()
//                异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .addFilter(jwtAuthicationFilter())
//                在验证账号密码之前校验验证码
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
