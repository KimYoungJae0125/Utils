package utils.redis.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
@SpringBootTest
public class RedisUtilsTest {

    @Autowired
    RedisUtils redisUtils;

    @Test
    @DisplayName("Redis에 유효기간 있는 데이터 저장")
    void testSaveValueWithExpireInRedis() {
        //Redis에 저장할 데이터 세팅
        String key = "key";
        String value = "hi";
        Long expiredTime = 1000L * 60 * 60; //1시간

        //Redis에 저장
        redisUtils.setValue(key, value, expiredTime);

        //Redis에 저장 되어 있는지 확인
        assertThat((String) redisUtils.getValue("key"), is("hi"));
        System.out.println("redis key : " + key);
        System.out.println("redis value : " + redisUtils.getValue(key));
        System.out.println("redis expiredTime : " + redisUtils.getExpireTime(key));
        //Redis에 저장 된 데이터 삭제
        assertThat(redisUtils.deleteValue("key"), is(true));
        //Redis에서 삭제됐는지 확인
        assertThat(redisUtils.getValue("key"), is(nullValue()));
    }

    @Test
    @DisplayName("Redis에 데이터 저장(유효기간 X)")
    void testSaveValueInRedis() {
        //Redis에 저장할 데이터 세팅
        String key = "key";
        String value = "hi";

        //Redis에 저장
        redisUtils.setValue(key, value);

        //Redis에 저장 되어 있는지 확인
        assertThat(redisUtils.getValue("key"), is("hi"));
        assertThat(redisUtils.getExpireTime("key"), is(-1L));

        //Redis에 저장 된 데이터 삭제
        assertThat(redisUtils.deleteValue("key"), is(true));
        //Redis에서 삭제됐는지 확인
        assertThat(redisUtils.getValue("key"), is(nullValue()));
    }

    @Test
    @DisplayName("Redis에 Json 데이터 저장")
    void testSaveJsonValueInRedis() {
        String key = "keys";
        var value = new LinkedHashMap<String, String>(){{
            put("oneKey", "oneValue");
            put("twoKey", "twoValue");
            put("threeKey", "threeValue");
        }};

        redisUtils.setValue(key, value);
        var jsonData = (HashMap) redisUtils.getValue(key);
        System.out.println("jsonData : " + jsonData);

        assertThat(jsonData.get("oneKey"), is("oneValue"));
        assertThat(jsonData.get("twoKey"), is("twoValue"));
        assertThat(jsonData.get("threeKey"), is("threeValue"));

        redisUtils.deleteValue(key);

    }




}
