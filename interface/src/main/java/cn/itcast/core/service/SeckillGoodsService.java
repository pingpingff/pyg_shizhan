package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.util.List;

public interface SeckillGoodsService {
    /**
     * 返回当前正在参与秒杀的商品
     * @return
     */
    public List<SeckillGoods> findList();
    public PageResult findByPage(SeckillGoods seckillGoods, Integer page, Integer rows);
}
