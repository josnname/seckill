package com.learn.seckill.Redis;

public abstract class BasePrefix implements KeyPrefix {


    private int expireSeconds;
    private String Prefix;

    public BasePrefix(int expireSeconds,String Prefix){
        this.expireSeconds = expireSeconds;
        this.Prefix = Prefix;
    }

    public BasePrefix(String Prefix){
        this(0,Prefix);
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
//        System.out.println("getSimpleName: "+className);
        return className + ":" + Prefix;
    }
}
