package com.learn.seckill.Dao;


import com.learn.seckill.domian.OrderInfo;
import com.learn.seckill.domian.SeckillOrder;
import org.apache.ibatis.annotations.*;

/**
 * Created by jiangyunxiong on 2018/5/23.
 */
@Mapper
public interface OrderDao {


    @Select("select * from sk_order where user_id = #{userId} and goods_id = #{goodsId}")
    public SeckillOrder getOrderByUserIdGoodsId(@Param("userId") String userId, @Param("goodsId") long goodsId);


    /**
     * 通过@SelectKey使insert成功后返回主键id，也就是订单id
     * @param orderInfo
     * @return
     */
    @Insert("insert into sk_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{user_id}, #{goods_id}, #{goods_name}, #{goods_count}, #{goods_price}, #{order_channel},#{status},#{create_date} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public int insert(OrderInfo orderInfo);


    @Insert("insert into sk_order (user_id, goods_id, order_id)values(#{user_id}, #{goods_id}, #{order_id})")
    public int insertSeckillOrder(SeckillOrder order);

    @Update("update sk_goods_seckill set stock_count = stock_count -1 where goods_id = 1")
    public int updateGoodsCount(long id);

    @Select("select * from sk_order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId") long orderId);

}
