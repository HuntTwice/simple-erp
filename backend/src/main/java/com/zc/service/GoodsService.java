package com.zc.service;


import com.zc.common.result.PageResult;
import com.zc.pojo.dto.GoodsDTO;
import com.zc.pojo.entity.Goods;
import com.zc.pojo.vo.GoodsVO;

import java.util.List;

public interface GoodsService {



    //新增部门记录
    void add(Goods goods);

    //通过id删除部门信息
    void deleteById(Integer id);

    //通过id修改部门信息
    void update(Goods goods);


    //分页查询
    PageResult pageSelect(GoodsDTO goodsDTO ,Integer pageNum, Integer pageSize);



    //批量删除
    void deleteBatch(List<Integer> ids);

    List<GoodsVO> getAll(GoodsDTO goodsDTO);
}
