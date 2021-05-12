package com.sqring.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author www.zhouwenfang.com
 * @version 1.0.0
 * @ClassName AuthorizeConfigurerManager.java
 * @Description TODO
 * @createTime 2021年05月11日
 */
public interface AuthorizeConfigurerManager {
    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}