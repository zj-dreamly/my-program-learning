package com.atguigu.stack;

/**方法中定义的局部变量是否线程安全?   具体问题具体分析
 * @author shkstart
 * @create 15:53
 */
public class LocalVariableThreadSafe {

    public static void main(String[] args) {

        LocalVariableThreadSafe l = new LocalVariableThreadSafe();
        StringBuilder s1 = new StringBuilder();
        l.method2(s1);

        s1.append("abc");

    }
    //s1的声明方式是线程安全的,因为线程私有，在线程内创建的s1 ，不会被其它线程调用
    public static void method1() {
        //StringBuilder:线程不安全
        StringBuilder s1 = new StringBuilder();
        s1.append("a");
        s1.append("b");
        //...
    }

    //stringBuilder的操作过程：是线程不安全的，
    // 因为stringBuilder是外面传进来的，有可能被多个线程调用
    public static void method2(StringBuilder stringBuilder) {
        stringBuilder.append("a");
        stringBuilder.append("b");
        //...
    }

    //stringBuilder的操作：是线程不安全的；因为返回了一个stringBuilder，
    // stringBuilder有可能被其他线程共享
    public static StringBuilder method3() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a");
        stringBuilder.append("b");
        return stringBuilder;
    }

    //stringBuilder的操作：是线程安全的；因为返回了一个stringBuilder.toString()相当于new了一个String，
    // 所以stringBuilder没有被其他线程共享的可能
    public static String method4() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a");
        stringBuilder.append("b");
        return stringBuilder.toString();

        /**
         * 结论：如果局部变量在内部产生并在内部消亡的，那就是线程安全的
         */
    }
}
