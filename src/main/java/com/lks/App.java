package com.lks;

import com.lks.service.IShout;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ServiceLoader<IShout> shouts = ServiceLoader.load(IShout.class);
        Iterator<IShout> iterator = shouts.iterator();
       while (iterator.hasNext()){
           IShout next = iterator.next();
           next.shout();
       }
    }
}
