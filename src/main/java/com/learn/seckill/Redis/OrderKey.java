package com.learn.seckill.Redis;

public class OrderKey extends BasePrefix {
    public static KeyPrefix getSeckillOrderByUidGid = new OrderKey("seckill");

    public OrderKey(String Prefix) {
        super( Prefix);
    }
}
