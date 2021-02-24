package com.lks.reactor.server;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lks
 * @descripte 模块只负责处理连接就绪事件
 */
public class Acceptor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel;
            socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                //这里把客户端通道传给Handler，Handler负责接下来的事件处理（除了连接事件以外的事件均可）
                new Handler(selector, socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
