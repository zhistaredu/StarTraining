package com.edu.framework.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 *
 * @author edu
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
{
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object,Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //使用JackSon的redis序列化机制
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //即将废弃的方法，被下面的方法替代
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashMap序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public DefaultRedisScript<Long> limitScript()
    {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 限流脚本
     */
    private String limitScriptText()
    {
        return "local key = KEYS[1]\n" +
                "local count = tonumber(ARGV[1])\n" +
                "local time = tonumber(ARGV[2])\n" +
                "local current = redis.call('get', key);\n" +
                "if current and tonumber(current) > count then\n" +
                "    return tonumber(current);\n" +
                "end\n" +
                "current = redis.call('incr', key)\n" +
                "if tonumber(current) == 1 then\n" +
                "    redis.call('expire', key, time)\n" +
                "end\n" +
                "return tonumber(current);";
    }

    @Bean
    public DefaultRedisScript<Long> learnWriteScript()
    {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(learnWriteScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 学习记录写入脚本
     */
    private String learnWriteScriptText()
    {
        return "local scoreKey = tostring(KEYS[1]);\n" +
                "local hashKey = tostring(KEYS[2]);\n" +
                "local score = tonumber(ARGV[1]);\n" +
                "local zvalue = tostring(ARGV[2]);\n" +
                "local field = tostring(ARGV[3]);\n" +
                "local hvalue = tostring(ARGV[4]);\n" +
                "redis.call('zadd',scoreKey,score,zvalue);\n" +
                "redis.call('hset',hashKey,field,hvalue);\n" +
                "return 1;";
    }

    @Bean
    public DefaultRedisScript<Long> learnSubmitScript()
    {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(learnSubmitScriptText());

        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 学习记录提交脚本
     */
    private String learnSubmitScriptText()
    {
        return "local scoreKey = tostring(KEYS[1]);\n" +
                "local hashKey = tostring(KEYS[2]);\n" +
                "local zvalue = tostring(ARGV[1]);\n" +
                "local field = tostring(ARGV[2]);\n" +
                "redis.call('zrem',scoreKey,zvalue);\n" +
                "redis.call('hdel',hashKey,field);\n" +
                "return 1;";
    }
}
