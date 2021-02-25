package com.lks.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author lks
 */
public class NettyClient {

    private static final String HOST = System.getProperty("host", "127.0.0.1");

    private static final int PORT = Integer.parseInt(System.getProperty("port", "8089"));

    private static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //一个引导程序，可以很容易地引导通道以供客户端使用。
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取pipeline
                            ChannelPipeline p = ch.pipeline();
                            //很重要哦
                            p.addLast(new LineBasedFrameDecoder(1024));
                            //很重要哦
                            p.addLast(new StringDecoder());
                            p.addLast(new EchoClientHandler());
                        }
                    });
            // Start the client.
            ChannelFuture f = bootstrap.connect(HOST, PORT).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            //优雅的关闭
            group.shutdownGracefully();
        }
    }
}
