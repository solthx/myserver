## 模块功能

1. BootStrap模块
- 负责服务器的启动. 

2. EndPoint模块
- 网络传输模块的入口, 负责整个服务的启动、停止和初始化（ps: 一个服务器可以提供多个服务，一个端口对应了一个服务，而BootStrap是对服务器的启动, EndPoint是对应服务的启动,初始化与停止）

3. Acceptor模块
- 监听端口的线程. 一个Acceptor线程负责监听一个端口

4. Dispatcher模块
- 任务转发器. 为了让一个端口能为多个客户端提供服务， Acceptor需要非阻塞的进行监听，即任务处理和监听这两个动作要分离. 而Dispatcher对象的作用就是把监听得到的socket立即转发给对应的Processor(这里是Handler)

5. Handler模块
- Handler模块的主要功能是负责Connector部分与Container部分之间的转接（根据socket调用对应Servlet）
- 目前是阶段打印来作为测试.. 之后实现完Servlet部分和Container部分之后在做连接.
