package com.lks.reactor.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * reactor模式
 */
public class Reactor implements Runnable {

    private ServerSocketChannel serverSocket;

    private Selector selector;

    public Reactor(int port) {
        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            //1、第一步接收accpet事件
            SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            // attach Acceptor 处理新连接
            sk.attach(new Acceptor(selector, serverSocket));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                //就绪事件到达之前，阻塞
                int select = selector.select(1000);
                if (select == 0) {
                    System.out.println("ReactorDemo.run");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    //任务分发
                    SelectionKey next = iterator.next();
                    dispatch(next);
                    iterator.remove();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到事件
     *
     * @param key
     */
    private void dispatch(SelectionKey key) {
        //调用之前注册的callback对象
        Runnable attachment = (Runnable) key.attachment();
        if (attachment != null) {
            attachment.run();
        }
    }
}
