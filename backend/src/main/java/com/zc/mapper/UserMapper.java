package com.zc.mapper;

import com.github.pagehelper.Page;
import com.zc.common.anno.AutoFill;
import com.zc.common.enums.OperationType;
import com.zc.pojo.dto.UserDTO;
import com.zc.pojo.entity.User;
import com.zc.pojo.vo.UserQueryVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

@Mapper
public interface UserMapper {


    //通过用户名查找用户信息
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    //查询用户信息和对应的部门
    @Select("select user.*,d.name as departmentName from user left join department d on user.department_id = d.id where username = #{username}")
    UserQueryVO get1ByUsername(String username);


    //通过id删除用户信息
    @Delete("delete from user where id = #{id}")
    int deleteById(Integer id);

    //根据id更新用户信息
    @AutoFill(value = OperationType.UPDATE)
    int updateById(User user);

    //根据用户名更新用户信息
    @AutoFill(value = OperationType.UPDATE)
    int updateByUsername(User user);

    //新增用户
    @AutoFill(value = OperationType.INSERT)
    int add(User user);

    //通过id查询用户
    @Select("select * from user where id = #{id}")
    User getById(Integer id);

    //条件分页查询
    Page<UserQueryVO> pageQuery(UserDTO userDTO);
}
