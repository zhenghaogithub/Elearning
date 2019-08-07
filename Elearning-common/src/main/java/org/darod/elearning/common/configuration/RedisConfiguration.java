package org.darod.elearning.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/3 0003 21:20
 */
@Configuration
public class RedisConfiguration {
    @Bean("redisTemplateForShiro")
    public RedisTemplate<String, Object> redisTemplateForShiro(RedisConnectionFactory factory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, RedisSerializer<Object> redisSerializer) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(redisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;
    }

//    @Bean
//    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
//        final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
//                Object.class);
//        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
////        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
////        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
////        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
////        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
////        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
////        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER,false);
////        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        return jackson2JsonRedisSerializer;
//    }
    @Bean
    public FastJson2JsonRedisSerializer<Object> fastJsonRedisSerializer(){
        FastJson2JsonRedisSerializer<Object> fastJson2JsonRedisSerializer =new FastJson2JsonRedisSerializer<>(Object.class);
        return fastJson2JsonRedisSerializer;
    }
}
