package com.learn.seckill.Controller;


import com.alibaba.druid.util.StringUtils;
import com.learn.seckill.Redis.RedisService;
import com.learn.seckill.Service.GoodsService;
import com.learn.seckill.Vo.GoodsVo;
import com.learn.seckill.domian.User;
import com.learn.seckill.Redis.GoodsKey;
import com.learn.seckill.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class Goods {
    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response,Model model, User user){
        if(user == null){
            return "sys/login";
        }
        String html = redisService.get(GoodsKey.getGoodsList,"html",String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goodsVo:goodsList){
            System.out.println(goodsVo);
        }
        model.addAttribute("goodsList",goodsList);

        WebContext wc = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("sys/goods_list",wc);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"html",html);
        }

        return html;
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable("goodsId")long goodsId){
        if(user == null){
            return "sys/login";
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        System.out.println(goodsVo);
        model.addAttribute("goods",goodsVo);
        model.addAttribute("user",user);
        long startTime = goodsVo.getStart_date().getTime();
        long endTime = goodsVo.getEnd_date().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startTime) {//秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {//秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "sys/goods_detail";
    }
}


