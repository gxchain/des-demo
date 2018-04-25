package com.gxb.block.des.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author liruobin
 * @since 2018/4/11 上午11:51
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:spring/appContext.xml"})
@ComponentScan(basePackages = {"com.gxb"})
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("des数据源服务启动成功。");
    }
}
