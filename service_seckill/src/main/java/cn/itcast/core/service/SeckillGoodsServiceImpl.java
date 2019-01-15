package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    //查询秒杀商品
    @Override
    public List<SeckillGoods> findList() {
        SeckillGoodsQuery query = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = query.createCriteria();
        criteria.andStatusEqualTo("1");//审核状态
        criteria.andStockCountGreaterThan(0);//有库存
        criteria.andStartTimeLessThanOrEqualTo(new Date());
        criteria.andEndTimeGreaterThan(new Date());
        return seckillGoodsDao.selectByExample(query);
    }

    @Override
    public PageResult findByPage(SeckillGoods seckillGoods, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        SeckillGoodsQuery query = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = query.createCriteria();
        if (seckillGoods!=null){
            if (seckillGoods.getTitle()!= null && !"".equals(seckillGoods.getTitle())){
                criteria.andTitleLike("%"+seckillGoods.getTitle()+"%");
            }
        }
        criteria.andStatusEqualTo("0");//审核状态
        Page<SeckillGoods> list = (Page<SeckillGoods>)seckillGoodsDao.selectByExample(query);
        return new PageResult(list.getTotal(), list.getResult());
    }
}
