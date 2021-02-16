package com.lks.service.impl;

import com.lks.service.IShout;

public class Dog implements IShout {
    @Override
    public void shout() {
        System.out.println("Dog.shout");
    }
}
