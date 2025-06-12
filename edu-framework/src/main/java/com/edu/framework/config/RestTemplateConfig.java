package com.edu.framework.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        /**
         * 使用客户端类API调用远程交易时必须设置相应的超时时间，如果远程交易有问题如长时间未响应，若未设置超时时间，则会耗尽客户端所在服务器的资源
         */
        // 连接超时
        requestFactory.setConnectTimeout(5000);
        //读超时
        requestFactory.setReadTimeout(10000);
        //连接池获取连接超时时间
        requestFactory.setConnectionRequestTimeout(5000);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //Httpclient连接池的方式，同时支持netty，okHttp以及其他http框架
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 最大连接数
        connectionManager.setMaxTotal(200);
        // 同路由并发数
        connectionManager.setDefaultMaxPerRoute(50);
        //配置连接池
        httpClientBuilder.setConnectionManager(connectionManager);

        // 最大连接数
        connectionManager.setMaxTotal(500);
        // 同路由并发数（每个主机的并发）
        connectionManager.setDefaultMaxPerRoute(100);
        httpClientBuilder.setConnectionManager(connectionManager);
        requestFactory.setHttpClient(httpClientBuilder.build());
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
