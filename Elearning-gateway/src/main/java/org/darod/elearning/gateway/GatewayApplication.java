package org.darod.elearning.gateway;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.darod.elearning.common.validator.ValidatorImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 14:24
 */
@SpringBootApplication(scanBasePackages = {"org.darod.elearning"})
@MapperScan("org.darod.elearning.gateway.dao")
@Import({ValidatorImpl.class})
@EnableAspectJAutoProxy
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    //启用fastjson
    @Bean
    public HttpMessageConverters fastJsonConfigure() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);
        //日期格式化
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss"); //若指定全局配置 则JsonField(format=)失效
        converter.setFastJsonConfig(fastJsonConfig);
        converter.setSupportedMediaTypes(fastMediaTypes);
//        HttpMessageConverter array[] = new HttpMessageConverter[]{stringHttpMessageConverter(), converter};
//        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(false, Arrays.asList(array));
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(converter);
        return httpMessageConverters;
    }

//    private StringHttpMessageConverter stringHttpMessageConverter() {
//        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//        stringHttpMessageConverter.setWriteAcceptCharset(false);
//        return stringHttpMessageConverter;
//    }
}
