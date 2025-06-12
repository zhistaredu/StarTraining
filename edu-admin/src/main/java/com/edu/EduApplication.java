package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 * 
 * @author edu
 */
// 启动类上面添加，开启异步调用
@EnableAsync
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class EduApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(EduApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
