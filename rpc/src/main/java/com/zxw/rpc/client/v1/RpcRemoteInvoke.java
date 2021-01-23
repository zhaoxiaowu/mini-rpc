package com.zxw.rpc.client.v1;

import java.lang.reflect.Proxy;

/**
 * Rpc 远程调用
 *
 * @author: wuhongyun
 * @date: 2021/1/21
 */
public class RpcRemoteInvoke {

    /**
     * 生成代理对象  使用JDK动态代理实现
     * 调用方法时进行远程调用
     *
     * @param interfaceCls 类或接口
     * @param host         主机
     * @param port         端口
     * @param <T>          类型
     * @return
     */
    public <T> T newProxyInstance(Class<T> interfaceCls, String host, int port) {

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls}, new RemoteInvocationHandler(host, port));
    }
}
