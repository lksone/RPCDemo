package com.lks.nio;

import com.lks.nio.demo.NIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NIOServiceDemo {

    private int port;

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
            channel.register(selector, SelectionKey.OP_ACCEPT);
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
                int wait = this.selector.select(2000);
                if (wait == 0) {
                    System.out.println("等待中" + new Date().toString());
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

    public void process(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            int len = client.read(buffer);
            if (len > 0) {
                buffer.flip();
                String content = new String(buffer.array(), 0, len);
                System.out.println(content);
                client.register(selector, SelectionKey.OP_WRITE);
            }
            buffer.clear();
        } else if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            client.write(ByteBuffer.wrap("Hello Wold".getBytes()));
            client.close();
        }
    }

    public static void main(String[] args) {
        new NIOServiceDemo(8888).listen();
    }


}
