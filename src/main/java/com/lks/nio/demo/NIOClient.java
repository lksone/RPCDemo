package com.lks.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIOClient {


    public static void main(String[] args) {
        try {
            SocketChannel client = SocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(8888);
            client.bind(address);
            if (!client.connect(address)) {
                //当连接还没有成功的时候
                while (!client.finishConnect()) {
                    System.out.println("建立连接中........");
                }
            }
            String mesg = "你好吗netty";
            ByteBuffer buffer = ByteBuffer.wrap(mesg.getBytes());
            buffer.put(mesg.getBytes(StandardCharsets.UTF_8));
            client.write(buffer);
            System.out.println("发送成功");
            System.in.read();
        } catch (IOException e) {

        }

    }
}
