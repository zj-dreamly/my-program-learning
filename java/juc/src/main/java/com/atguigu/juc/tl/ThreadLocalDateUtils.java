package com.atguigu.juc.tl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther zzyy
 * @create 2021-03-23 15:46
 */
public class ThreadLocalDateUtils
{
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Date parse(String stringDate) throws ParseException
    {
        return sdf.parse(stringDate);
    }


    //2   ThreadLocal可以确保每个线程都可以得到各自单独的一个SimpleDateFormat的对象，那么自然也就不存在竞争问题了。
    public static final ThreadLocal<SimpleDateFormat> sdfThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    public static final Date parseByThreadLocal(String stringDate) throws ParseException
    {
        return sdfThreadLocal.get().parse(stringDate);
    }


    //3 DateTimeFormatter 代替 SimpleDateFormat

    /*说明：如果是 JDK8 的应用，可以使用 Instant 代替 Date，LocalDateTime 代替 Calendar，
    DateTimeFormatter 代替 SimpleDateFormat，官方给出的解释：simple beautiful strong immutable
    thread-safe。*/

    /*public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime localDateTime)
    {
        return DATE_TIME_FORMAT.format(localDateTime);
    }

    public static LocalDateTime parse(String dateString)
    {

        return LocalDateTime.parse(dateString,DATE_TIME_FORMAT);
    }*/

    public static void main(String[] args) throws ParseException
    {
        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                try {
                    //System.out.println(ThreadLocalDateUtils.parse("2011-11-11 11:11:11"));

                    /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println(sdf.parse("2011-11-11 11:11:11"));
                    sdf = null;*/

                    System.out.println(ThreadLocalDateUtils.parseByThreadLocal("2011-11-11 11:11:11"));

                } catch (ParseException e) {
                    e.printStackTrace();
                }finally {
                    //remove();
                }
            },String.valueOf(i)).start();
        }
    }
}
