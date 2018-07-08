package com.quxiaojun.test.common.services;

import com.quxiaojun.test.common.request.RedisRequestWrapper;
import com.quxiaojun.test.common.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SessionService {
    private static Logger LOG = LoggerFactory.getLogger(RedisRequestWrapper.class);

    private JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
    private RedisTemplate<Serializable, Serializable> redisTemplate;


    @SuppressWarnings("unchecked")
    public Map<String, Object> getSession(String sid) {
        Map<String, Object> session = new HashMap<String, Object>();
        try {
            Object obj = redisTemplate.opsForValue()
                    .get(RedisKeyUtil.SESSION_DISTRIBUTED_SESSIONID + sid);
            if(obj != null){
                obj = jdkSerializer.deserialize((byte[])obj);
                session = (Map<String, Object>) obj;
            }
        } catch (Exception e) {
            LOG.error("Redis获取session异常" + e.getMessage(), e.getCause());
        }

        return session;
    }

    public void saveSession(String sid, Map<String, Object> session) {
        try {
            redisTemplate.opsForValue()
                    .set(RedisKeyUtil.SESSION_DISTRIBUTED_SESSIONID + sid,
                            jdkSerializer.serialize(session), RedisKeyUtil.SESSION_TIMEOUT,
                            TimeUnit.MINUTES);
        } catch (Exception e) {
            LOG.error("Redis保存session异常" + e.getMessage(), e.getCause());
        }
    }

    public void removeSession(String sid) {
        try {
            redisTemplate.delete(
                    RedisKeyUtil.SESSION_DISTRIBUTED_SESSIONID + sid);
        } catch (Exception e) {
            LOG.error("Redis删除session异常" + e.getMessage(), e.getCause());
        }
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
