package com.github.zj.dreamly.guava.eventbus.events;

/**
 * @author 苍海之南
 */
public class Request
{

    private final String orderNo;

    public Request(String orderNo)
    {
        this.orderNo = orderNo;
    }

    @Override
    public String toString()
    {
        return "Request{" +
                "orderNo='" + orderNo + '\'' +
                '}';
    }
}
