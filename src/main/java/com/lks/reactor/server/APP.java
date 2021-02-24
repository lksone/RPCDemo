package com.lks.reactor.server;

public class APP {
    public static void main(String[] args) {
        new Thread(new Reactor(9090)).start();
    }
}
