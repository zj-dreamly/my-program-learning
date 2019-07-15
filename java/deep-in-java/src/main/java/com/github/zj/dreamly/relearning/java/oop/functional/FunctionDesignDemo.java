package com.github.zj.dreamly.relearning.java.oop.functional;

import java.util.function.Function;

public class FunctionDesignDemo {

    public static void main(String[] args) {

        Function function = a -> a;

        // Function 可以用于同类转换
        Function<Integer, Integer> divide2 = a -> a / 2;
    }
}
