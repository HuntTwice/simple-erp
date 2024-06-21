package com.zc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zc.common.constants.*;
import com.zc.common.enums.ResultCodeEnum;
import com.zc.common.enums.RoleEnum;
import com.zc.common.result.PageResult;
import com.zc.common.exception.CustomException;
import com.zc.mapper.UserMapper;
import com.zc.pojo.dto.UserDTO;
import com.zc.pojo.dto.UserLoginDTO;
import com.zc.pojo.entity.User;
import com.zc.pojo.vo.UserQueryVO;
import com.zc.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.zc.common.constants.UserInfoConstant.DEFAULT_DEPARTMENT;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public void register(UserLoginDTO registerDtO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(registerDtO, userDTO);
        add(userDTO);
    }

    @Override
    public User login(UserLoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        User dbUser = userMapper.getByUsername(username);
        //查看数据库是否有这个用户
        if (dbUser == null)
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        //如果用户存在，则比对密码是否正确
        if (!loginDTO.getPassword().equals(dbUser.getPassword()))
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);

        //查看账号是否被禁用
        if (dbUser.getStatus() == false)
            //todo 需要修改
            throw new CustomException(ResultCodeEnum.ACCOUNT_DISABLE);
        return dbUser;
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteById(id);
        }
    }

    @Override
    public UserQueryVO userInfo(String username) {
        UserQueryVO userQueryVO = userMapper.get1ByUsername(username);
        return userQueryVO;
    }


    //新增员工记录
    @Override
    public void add(UserDTO userDTO) {
        //通过用户名查询数据库是否有这条记录
        User dbUser = userMapper.getByUsername(userDTO.getUsername());
        //如果存在这条记录，抛出异常，插入失败
        if (dbUser != null)
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        //如果没有设置密码，设置默认的密码
        if (user.getPassword() == null || user.getPassword().equals(""))
            user.setPassword(PasswordConstant.DEFAULT_PASSWORD);


        if (user.getStatus() == null)
            //设置账号的状态
            user.setStatus(StatusConstant.ENABLE);

        if (user.getRole() == null || user.getRole().equals(""))
            //设置账号的默认权限
            user.setRole(RoleEnum.STAFF.name());

        if (user.getDepartmentId() == null)
            //设置部门默认的部门id
            user.setDepartmentId(UserInfoConstant.DEFAULT_DEPARTMENT);

        //设置默认昵称，头像，地址，性别，邮箱，年龄，手机号
        //todo 可以随机生成 也可以删除
        if (user.getName() == null || user.getName().equals(""))
            user.setName(UserInfoConstant.DEFAULT_NAME);
        if (user.getAvatar() == null || user.getAvatar().equals(""))
            user.setAvatar(UserInfoConstant.DEFAULT_AVATAR);
        if (user.getAddress() == null || user.getAddress().equals(""))
            user.setAddress(UserInfoConstant.DEFAULT_ADDRESS);
        if (user.getSex() == null || user.getSex().equals(""))
            user.setSex(UserInfoConstant.DEFAULT_SEX);
        if (user.getEmail() == null || user.getEmail().equals(""))
            user.setEmail(UserInfoConstant.DEFAULT_EMAIL);
        if (user.getAge() == null)
            user.setAge(UserInfoConstant.DEFAULT_AGE);
        if (user.getPhone() == null || user.getPhone().equals(""))
            user.setPhone(UserInfoConstant.DEFAULT_PHONE);


        userMapper.add(user);
    }

    //通过id删除员工账号
    @Override
    public void deleteById(Integer id) {
        int updates = userMapper.deleteById(id);
        if (updates == 0)
            throw new CustomException(ResultCodeEnum.DELETE_ERROR);
    }

    //通过id修改员工信息
    @Override
    public void update(UserDTO userDTO) {
        //根据username去数据库查找这条记录
        User dbUser = userMapper.getById(userDTO.getId());

        //如果这条记录不存在，抛出异常，修改失败
        if (dbUser == null)
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userMapper.updateById(user);
    }


    /**
     * 条件分页查询
     *
     * @param userDTO  查询条件
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 自定义对象PageResult 包含两个属性 total 和 records
     */
    @Override
    public PageResult pageSelect(UserDTO userDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Page<UserQueryVO> page = userMapper.pageQuery(userDTO);
        long total = page.getTotal();
        List<UserQueryVO> records = page.getResult();
        return new PageResult<>(total, records);
    }


}
