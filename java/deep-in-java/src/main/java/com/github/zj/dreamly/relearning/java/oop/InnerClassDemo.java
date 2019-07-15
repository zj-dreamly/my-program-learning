package com.github.zj.dreamly.relearning.java.oop;

public class InnerClassDemo {

    private int data;

    private C c;

    public class C {

        private int data;

    }

    public void setData() {
        c.data = data;
    }

    public static class A {

    }

    public /*static*/ interface B {

    }

    public static void main(String[] args) {
        A a = null;
        B b = null;
        C c = null;
    }
}
