package com.atguigu.java2;

/**
 * @author shkstart
 * @create 15:44
 */
public class Test03 {
    public static void main(String[] args) {
        Father f = new Son();
        System.out.println(f.x);
    }
}
class Father{
    int x = 10;
    public Father(){
        this.print();
        x = 20;
    }
    public void print(){
        System.out.println("Father.x = " + x);
    }
}
class Son extends Father{
    int x = 30;
    public Son(){
        this.print();
        x = 40;
    }
    public void print(){
        System.out.println("Son.x = " + x);
    }
}


