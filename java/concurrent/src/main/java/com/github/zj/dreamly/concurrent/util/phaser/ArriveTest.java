package com.github.zj.dreamly.concurrent.util.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class ArriveTest {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    //arrive
    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 0; i < 4; i++) {
            new ArriveTask(phaser, i).start();
        }

        phaser.arriveAndAwaitAdvance();

        System.out.println("The phase 1 work finished done.");
    }


    private static class ArriveTask extends Thread {
        private final Phaser phaser;

        private ArriveTask(Phaser phaser, int no) {
            super(String.valueOf(no));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + " start working.");
            ArriveTest.sleep();
            System.out.println(getName() + " The phase one is running");
            phaser.arrive();


            ArriveTest.sleep();

            System.out.println(getName() + " keep to do other thing.");

        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
