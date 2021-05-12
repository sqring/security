package com.sqring.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName CutomAuthorizeConfigurerManager.java
 * @Description 将所有的授权配置统一的管理起来
 * @createTime 2021年05月11日
 */
@Component
public class CutomAuthorizeConfigurerManager implements AuthorizeConfigurerManager {

    @Autowired
    List<AuthorizeConfigurerProvider> authorizeConfigurerProviders;

    // 将一个个AuthorizeConfigurerProvider的实现类，传入配置的参数 ExpressionInterceptUrlRegistry
    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for(AuthorizeConfigurerProvider provider: authorizeConfigurerProviders) {
            provider.confiure(config);
        }
    }
}