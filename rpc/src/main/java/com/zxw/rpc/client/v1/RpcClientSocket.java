package com.zxw.rpc.client.v1;

import com.zxw.rpc.entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Rpc 客户端socket通信
 *
 * @author: wuhongyun
 * @date: 2021/1/21
 */
public class RpcClientSocket {
    private String host;
    private int port;

    public RpcClientSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest request) {
        Object result = null;
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ) {
            outputStream.writeObject(request);
            outputStream.flush();
            result = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
