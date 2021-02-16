package com.lks.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServiceDemo {

    private int port = 8080;

    private InetSocketAddress address;


    private Selector selector;

    public NIOServiceDemo(int port) {
        try {
            this.port = port;
            address = new InetSocketAddress(port);
            //创建一个chanl,打开一个通道
            ServerSocketChannel channel = ServerSocketChannel.open();
            //绑定地址通道的位置，要从哪里获取数据
            channel.bind(address);
            //设置是否可以阻塞,默认是阻塞的
            channel.configureBlocking(false);
            //设置选择器
            selector = Selector.open();
            //将选择器和端口号注入到通道中
            channel.register(selector, port);
            System.out.println("服务器准备就绪，监听端口是：" + this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            //轮询
            while (true) {
                //有多少个人在服务大厅排队
                int wait = this.selector.select();
                if (wait == 0) {
                    continue;
                }

                Set<SelectionKey> keys = this.selector.selectedKeys();

                Iterator<SelectionKey> i = keys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();
                    process(key);
                    i.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process(SelectionKey key) {

    }

    public static void main(String[] args) {
        try {
            ServerSocketChannel open = ServerSocketChannel.open();
            //获取接受数据
            SocketChannel accept = open.accept();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


}
