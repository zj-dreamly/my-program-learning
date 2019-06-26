package com.github.zj.dreamly.design.pattern.pattern.creational.prototype.abstractprototype;

/**
 * @author 苍海之南
 */
public abstract class A implements Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
