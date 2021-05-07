package com.sqring.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * descriptions: 读取自定义配置
 * author: Mr.zhou
 * date: 2021/5/7
 */
@Component
@ConfigurationProperties(prefix = "sqring.security")
@Data
public class SecurityProperties {
    private AuthenticationProperties authentication;
}
