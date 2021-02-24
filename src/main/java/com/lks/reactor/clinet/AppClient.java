package com.lks.reactor.clinet;

public class AppClient {

    public static void main(String[] args) {
        new Thread(new NIOClient("127.0.0.1", 9090)).start();
    }
}
