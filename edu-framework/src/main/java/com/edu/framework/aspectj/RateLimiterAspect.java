package com.edu.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import com.edu.common.annotation.RateLimiter;
import com.edu.common.enums.LimitType;
import com.edu.common.exception.ServiceException;
import com.edu.common.utils.ServletUtils;
import com.edu.common.utils.StringUtils;
import com.edu.common.utils.ip.IpUtils;

/**
 * 限流处理
 *
 * @author edu
 */
@Aspect
@Component
public class RateLimiterAspect
{
//    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);
//
//    private RedisTemplate<String, Object> redisTemplate;
//
//    private RedisScript<Long> limitScript;

//    @Autowired
//    public void setRedisTemplate1(RedisTemplate<String, Object> redisTemplate)
//    {
//        this.redisTemplate = redisTemplate;
//    }

//    @Autowired
//    public void setLimitScript(RedisScript<Long> limitScript)
//    {
//        this.limitScript = limitScript;
//    }

//    @Before("@annotation(rateLimiter)")
//    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable
//    {
//        String key = rateLimiter.key();
//        int time = rateLimiter.time();
//        int count = rateLimiter.count();
//
//        String combineKey = getCombineKey(rateLimiter, point);
//        List<String> keys = Collections.singletonList(combineKey);
//        try
//        {
//            Long number = redisTemplate.execute(limitScript, keys, count, time);
//            if (StringUtils.isNull(number) || number.intValue() > count)
//            {
//                throw new ServiceException("访问过于频繁，请稍候再试");
//            }
//            log.info("限制请求'{}',当前请求'{}',缓存key'{}'", count, number.intValue(), key);
//        }
//        catch (ServiceException e)
//        {
//            throw e;
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException("服务器限流异常，请稍候再试");
//        }
//    }

//    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point)
//    {
//        StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
//        if (rateLimiter.limitType() == LimitType.IP)
//        {
//            stringBuffer.append(IpUtils.getIpAddr(ServletUtils.getRequest())).append("-");
//        }
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//        Class<?> targetClass = method.getDeclaringClass();
//        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
//        return stringBuffer.toString();
//    }
}
