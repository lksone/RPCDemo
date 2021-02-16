package com.lks.nio.buffer;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 为了了解和使用Buffer中的常用的方法实况
 */
public class BufferProgram {

    /**
     * 获取buffer中的状态 limit,capacity,position
     *
     * @param step
     * @param buffer
     */
    public static void output(String step, Buffer buffer) {
        System.out.println(step + " : ");
        //容量，数组大小
        System.out.print("capacity: " + buffer.capacity() + ", ");
        //当前操作数据所在的位置，也可以叫做游标
        System.out.print("position: " + buffer.position() + ", ");
        //锁定值，flip，数据操作范围索引只能在position - limit 之间
        System.out.println("limit: " + buffer.limit());
        System.out.println();
    }

    public static void main(String[] args) {
        try {
            FileInputStream is = new FileInputStream("C:/Users/Administrator/IdeaProjects/RPCDemo/src/main/resources/a.txt");
            FileChannel channel = is.getChannel();
            //初始化的时候获取到的状态
            ByteBuffer buffer = ByteBuffer.allocate(10);
            output("初始化", buffer);
            channel.read(buffer);
            output("读取", buffer);
            buffer.flip();
            output("flip", buffer);
            while (buffer.remaining() > 0) {
                byte b = buffer.get();
                System.out.print(((char) b));
            }
            output("调用get()", buffer);
            buffer.clear();
            output("调用clear()", buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
