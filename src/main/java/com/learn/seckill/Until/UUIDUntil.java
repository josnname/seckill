package com.learn.seckill.Until;

import java.util.UUID;

public class UUIDUntil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
