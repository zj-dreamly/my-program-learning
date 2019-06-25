package com.github.zj.dreamly.design.pattern.principle.liskovsubstitution.methodinput;

import java.util.HashMap;

/**
 * @author 苍海之南
 */
public class Test {
    public static void main(String[] args) {
        Base child = new Child();
        HashMap hashMap = new HashMap();
        child.method(hashMap);
    }
}
