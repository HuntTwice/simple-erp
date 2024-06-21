package com.zc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.enums.ResultCodeEnum;
import com.zc.common.exception.CustomException;
import com.zc.common.result.PageResult;
import com.zc.mapper.GoodsMapper;
import com.zc.mapper.StockBackMapper;
import com.zc.pojo.dto.StockBackDTO;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.entity.StockBack;
import com.zc.pojo.vo.StockBackVO;
import com.zc.service.StockBackService;
import com.zc.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品退货
 */
@Slf4j
@Service
public class StockBackServiceImpl implements StockBackService {

    @Autowired
    private StockBackMapper stockBackMapper;

    @Autowired
    private GoodsMapper goodsMapper;


    //添加退货信息
    @Transactional
    @Override
    public void add(StockBack stockBack) {

        log.info("stockBack:{}", stockBack);

        //设置当前的退货时间
        stockBack.setTime(LocalDateTime.now());

        //设置当前的操作人
        stockBack.setUser(ThreadLocalUtil.get());


        //通过当前的商品id查询数据库中的商品信息
        Goods goods = goodsMapper.getById(stockBack.getGoodsId());

        //需要判断商品的库存是否大于退货的数量，如果库存小于退货数，则抛出异常
        if (goods.getNum() < stockBack.getNum())
            throw new CustomException(ResultCodeEnum.INVENTORY_NOT_ENOUGH);
        else {
            //修改商品的数量
            goods.setNum(goods.getNum() - stockBack.getNum());
            //向商品表中提交修改
            goodsMapper.updateById(goods);
            //往商品退货表中添加商品退货信息
            stockBackMapper.add(stockBack);
        }

    }


    @Override
    public PageResult pageSelect(StockBackDTO stockBackDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<StockBackVO> page = stockBackMapper.pageQuery(stockBackDTO);
        long total = page.getTotal();
        List<StockBackVO> records = page.getResult();
        return new PageResult<>(total, records);
    }


    @Override
    public List<StockBackVO> getAll() {
        return stockBackMapper.getAll();
    }
}
