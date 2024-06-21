package com.zc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.result.PageResult;
import com.zc.mapper.GoodsMapper;
import com.zc.pojo.dto.GoodsDTO;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.vo.GoodsVO;
import com.zc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void add(Goods goods) {
        //todo 硬编码，需修改
        goods.setNum(0);
        goodsMapper.add(goods);
    }

    @Override
    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    @Override
    public void update(Goods goods) {
        goodsMapper.updateById(goods);
    }

    @Override
    public PageResult pageSelect(GoodsDTO goodsDTO, Integer pageNum, Integer pageSize) {
            PageHelper.startPage(pageNum, pageSize);
            Page<GoodsVO> page = goodsMapper.pageQuery(goodsDTO);
            long total = page.getTotal();
            List<GoodsVO> records = page.getResult();
            return new PageResult<>(total, records);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            goodsMapper.deleteById(id);
        }
    }

    @Override
    public List<GoodsVO> getAll(GoodsDTO goodsDTO) {
        return goodsMapper.pageQuery(goodsDTO);
    }
}
