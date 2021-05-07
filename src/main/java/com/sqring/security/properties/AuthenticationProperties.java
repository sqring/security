package com.sqring.security.properties;

import lombok.Data;

/**
 * descriptions:
 * author: Mr.zhou
 * date: 2021/5/7
 */
@Data
public class AuthenticationProperties {
    // application.yml 没配置取默认值
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};
}
