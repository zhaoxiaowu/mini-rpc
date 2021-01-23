package com.zxw.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: wuhongyun
 * @date: 2021/1/22
 */
public class HelloProviderV2 {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(RpcConfig.class).start();
    }
}
