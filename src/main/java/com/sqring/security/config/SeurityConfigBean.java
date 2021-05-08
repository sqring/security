package com.sqring.security.config;

import com.sqring.security.mobile.SmsCodeSender;
import com.sqring.security.mobile.SmsSend;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * descriptions:
 * author: Mr.zhou
 * date: 2021/5/8
 */
@Configuration
public class SeurityConfigBean {
    /**
     * @ConditionalOnMissingBean(SmsSend.class)
     * 默认采用SmsCodeSender实例 ，但如果容器中有其他 SmsSend 类型的实例，则当前实例失效
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }
}
