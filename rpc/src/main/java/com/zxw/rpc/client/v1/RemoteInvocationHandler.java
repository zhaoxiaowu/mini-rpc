package com.zxw.rpc.client.v1;

import com.zxw.rpc.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理对象处理类
 *
 * @author: wuhongyun
 * @date: 2021/1/21
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用方法：" + method.getName());
        //请求数据封装
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);
        //发送远程数据包
        RpcClientSocket clientSocket = new RpcClientSocket(host, port);
        Object result = clientSocket.send(rpcRequest);
        return result;
    }
}
