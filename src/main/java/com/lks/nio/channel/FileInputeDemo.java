package com.lks.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileInputeDemo {


    public static void main(String[] args) {
        try {
            FileInputStream inputStream = new FileInputStream("C:/Users/Administrator/IdeaProjects/RPCDemo/src/main/resources/a.txt");
            FileChannel channel = inputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //获取数据
            channel.read(buffer);
            buffer.flip();
            //输出的是字节流
            while (buffer.remaining() > 0) {
                byte b = buffer.get();
                buffer.rewind();
                System.out.print((char) b);
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
