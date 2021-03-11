package com.lks.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author lks
 * @E-mail 1056224715@qq.com.
 * @Since 1.0
 * @Date 2021/3/2 23:16
 */
@Component
public class B {

    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
