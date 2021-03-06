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
    /**
     * Description:登录成功后响应 JSON , 还是重定向
     */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    private String imageCodeUrl = "/code/image";
    private String mobileCodeUrl = "/code/mobile";
    private String mobilePage = "/mobile/page";
    private Integer tokenValiditySeconds = 60*60*24*7;



}
