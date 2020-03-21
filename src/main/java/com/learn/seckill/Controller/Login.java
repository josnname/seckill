package com.learn.seckill.Controller;

import com.learn.seckill.Result.Result;
import com.learn.seckill.Service.UserService;
import com.learn.seckill.Vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class Login {
    @Autowired
    UserService userService;
    @RequestMapping("/to_login")
    public String toLogin(){
        System.out.println("进入登陆界面！");
        return "sys/login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(HttpServletResponse response,@Valid LoginVO loginVo){
        userService.doLogin(response,loginVo);
        return Result.success(true);

    }

}
