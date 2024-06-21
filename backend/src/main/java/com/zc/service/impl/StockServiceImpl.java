package com.zc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.result.PageResult;
import com.zc.mapper.GoodsMapper;
import com.zc.mapper.StockMapper;
import com.zc.pojo.dto.StockDTO;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.entity.Stock;
import com.zc.pojo.vo.StockVO;
import com.zc.service.StockService;
import com.zc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private GoodsMapper goodsMapper;


    @Transactional
    @Override
    public void add(Stock stock) {
        //todo 需要优化

        //获取当前的操作用户id
        Integer userId = ThreadLocalUtil.get();
        //设置进货的用户
        stock.setUserId(userId);
        //设置进货的总价格
        stock.setTotalPrice(stock.getPrice() * stock.getNum());

        //进货的同时，增加商品的数量
        Goods goods = goodsMapper.getById(stock.getGoodsId());
        goods.setNum(goods.getNum()+stock.getNum());
        goodsMapper.updateById(goods);

        stockMapper.add(stock);

    }

    @Override
    public void deleteById(Integer id) {
        stockMapper.deleteById(id);
    }

    @Override
    public void update(Stock stock) {
        stock.setTotalPrice(stock.getPrice() * stock.getNum());
        stockMapper.updateById(stock);
    }

    @Override
    public PageResult pageSelect(StockDTO stockDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<StockVO> page = stockMapper.pageQuery(stockDTO);
        long total = page.getTotal();
        List<StockVO> records = page.getResult();
        return new PageResult<>(total, records);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            stockMapper.deleteById(id);
        }
    }

    @Override
    public List<StockVO> getAll() {
        return stockMapper.getAll();
    }
}
