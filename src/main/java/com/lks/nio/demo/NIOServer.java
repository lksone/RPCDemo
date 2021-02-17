package com.lks.nio.demo;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * INo服务器端
 */
public class NIOServer {

    private SocketAddress socketAddress;

    private int port;

    private Charset charset = Charset.forName("UTF-8");

    private Selector selector;


    public NIOServer(int port) {
        this.port = port;
        try {
            //创建一个channel
            ServerSocketChannel server = ServerSocketChannel.open();
            //2.设置非阻塞的方式
            server.configureBlocking(false);
            //设置绑定地址
            socketAddress = new InetSocketAddress(port);
            server.bind(socketAddress);
            selector = Selector.open();
            //开始可以接受事件了
            server.register(selector, SelectionKey.OP_ACCEPT);
            //服务启动了开始可以进行监听任务了
            System.out.println("服务已启动，监听端口是：" + this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void listen() {
        try {
            while (true) {
                //排队的人数
                int select = selector.select(1000);
                if (select == 0) {
                    System.out.println("服务器连接的方式和方法--" + new Date());
                    continue;
                }
                ///取号，默认给他分配个号码（排队号码）
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    process(next);
                    //手动删除selectionKey处理玩的数据
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key判断流程的走向
     *
     * @param key
     */
    private void process(SelectionKey key) throws IOException {
        //当前的可以是准备的状态
        if (key.isAcceptable()) {
            //强制设置为serverSocketChannel
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel client = channel.accept();
            channel.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        }
        //当前的key是读的状态
        if (key.isReadable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            if (read > 0) {
                buffer.flip();
                System.out.println(buffer.array());
            }
        }
    }

    public static void main(String[] args) {
        new NIOServer(8888).listen();
    }
}
