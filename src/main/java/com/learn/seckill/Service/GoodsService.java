package com.learn.seckill.Service;

import com.learn.seckill.Dao.GoodsDao;
import com.learn.seckill.Vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService{
    @Autowired
    GoodsDao goodsDao;


    /**
     * 查询商品列表
     *
     * @return
     */
    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }


    /**
     * 根据商品id获取商品详细信息
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(long goodsid) {
        return goodsDao.getGoodsVoByGoodsId(goodsid);
    }

}
