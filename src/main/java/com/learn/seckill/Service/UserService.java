package com.learn.seckill.Service;

import com.learn.seckill.Dao.UserDao;
import com.learn.seckill.Redis.RedisService;
import com.learn.seckill.Redis.UserKey;
import com.learn.seckill.Result.CodeMsg;
import com.learn.seckill.Until.MD5Until;
import com.learn.seckill.Until.UUIDUntil;
import com.learn.seckill.Vo.LoginVO;
import com.learn.seckill.domian.User;
import com.learn.seckill.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        User user = redisService.get(UserKey.token,token,User.class);
//        if(user != null){
//            userService.addCookie(response,user);//刷新cookie延长过期时间
//        }

        return user;
    }

    public User getById(String id){

        return  userDao.getById(id);
    }

    public Boolean doLogin(HttpServletResponse response, LoginVO loginVo ){
        System.out.println("登陆校验！");
        System.out.println(loginVo);
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        User user = getById(loginVo.getMobile());
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }else if(!MD5Until.md5(loginVo.getPassword()).equals(user.getPassword())){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        addCookie(response,user);//生成cookie
        return true;
    }

    public void addCookie(HttpServletResponse response,User user){
        String token = UUIDUntil.uuid();
        System.out.println("addCookie进来了");
        redisService.set(UserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
