package com.learn.seckill.Service;


import com.learn.seckill.Dao.OrderDao;
import com.learn.seckill.Redis.RedisService;
import com.learn.seckill.Vo.GoodsVo;
import com.learn.seckill.domian.User;
import com.learn.seckill.domian.OrderInfo;
import com.learn.seckill.domian.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by jiangyunxiong on 2018/5/23.
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public SeckillOrder getOrderByUserIdGoodsId(String userId, long goodsId) {
        return orderDao.getOrderByUserIdGoodsId(userId,goodsId);
//        return redisService.get(OrderKey.getSeckillOrderByUidGid, "" + userId + "_" + goodsId, SeckillOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    /**
     * 因为要同时分别在订单详情表和秒杀订单表都新增一条数据，所以要保证两个操作是一个事物
     */
    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreate_date(new Date());
        orderInfo.setDelivery_addr_id(0L);
        orderInfo.setGoods_count(1);
        orderInfo.setGoods_id(goods.getId());
        orderInfo.setGoods_name(goods.getGoods_name());
        orderInfo.setGoods_price(goods.getGoods_price());
        orderInfo.setOrder_channel(1);
        orderInfo.setStatus(0);
        orderInfo.setUser_id(user.getId());
        orderDao.insert(orderInfo);

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoods_id(goods.getId());
        seckillOrder.setOrder_id(orderInfo.getId());
        seckillOrder.setUser_id(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);
        orderDao.updateGoodsCount(orderInfo.getId());
//        redisService.set(OrderKey.getSeckillOrderByUidGid, "" + user.getId() + "_" + goods.getId(), seckillOrder);

        return orderInfo;
    }


}
