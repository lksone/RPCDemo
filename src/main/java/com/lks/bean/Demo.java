package com.lks.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lks
 * @E-mail 1056224715@qq.com.
 * @Since 1.0
 * @Date 2021/3/2 23:17
 */
public class Demo {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Demo.class);
        Object a = context.getBean( A.class);

    }
}
