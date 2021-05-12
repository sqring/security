package com.sqring.security.authorize;

import com.sqring.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName CustomAuthorizeConfigurerProvider.java
 * @Description 身份认证相关的授权配置
 * @createTime 2021年05月11日
 */
@Component
@Order(Integer.MAX_VALUE) // 值越小加载越优先，值越大加载越靠后
public class CustomAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {


    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(securityProperties.getAuthentication().getLoginPage(),
                securityProperties.getAuthentication().getImageCodeUrl(),
                securityProperties.getAuthentication().getMobilePage(),
                securityProperties.getAuthentication().getMobileCodeUrl()
        ).permitAll(); // 放行/login/page不需要认证可访问

        // 其他请求都要通过身份认证
        config.anyRequest().authenticated();


    }
}