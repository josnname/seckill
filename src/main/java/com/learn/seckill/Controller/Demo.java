package com.learn.seckill.Controller;
import com.learn.seckill.Redis.RedisService;
import com.learn.seckill.Redis.UserKey;
import com.learn.seckill.domian.User;
import com.learn.seckill.Result.Result;
import com.learn.seckill.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/demo")
public class Demo {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;
    @RequestMapping("/info")
    @ResponseBody
    public Result<User> info(User user) {
        return Result.success(user);
    }


    @RequestMapping("/login")
    public String login(Model model) {
        return "sys/login";

    }

//    @RequestMapping("/do_login")
//    @ResponseBody
//    public String doLogin(HttpServletResponse response, @Valid LoginVO loginVo) {//加入JSR303参数校验
////        log.info(loginVo.toString());
////        String token = userService.login(response, loginVo);
//        return token;
//    }
    @RequestMapping("/db/get")
    @ResponseBody
    public User DBGet() {
        User user=userService.getById("18181818181");
        return user;
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public User RedisGet() {

        User str= redisService.get(UserKey.token,""+1,User.class);
        System.out.println(str);
        return str;
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public String RedisSet() {
        User user = new User();
        user.setId("1");
        user.setPassword("password");
        Boolean str= redisService.set(UserKey.token,""+1,user);
        System.out.println("是否添加成功： "+str);
        return str.toString();
    }
}
