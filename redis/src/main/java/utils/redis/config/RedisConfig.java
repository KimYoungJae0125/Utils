package utils.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    private RedisProperties redisProperties;
    public RedisConfig(@Autowired RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory redisConnectionFactory() {
        var config = new RedisStandaloneConfiguration(){{
            setHostName(redisProperties.getHost());
            setPort(redisProperties.getPort());
            setPassword(redisProperties.getPassword());
        }};
        return new LettuceConnectionFactory(config);
    }
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>() {{
            setConnectionFactory(redisConnectionFactory());
            setKeySerializer(new StringRedisSerializer());
    //        setValueSerializer(new StringRedisSerializer());    //String만 저장 할 경우
            setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));    //Json 데이터 저장을 할 경우
        }};
        return redisTemplate;
    }

}
