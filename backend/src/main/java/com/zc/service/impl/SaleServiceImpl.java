package com.zc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.enums.ResultCodeEnum;
import com.zc.common.exception.CustomException;
import com.zc.common.result.PageResult;
import com.zc.mapper.GoodsMapper;
import com.zc.mapper.SaleMapper;
import com.zc.pojo.dto.SaleDTO;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.entity.Sale;
import com.zc.pojo.vo.SaleVO;
import com.zc.service.SaleService;
import com.zc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private GoodsMapper goodsMapper;


    @Transactional
    @Override
    public void add(Sale sale) {
        //todo 需要优化

        //获取当前的操作用户id
        Integer userId = ThreadLocalUtil.get();
        //设置销售的用户
        sale.setUserId(userId);
        //设置销售的总价格
        sale.setTotalPrice(sale.getPrice() * sale.getNum());


        Goods goods = goodsMapper.getById(sale.getGoodsId());

        //判断当前商品的库存是否充足，如果销售数量大于库存则报错
        if (goods.getNum() < sale.getNum())
            throw new CustomException(ResultCodeEnum.INVENTORY_NOT_ENOUGH);
        else{
            //销售的同时，在库存中减去对应的数量
            goods.setNum(goods.getNum()-sale.getNum());
            goodsMapper.updateById(goods);
            saleMapper.add(sale);
        }


    }

    @Override
    public void deleteById(Integer id) {
        saleMapper.deleteById(id);
    }



    @Override
    public PageResult pageSelect(SaleDTO saleDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<SaleVO> page = saleMapper.pageQuery(saleDTO);
        long total = page.getTotal();
        List<SaleVO> records = page.getResult();
        return new PageResult<>(total, records);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            saleMapper.deleteById(id);
        }
    }

    @Override
    public List<SaleVO> getAll() {
        return saleMapper.getAll();
    }
}
