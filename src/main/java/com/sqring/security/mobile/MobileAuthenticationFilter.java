package com.sqring.security.mobile;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * descriptions: 用于校验用户手机号是否允许通过认证
 * author: Mr.zhou
 * date: 2021/5/8
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String mobileParameter = "mobile";
    private boolean postOnly = true;

    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher("/mobile/form", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String mobile = obtainMobile(request);
        if (mobile == null) {
            mobile = "";
        }
        mobile = mobile.trim();
        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);
        // sessionID, hostname
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 从请求中获取手机号码
     */
    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * 将 sessionID和hostname添加 到MobileAuthenticationToken
     */
    protected void setDetails(HttpServletRequest request,
                              MobileAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    /**
     * 设置是否为post请求
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getMobileParameter() {
        return mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        this.mobileParameter = mobileParameter;
    }
}
