package com.zxw.rpc.server.v2;

import com.zxw.rpc.server.v2.RpcInvokeHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果Spring中的类实现了ApplicationContextAware接口，
 * Spring就会为该类的实例注入ApplicationContext实例，从而拥有的访问Spring容器的能力
 *
 * @author: wuhongyun
 * @date: 2021/1/21
 */
public class RpcServerManage implements ApplicationContextAware, InitializingBean {

    ExecutorService executorService = Executors.newCachedThreadPool();

    private Map<String, Object> handlerMap = new HashMap();

    private int port;

    public RpcServerManage(int port) {
        this.port = port;
    }

    public void afterPropertiesSet() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) { //不断接受请求
                Socket socket = serverSocket.accept();
                //每个请求 交给一个RpcInvokeHandler
                executorService.execute(new RpcInvokeHandler(socket, handlerMap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!serviceBeanMap.isEmpty()) {
            for (Object servcieBean : serviceBeanMap.values()) {
                //拿到注解
                RpcService rpcService = servcieBean.getClass().getAnnotation((RpcService.class));
                String serviceName = rpcService.value().getName();//拿到接口类定义
                handlerMap.put(serviceName, servcieBean);
            }
        }
    }
}
