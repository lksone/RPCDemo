package com.lks.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lks
 * @E-mail 1056224715@qq.com
 * @Since 1.0
 * @Date 2021/2/25
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] req;

    public EchoClientHandler() {
        req = ("你好么。" + System.getProperty("line.separator")).getBytes();
    }

    /**
     * 调用ChannelHandlerContext.fireChannelActive（）以转发到ChannelPipeline中的下一个ChannelInboundHandler。
     * 子类可以重写此方法以更改行为。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClientHandler.channelActive");
        ByteBuf message = null;
        //将数据写入到容器中，发送100次
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClientHandler.channelInactive");
        super.channelInactive(ctx);
    }

    /**
     * 数据读取
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("EchoClientHandler.channelRead");
        String msg1 = (String) msg;
        System.out.println("接收到的数据" + msg1);
    }

    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClientHandler.channelReadComplete");
        ctx.flush();
    }

    /**
     * 发生异常的时候怎么处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("EchoClientHandler.exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}
