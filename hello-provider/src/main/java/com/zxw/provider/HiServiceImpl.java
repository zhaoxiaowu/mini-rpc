package com.zxw.provider;

import com.zxw.api.HelloService;
import com.zxw.api.HiService;
import com.zxw.rpc.server.v2.RpcService;

/**
 * @author: wuhongyun
 * @date: 2021/1/18
 */
@RpcService(value = HiService.class)
public class HiServiceImpl implements HiService {
    public String sayHi() {
        System.out.println("调用 sayHi 参数");
        return "Hello world";
    }
}
