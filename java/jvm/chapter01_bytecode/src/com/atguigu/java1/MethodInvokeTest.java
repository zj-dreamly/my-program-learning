package com.atguigu.java1;

import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;

import java.util.Comparator;

/**
 * @author shkstart
 * @create 14:47
 */
public class MethodInvokeTest {
    public static void main(String[] args) {
        Father f = new Father();
        Son s = new Son();
        System.out.println(f.getInfo());
        System.out.println(s.getInfo());

        Son.show();

        Comparator<Integer> comparator = Integer::compareTo;

        comparator.compare(12,32);
    }
}

class Father {
    private String info = "atguigu";

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}

class Son extends Father {
    private String info = "尚硅谷";

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public static void show(){
        System.out.println("hello");
    }

}


