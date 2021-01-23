package com.zxw.customer;

import com.zxw.api.HelloService;
import com.zxw.api.HiService;
import com.zxw.rpc.client.v1.RpcRemoteInvoke;

/**
 * @author: wuhongyun
 * @date: 2021/1/21
 */
public class HelloCustomer {
    public static void main(String[] args) {
        RpcRemoteInvoke rpcRemotreInvoke = new RpcRemoteInvoke();
        HelloService helloService = rpcRemotreInvoke.newProxyInstance(
                HelloService.class, "localhost", 8888);
        String result = helloService.sayHello("mini-rpc");
        System.out.println("返回结果：" + result);

        HiService hiService = rpcRemotreInvoke.newProxyInstance(
                HiService.class, "localhost", 8888);
        String result2 = hiService.sayHi();
        System.out.println("返回结果：" + result2);
    }
}
