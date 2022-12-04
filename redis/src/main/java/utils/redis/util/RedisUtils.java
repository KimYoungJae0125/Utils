package utils.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils<T> {
    private RedisTemplate<String, T> redisTemplate;
    public RedisUtils(@Autowired RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, T value) {
        //유효 기간 없이
        redisTemplate.opsForValue().set(key, value);
    }
    public void setValue(String key, T value, Long expiredTime) {
        //유효 기간 설정해서 저장
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(expiredTime));
    }

    public T getValue(String key) {
        //Redis에서 key에 맞는 value 찾기
        if(redisTemplate.hasKey(key)) {
            return (T) redisTemplate.opsForValue().get(key);
        } else {
            return null;
        }
    }

    public Boolean deleteValue(String key) {
        //데이터 삭제
        return redisTemplate.delete(key);
    }

    public Long getExpireTime(String key) {
        //유효 기간 확인
        //유효 기간 없을 시 데이터는 -1을 반환
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }

}
