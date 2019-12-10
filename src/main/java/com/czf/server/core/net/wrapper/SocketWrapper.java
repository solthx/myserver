package com.czf.server.core.net.wrapper;

import java.io.IOException;

public interface SocketWrapper {
    void close() throws IOException; // 关闭socket连接
}
