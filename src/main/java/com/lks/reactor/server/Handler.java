package com.lks.reactor.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {

    private SocketChannel socketChannel;

    private SelectionKey selectionKey;

    ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    ByteBuffer writeBuffer = ByteBuffer.allocate(2048);

    //读写状态
    private static final int READING = 0, SENDING = 1;

    private int state = READING;

    public Handler(Selector selector, SocketChannel socketChannel) {
        try {
            this.socketChannel = socketChannel;
            //附加处理对象，当前是Handler对象，run是对象处理业务的方法
            socketChannel.configureBlocking(false);
            selectionKey = this.socketChannel.register(selector, 0);
            //将Handler作为callback对象
            selectionKey.attach(this);
            //第二步,接收Read事件
            selectionKey.interestOps(SelectionKey.OP_READ);
            //唤起select阻塞
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            switch (state) {
                case READING:
                    read();
                    break;
                case SENDING:
                    send();
                    break;
                default:
            }
        } catch (IOException e) { //这里的异常处理是做了汇总，常出的异常就是server端还有未读/写完的客户端消息，客户端就主动断开连接，这种情况下是不会触发返回-1的，这样下面read和write方法里的cancel和close就都无法触发，这样会导致死循环异常（read/write处理失败，事件又未被cancel，因此会不断的被select到，不断的报异常）
            System.err.println("read或send时发生异常！异常信息：" + e.getMessage());
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e2) {
                System.err.println("关闭通道时发生异常！异常信息：" + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }

    void read() throws IOException {
        //是否失效
        if (selectionKey.isValid()) {
            //读取之前先恢复point
            readBuffer.clear();
            //read方法结束，意味着本次"读就绪"变为"读完毕"，标记着一次就绪事件的结束
            int count = socketChannel.read(readBuffer);
            if (count > 0) {
                System.out.println(String.format("收到来自 %s 的消息: %s",
                        socketChannel.getRemoteAddress(),
                        new String(readBuffer.array())));
                state = SENDING;
                ////注册写方法
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            } else {
                //读模式下拿到的值是-1，说明客户端已经断开连接，那么将对应的selectKey从selector里清除，否则下次还会select到，因为断开连接意味着读就绪不会变成读完毕，也不cancel，下次select会不停收到该事件
                //所以在这种场景下，（服务器程序）你需要关闭socketChannel并且取消key，最好是退出当前函数。注意，这个时候服务端要是继续使用该socketChannel进行读操作的话，就会抛出“远程主机强迫关闭一个现有的连接”的IO异常。
                selectionKey.cancel();
                socketChannel.close();
                System.out.println("read时-------连接关闭");
            }
        }
    }

    void send() throws IOException {
        if (selectionKey.isValid()) {
            writeBuffer.clear();
            writeBuffer.put(String.format("我收到来自%s的信息辣：%s,  200ok;", socketChannel.getRemoteAddress(), new String(readBuffer.array())).getBytes());
            writeBuffer.flip();
            //write方法结束，意味着本次写就绪变为写完毕，标记着一次事件的结束
            int count = socketChannel.write(writeBuffer);
            if (count < 0) {
                //同上，write场景下，取到-1，也意味着客户端断开连接
                selectionKey.cancel();
                socketChannel.close();
                System.out.println("send时-------连接关闭");
            }

            //没断开连接，则再次切换到读
            state = READING;
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }
}
