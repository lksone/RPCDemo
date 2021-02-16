package com.lks.service.impl;

import com.lks.service.IShout;

public class Cat implements IShout {
    @Override
    public void shout() {
        System.out.println("Cat.shout");
    }
}
