package com.lks;

import com.lks.service.IShout;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

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
        ApplicationContext context = new AnnotationConfigWebApplicationContext();
        Object bean = context.getBean("");
    }
}
