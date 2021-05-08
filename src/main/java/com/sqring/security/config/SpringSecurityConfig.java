package com.sqring.security.config;

import com.sqring.security.filter.ImageCodeValidateFilter;
import com.sqring.security.filter.MobileValidateFilter;
import com.sqring.security.handler.CustomAuthenticationFailureHandler;
import com.sqring.security.handler.CustomAuthenticationSuccessHandler;
import com.sqring.security.handler.CustomLogoutHandler;
import com.sqring.security.mobile.MobileAuthenticationConfig;
import com.sqring.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * descriptions: 安全控制中心
 * author: Mr.zhou
 * date: 2021/5/6
 */
@Configuration
@EnableWebSecurity //启动 SpringSecurity 过滤器链功能
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 是否启动时自动创建表，第一次启动创建就行，后面启动把这个注释掉,不然报错已存在表
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
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
        auth.userDetailsService(customUserDetailsService);
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
        http.addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // 表单认证
                .loginPage(securityProperties.getAuthentication().getLoginPage()) // 交给 /login/page 响应认证(登录)页面
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理Url, 默认是 /login
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) // 默认用户名的属性名是 username
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter()) // 默认密码的属性名是 password
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .authorizeRequests() // 认证请求
                .antMatchers(securityProperties.getAuthentication().getLoginPage(),
                        securityProperties.getAuthentication().getImageCodeUrl(),
                        securityProperties.getAuthentication().getMobilePage(),
                        securityProperties.getAuthentication().getMobileCodeUrl()
                        ).permitAll() // 放行跳转认证请求
                .anyRequest().authenticated() // 所有进入应用的HTTP请求都要进行认证
                .and()
                .rememberMe().tokenRepository(jdbcTokenRepository()).tokenValiditySeconds(60*60*24*7)
                .and() //配置session管理
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(50)
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                // .maxSessionsPreventsLogin(true)
                .and()
                .and().logout().addLogoutHandler(new CustomLogoutHandler());

        //关闭csrf攻击
        http.csrf().disable();
        http.apply(mobileAuthenticationConfig);
    }

    /**
     * Description: 放行静态资源
     * Params: [web]
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }
}
