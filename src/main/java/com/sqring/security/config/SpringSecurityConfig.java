package com.sqring.security.config;

import com.sqring.security.authorize.AuthorizeConfigurerManager;
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启注解方法级别权限控制
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

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Autowired
    private AuthorizeConfigurerManager authorizeConfigurerManager;

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
     * 当你认证成功之后 ，springsecurity它会重写向到你上一次请求上
     * 资源权限配置：
     * 1. 被拦截的资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic() // 采用 httpBasic认证方式
        // 校验手机验证码过滤器
        http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // 表单登录方式
                .loginPage(securityProperties.getAuthentication().getLoginPage())
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理url, 默认是/login
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) //默认的是 username
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter())  // 默认的是 password
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
//                .and()
//                    .authorizeRequests() // 授权请求
//                    .antMatchers(securityProperties.getAuthentication().getLoginPage(),
////                    "/code/image","/mobile/page", "/code/mobile"
//                            securityProperties.getAuthentication().getImageCodeUrl(),
//                            securityProperties.getAuthentication().getMobilePage(),
//                            securityProperties.getAuthentication().getMobileCodeUrl()
//                    ).permitAll() // 放行/login/page不需要认证可访问
//
//                    // 有 sys:user 权限的可以访问任意请求方式的/role
//                    .antMatchers("/user").hasAuthority("sys:user")
//                    // 有 sys:role 权限的可以访问 get方式的/role
//                    .antMatchers(HttpMethod.GET,"/role").hasAuthority("sys:role")
//                    .antMatchers(HttpMethod.GET, "/permission")
//                    // ADMIN 注意角色会在前面加上前缀 ROLE_ , 也就是完整的是 ROLE_ADMIN, ROLE_ROOT
//                    .access("hasAuthority('sys:premission') or hasAnyRole('ADMIN', 'ROOT')")
//
//                    .anyRequest().authenticated() //所有访问该应用的http请求都要通过身份认证才可以访问
                .and()
                .rememberMe() // 记住功能配置
                .tokenRepository(jdbcTokenRepository()) //保存登录信息
                .tokenValiditySeconds(securityProperties.getAuthentication().getTokenValiditySeconds()) //记住我有效时长
                .and()
                .sessionManagement()// session管理
                .invalidSessionStrategy(invalidSessionStrategy) //当session失效后的处理类
                .maximumSessions(1) // 每个用户在系统中最多可以有多少个session
                .expiredSessionStrategy(sessionInformationExpiredStrategy)// 当用户达到最大session数后，则调用此处的实现
                .maxSessionsPreventsLogin(true) // 当一个用户达到最大session数,则不允许后面再登录
                .sessionRegistry(sessionRegistry())
                .and().and()
                .logout()
                .addLogoutHandler(customLogoutHandler) // 退出清除缓存
                .logoutUrl("/user/logout") // 退出请求路径
                .logoutSuccessUrl("/mobile/page") //退出成功后跳转地址
                .deleteCookies("JSESSIONID") // 退出后删除什么cookie值
        ;// 注意不要少了分号

        http.csrf().disable(); // 关闭跨站请求伪造
        //将手机认证添加到过滤器链上
        http.apply(mobileAuthenticationConfig);

        // 将所有的授权配置统一的起来
        authorizeConfigurerManager.configure(http.authorizeRequests());
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
