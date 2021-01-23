# **实现一个RPC框架**

1. 通信 TCP
2. IO   目前使用Socket(BIO)    -- NIO 
3. 序列化 反序列化  
4. 方法调用 反射

# **V1版本： 基本功能实现**

**服务端：**
 ServerSocket接受连接 --  每个请求 交给一个RpcInvokeHandler -- 解析流得到RpcRequest -- 获取方法参数 -- 反射调用
**客户端：**
newProxyInstance生成代理类 -- 方法调用执行RemoteInvocationHandler -- 封装数据发送

# **V2版本： 使用注解发布服务**

**服务端：**
RpcService注解标注服务 --Spring配置Bean 扫描Bean --  RpcServerManage 实现ApplicationContextAware 获取handlerMap(类名和实例关系) -- RpcServerManage 实现InitializingBean 类加载后启动ServerSocket