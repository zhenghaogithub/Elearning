package org.darod.elearning.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/30 0030 15:46
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
//
//    /**
//     * 此方法把该拦截器实例化成一个bean,否则在拦截器里无法注入其它bean
//     * @return
//     */
//    @Bean
//    SessionInterceptor sessionInterceptor() {
//        return new SessionInterceptor();
//    }
//    /**
//     * 配置拦截器
//     * @param registry
//     */
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(sessionInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login","/permission/userInsert",
//                        "/error","/tUser/insert","/gif/getGifCode");
//    }

}