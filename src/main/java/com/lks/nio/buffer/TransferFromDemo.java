package com.lks.nio.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TransferFromDemo {

    public static void transFrom() {
        RandomAccessFile fromFile = null;
        FileChannel channel = null;
        RandomAccessFile toFile = null;
        FileChannel toChannel = null;
        try {
            fromFile = new RandomAccessFile("d:\\a.txt", "rw");
            channel = fromFile.getChannel();
            toFile = new RandomAccessFile("d:\\b.txt", "rw");
            toChannel = toFile.getChannel();
            long position = toChannel.size();
            long count = channel.size();
            //这是对数据的拷贝，直接将数据进行数据拷贝呢
            /**
             * 将b文件中的数据直接拷贝到，a文件中
             */
            toChannel.transferFrom(channel, position, count);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                toChannel.close();
                channel.close();
                fromFile.close();
                toFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据转换
     */
    public static void transTo() {
        RandomAccessFile fromFile = null;
        FileChannel channel = null;
        RandomAccessFile toFile = null;
        FileChannel toChannel = null;
        try {
            fromFile = new RandomAccessFile("d:\\a.txt", "rw");
            channel = fromFile.getChannel();
            toFile = new RandomAccessFile("d:\\b.txt", "rw");
            toChannel = toFile.getChannel();
            long position = 0;
            long count = toChannel.size();
            //将数据tochanel中的数据copy到channel中
            toChannel.transferTo(position, count, channel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                toChannel.close();
                channel.close();
                fromFile.close();
                toFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        transTo();
    }
}
