package com.lks.gc;

/**
 * @author lks
 * @E-mail 1056224715@qq.com.
 * @Since 1.0
 * @Date 2021/2/27 19:25
 */
public class Demo1 {

    private Object instance = null;

    private static final int _1MB = 1024 * 1024;

    /**
     * 这个成员属性唯一的作用就是占用一点内存
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        Demo1 objectA = new Demo1();
        Demo1 objectB = new Demo1();
        objectA.instance = objectB;
        objectB.instance = objectA;
        objectA = null;
        objectB = null;

        System.gc();
    }
}
