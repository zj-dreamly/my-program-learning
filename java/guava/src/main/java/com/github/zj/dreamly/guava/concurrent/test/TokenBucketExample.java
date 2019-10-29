package com.github.zj.dreamly.guava.concurrent.test;

import com.github.zj.dreamly.guava.concurrent.TokenBucket;

/**
 * @author 苍海之南
 */
public class TokenBucketExample
{

    public static void main(String[] args)
    {
        final TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i < 200; i++)
        {
            new Thread(tokenBucket::buy).start();
        }
    }
}
