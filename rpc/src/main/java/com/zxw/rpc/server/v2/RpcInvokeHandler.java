package com.zxw.rpc.server.v2;

import com.zxw.rpc.entity.RpcRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * RPC 方法调用
 *
 * @author: wuhongyun
 * @date: 2021/1/20
 */
public class RpcInvokeHandler implements Runnable {

    private Socket socket;
    private Map<String, Object> handlerMap;

    public RpcInvokeHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
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
        //service 从handlerMap中获取
        String serviceName = request.getClassName();
        Object service = handlerMap.get(serviceName);
        if (service == null) {
            throw new RuntimeException("service not found:" + serviceName);
        }

        //1. 找到类   2.找到方法 方法参数类型   3.invoke  传入实例  和参数
        Class<?> clazz = Class.forName(serviceName);
        Method method;

        Object[] params = request.getParams();
        if (params != null) {
            Class<?>[] types = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                types[i] = params[i].getClass();
            }
            method = clazz.getMethod(request.getMethodName(), types);
        } else {
            method = clazz.getMethod(request.getMethodName());
        }

        Object invokeResult = method.invoke(service, params);
        return invokeResult;
    }
}
