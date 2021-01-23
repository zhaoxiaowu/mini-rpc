package com.zxw.rpc.server.v1;

import com.zxw.rpc.entity.RpcRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * RPC 方法调用
 *
 * @author: wuhongyun
 * @date: 2021/1/20
 */
public class RpcInvokeHandler implements Runnable {

    private Socket socket;
    private Object service;

    public RpcInvokeHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ) {
            RpcRequest rpcRequest = (RpcRequest) inputStream.readObject();
            Object invoke = invoke(rpcRequest);
            outputStream.writeObject(invoke);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 方法调用
     */
    public Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //1. 找到类   2.找到方法 方法参数类型   3.invoke  传入实例  和参数
        Object[] params = request.getParams();
        Class<?>[] types = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            types[i] = params[i].getClass();
        }

        Class<?> clazz = Class.forName(request.getClassName());
        Method method = clazz.getMethod(request.getMethodName(), types);
        Object invokeResult = method.invoke(service, params);
        return invokeResult;
    }
}
