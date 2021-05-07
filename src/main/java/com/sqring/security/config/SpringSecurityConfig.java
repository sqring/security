package com.sqring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * descriptions: 安全控制中心
 * author: Mr.zhou
 * date: 2021/5/6
 */
@Configuration
@EnableWebSecurity //启动 SpringSecurity 过滤器链功能
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //用户信息存储到内存中
        String password = passwordEncoder().encode("1234");
        auth.inMemoryAuthentication()
                .withUser("zwf")
                .password(password)
                .authorities("ADMIN");
    }

    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 表单认证
                .loginPage("/login/page") // 交给 /login/page 响应认证(登录)页面
                .loginProcessingUrl("/login/form") // 登录表单提交处理Url, 默认是 /login
                .usernameParameter("name") // 默认用户名的属性名是 username
                .passwordParameter("pwd") // 默认密码的属性名是 password
                .and()
                .authorizeRequests() // 认证请求
                .antMatchers("/login/page").permitAll() // 放行跳转认证请求
                .anyRequest().authenticated() // 所有进入应用的HTTP请求都要进行认证
        .and().csrf().disable(); //关闭csrf攻击
    }

    /**
     * Description: 放行静态资源
     * Params: [web]
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
    }
}
