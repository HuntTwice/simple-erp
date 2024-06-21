package com.zc.service;


import com.zc.common.result.PageResult;
import com.zc.pojo.dto.UserDTO;
import com.zc.pojo.dto.UserLoginDTO;

import com.zc.pojo.entity.User;
import com.zc.pojo.vo.UserQueryVO;

import java.util.List;

public interface UserService {


    //新增员工记录
    void add(UserDTO userDTO);

    //通过id删除员工账号
    void deleteById(Integer id);

    //通过id修改员工信息
    void update(UserDTO userDTO);


    //分页查询
    PageResult pageSelect(UserDTO userDTO, Integer pageNum, Integer pageSize);

    User login(UserLoginDTO userDTO);

    void register(UserLoginDTO registerDTO);

    void deleteBatch(List<Integer> ids);


    UserQueryVO userInfo(String username);
}
