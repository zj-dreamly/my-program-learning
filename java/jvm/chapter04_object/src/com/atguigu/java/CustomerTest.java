package com.atguigu.java;

/**
 * @author shkstart  shkstart@126.com
 * @create 2020  18:42
 */
public class CustomerTest {
    public static void main(String[] args) {
//        Customer cust1 = new Customer();

        Customer cust2 = new Customer("Tom");
        System.out.println(cust2.name);

        System.out.println(cust2.getClass());

        System.out.println(cust2);

        Customer cust3 = cust2;

    }
}
