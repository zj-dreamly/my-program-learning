package com.github.zj.dreamly.concurrent.util.exchanger;

import java.util.concurrent.Exchanger;

/**
 * @author 苍海之南
 */
public class ExchangerExample2 {

    /**
     * Actor
     */
    public static void main(String[] args) {

        final Exchanger<Object> exchanger = new Exchanger<Object>();

        new Thread() {
            @Override
            public void run() {
                Object aobj = new Object();
                System.out.println("A will send  the object " + aobj);

                try {
                    Object rObj = exchanger.exchange(aobj);
                    System.out.println("A recieved the object " + rObj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Object bobj = new Object();
                System.out.println("B will send  the object " + bobj);

                try {
                    Object rObj = exchanger.exchange(bobj);
                    System.out.println("B recieved the object " + rObj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
