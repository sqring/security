package com.sqring.security.config;

import com.sqring.security.mobile.SmsCodeSender;
import com.sqring.security.mobile.SmsSend;
import com.sqring.security.session.CustomInvalidSessionStrategy;
import com.sqring.security.session.CustomSessionInformationExpiredStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * descriptions:
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Configuration
public class SeurityConfigBean {


    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }


    // 当session失效后的处理类
    @Autowired
    private SessionRegistry sessionRegistry;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy(sessionRegistry);
    }

    // @ConditionalOnMissingBean(SmsSend.class) 默认采用SmsCodeSender实例 ，但如果容器中有其他 SmsSend 类型的实例，则当前实例失效
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }

}
