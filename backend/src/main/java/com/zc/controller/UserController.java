package com.zc.controller;


import com.zc.common.anno.BusinessOperation;
import com.zc.common.anno.BusinessTitle;
import com.zc.common.constants.JwtClaimsConstant;
import com.zc.common.enums.OperationType;
import com.zc.common.enums.TitleType;
import com.zc.common.properties.JwtProperties;
import com.zc.common.result.PageResult;
import com.zc.common.result.Result;
import com.zc.pojo.dto.UserDTO;
import com.zc.pojo.dto.UserLoginDTO;
import com.zc.pojo.entity.User;
import com.zc.pojo.vo.UserLoginVO;
import com.zc.pojo.vo.UserQueryVO;
import com.zc.service.UserService;
import com.zc.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/users")
@Api(tags = "员工信息管理")
@BusinessTitle(titleType = TitleType.USER)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/register")
    @ApiOperation("用户注册")

    public Result register(@RequestBody UserLoginDTO registerDTO){
        userService.register(registerDTO);
        return Result.success();
    }

    @PostMapping
    @ApiOperation("创建新的员工记录")
    @BusinessOperation(operationType = OperationType.INSERT)
    public Result add(@RequestBody UserDTO userDTO) {
        userService.add(userDTO);
        return Result.success();
    }

    @GetMapping("/{username}")
    @ApiOperation("查询当前员工信息")
    public Result userInfo(@PathVariable String username) {
        UserQueryVO userQueryVO = userService.userInfo(username);
        return Result.success(userQueryVO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除员工的信息")
    @BusinessOperation(operationType = OperationType.DELETE)
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除用户信息")
    @BusinessOperation(operationType = OperationType.DELETE_BATCH)
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改员工信息")
    @BusinessOperation(operationType = OperationType.UPDATE)
    public Result update(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return Result.success();
    }


    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result pageSelect(@RequestBody(required = false) UserDTO userDTO,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize) {
        PageResult pageResult = userService.pageSelect(userDTO, pageNum, pageSize);
        return Result.success(pageResult);
    }


    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result login(@RequestBody UserLoginDTO loginDTO) {
        //登陆成功，返回数据库完整的user对象
        User user = userService.login(loginDTO);

        //jwt令牌中用户自定义的数据
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        claims.put(JwtClaimsConstant.USER_NAME, user.getUsername());
        claims.put(JwtClaimsConstant.NAME,user.getName());
        claims.put(JwtClaimsConstant.ROLE, user.getRole());

        //生成token
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        //构建返回前端的对象
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())  //用户id
                .username(user.getUsername()) //用户名
                .role(user.getRole()) //角色
                .avatar(user.getAvatar()) //头像
                .name(user.getName()) //姓名
                .token(token) //token
                .build();

        return Result.success(userLoginVO);
    }
}
