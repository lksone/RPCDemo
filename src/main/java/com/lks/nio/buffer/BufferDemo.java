package com.lks.nio.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {

    public static void main(String[] args) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("C:/Users/Administrator/IdeaProjects/RPCDemo/src/main/resources/a.txt", "rw");
            FileChannel channel = file.getChannel();
            //设置缓存单位48bit
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int read = channel.read(buffer);
            while (read != -1) {
                //设置buffe可以开始读了,切换模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    // read 1 byte at a time
                    System.out.print((char) buffer.get());
                }
                buffer.compact();
                read = channel.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private static void output(String step, ByteBuffer buffer) {
        System.out.println(step + "");
        System.out.println("capacity:" + buffer.capacity());
    }
}
