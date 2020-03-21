package com.learn.seckill.Vo;

import com.learn.seckill.domian.Goods;

import java.util.Date;

public class GoodsVo extends Goods {
    private Double seckill_price;
    private Integer stock_count;
    private Date start_date;
    private Date end_date;
    private Integer version;
    public GoodsVo (){
        System.out.println("GoodsVo被调用！");
    }
    public Double getSeckill_price() {
        return seckill_price;
    }

    public void setSeckill_price(Double seckill_price) {
        this.seckill_price = seckill_price;
    }

    public Integer getStock_count() {
        return stock_count;
    }

    public void setStock_count(Integer stock_count) {
        this.stock_count = stock_count;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "seckill_price=" + seckill_price +
                ", stock_count=" + stock_count +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", version=" + version +
                '}';
    }
}
