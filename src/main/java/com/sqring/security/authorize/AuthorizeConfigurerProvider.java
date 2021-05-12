package com.sqring.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName AuthorizeConfigurerProvider.java
 * @Description 授权配置统一接口, 所有模块授权配置类都实现此接口
 * @createTime 2021年05月11日
 */
public interface AuthorizeConfigurerProvider {

    void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}