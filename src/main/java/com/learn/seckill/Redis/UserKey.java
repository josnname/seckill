package com.learn.seckill.Redis;

public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24;

    private UserKey(int expireSeconds,String Prefix) {
        super(expireSeconds,Prefix);
    }
    public static UserKey token = new UserKey(TOKEN_EXPIRE,"tk");
//    public static UserKey getByName = new UserKey("name");
}
