package com.zxw.provider;

import com.zxw.api.HelloService;
import com.zxw.rpc.server.v2.RpcService;

/**
 * @author: wuhongyun
 * @date: 2021/1/18
 */
@RpcService(value = HelloService.class)
public class HelloServiceImpl implements HelloService {
    public String sayHello(String str) {
        System.out.println("调用 sayHello 参数:" + str);
        return "Say Hello:" + str;
    }
}
