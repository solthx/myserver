package com.czf.server.core.net.dispatch;

import com.czf.server.core.net.wrapper.SocketWrapper;

/**
 * 任务分派器, 接收到wrapper，然后读取并封装出request和response然后丢给容器...
 */
public interface Dispatcher {
    void doDispatcher(SocketWrapper wrapper);
}
