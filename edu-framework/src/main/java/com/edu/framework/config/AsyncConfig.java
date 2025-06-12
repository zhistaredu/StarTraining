package com.edu.framework.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

@Configuration
public class AsyncConfig extends AsyncConfigurerSupport
{
    /**
     * 异步执行需要使用权限框架自带的包装线程池 保证权限信息的传递
     */
    @Override
    public Executor getAsyncExecutor()
    {
        return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(5));
    }
}
