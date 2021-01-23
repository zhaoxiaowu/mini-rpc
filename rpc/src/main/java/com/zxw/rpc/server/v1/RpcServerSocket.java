package com.zxw.rpc.server.v1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RPC 服务端scoket服务
 *
 * @author: wuhongyun
 * @date: 2021/1/20
 */
public class RpcServerSocket {

    ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 发布服务到 指定端口
     *
     * @param service
     * @param port
     * @throws IOException
     */
    public void publish(Object service, int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) { //不断接受请求
                Socket socket = serverSocket.accept();
                //每个请求 交给一个RpcInvokeHandler
                executorService.execute(new RpcInvokeHandler(socket, service));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
