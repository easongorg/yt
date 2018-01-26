package com.study;/**
 * Created by Easong on 2018/1/26.
 */

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 使用tomcat启动web应用，继承 SpringBootServletInitializer 并重写 configure 方法
 *
 * @author Easong
 * @create 2018-01-26 13:09
 **/
public class SpringBootStartWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        if (logger.isInfoEnabled()) {
            logger.info("[SpringBootStartWebApplication Class] TOMCAT: WEB APPLICATION START...");
        }
        return builder.sources(SpringbootShiroApplication.class);
    }
}
