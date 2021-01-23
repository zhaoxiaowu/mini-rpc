package com.zxw.provider;

import com.zxw.rpc.server.v2.RpcServerManage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wuhongyun
 * @date: 2021/1/22
 */
@Configuration
@ComponentScan(basePackages = "com.zxw.provider")
public class RpcConfig {
    @Bean(name="rpcServerManage")
    public RpcServerManage rpcServerManage(){
        return new RpcServerManage(8888);
    }
}
