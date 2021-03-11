package com.lks.bean;

import org.springframework.stereotype.Component;

/**
 * @author lks
 * @E-mail 1056224715@qq.com.
 * @Since 1.0
 * @Date 2021/3/2 23:16
 */
@Component
public class A {

    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
