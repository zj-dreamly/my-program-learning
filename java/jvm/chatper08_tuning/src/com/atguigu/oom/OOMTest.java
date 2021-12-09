package com.atguigu.oom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 案例3：测试 GC overhead limit exceeded
 * @author shkstart
 * @create 16:57
 */
public class OOMTest {
    public static void main(String[] args) {
        test1();

//        test2();
    }

    public static void test1() {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                list.add(UUID.randomUUID().toString().intern());
                i++;
            }
        } catch (Throwable e) {
            System.out.println("************i: " + i);
            e.printStackTrace();
            throw e;
        }
    }

    public static void test2() {
        String str = "";
        Integer i = 1;
        try {
            while (true) {
                i++;
                str += UUID.randomUUID();
            }
        } catch (Throwable e) {
            System.out.println("************i: " + i);
            e.printStackTrace();
            throw e;
        }
    }

}