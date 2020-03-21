package com.learn.seckill.Controller;


import com.learn.seckill.Result.CodeMsg;
import com.learn.seckill.Service.GoodsService;
import com.learn.seckill.Service.OrderService;
import com.learn.seckill.Vo.GoodsVo;
import com.learn.seckill.domian.SeckillOrder;
import com.learn.seckill.domian.User;
import com.learn.seckill.domian.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/seckill")
public class seckill {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/do_seckill")
    public String do_seckill(Model model, User user, @RequestParam("goodsId" )long goodsId){
        if(user == null){
            return "login";
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        if(goodsVo.getStock_count() <= 0){
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER);
            return "sys/seckill_fail";
        }

        SeckillOrder seckillOrder =orderService.getOrderByUserIdGoodsId(user.getId(),goodsId);
        if(seckillOrder != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL);
            return "sys/seckill_fail";
        }

        OrderInfo orderInfo = orderService.createOrder(user,goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);
        return "sys/order_detail";
    }
}
