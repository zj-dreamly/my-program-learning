package com.github.zj.dreamly.design.pattern.pattern.behavioral.mediator;

/**
 * @author 苍海之南
 */
public class Test {
    public static void main(String[] args) {
        User geely = new User("Geely");
        User tom= new User("Tom");

        geely.sendMessage(" Hey! Tom! Let's learn Design Pattern");
        tom.sendMessage("OK! Geely");
    }


}
