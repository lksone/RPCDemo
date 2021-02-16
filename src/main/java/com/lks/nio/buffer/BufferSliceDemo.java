package com.lks.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferSliceDemo {

    /**
     * 对buffer中的指定的位置上，创建一个子项目,可对其进项读写操作
     */
    public static void slice() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //缓存数据0-9
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        //创建一个子项目,将3-7的位置上的数据单独的弄成一个子项目
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();
        // 改变子缓冲区的内容
        for (int i = 0; i < slice.capacity(); ++i) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }

    public static void wrap() {
        // 分配指定大小的缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(10);

        // 包装一个现有的数组
        byte array[] = new byte[10];
        //将数组array放入到缓存中
        ByteBuffer buffer2 = ByteBuffer.wrap(array);
        System.out.println(buffer2);
    }

    /**
     * 数据拷贝
     *
     * @throws IOException
     */
    public static void allocateDirect() throws IOException {
        //首先我们从磁盘上读取刚才我们写出的文件内容
        String infile = "d:/a.txt";
        FileInputStream fin = new FileInputStream(infile);
        FileChannel fcin = fin.getChannel();
        //把刚刚读取的内容写入到一个新的文件中
        String outfile = String.format("d:/b.txt");
        FileOutputStream fout = new FileOutputStream(outfile);
        FileChannel fcout = fout.getChannel();
        // 使用allocateDirect，而不是allocate
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            buffer.clear();
            int r = fcin.read(buffer);
            if (r == -1) {
                break;
            }
            buffer.flip();
            //将buffer中的数据写入到fcout中
            fcout.write(buffer);
        }
    }

    public static void main(String[] args) throws IOException {
        allocateDirect();
    }
}
