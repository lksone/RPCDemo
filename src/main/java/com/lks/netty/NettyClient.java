package com.lks.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author lks
 * @describe netty的客戶端
 * @Email 1056224715@qq.com
 * @date 2021/2/25 17:34
 **/
public class NettyClient {

    private final int port =Integer.parseInt(System.getProperty("port","8089"));

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //紅色還在
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new LineBasedFrameDecoder(1024));//很重要哦
                        p.addLast(new StringDecoder());//很重要哦
                        p.addLast(new EchoServerHandler());
                    }
                });
    }
}
