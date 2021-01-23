package com.zxw.provider;

import com.zxw.api.HelloService;
import com.zxw.rpc.server.v1.RpcServerSocket;

/**
 * @author: wuhongyun
 * @date: 2021/1/21
 */
public class HelloProviderV1 {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();

        RpcServerSocket rpcServerSocket = new RpcServerSocket();
        rpcServerSocket.publish(helloService, 8888);
    }
}
