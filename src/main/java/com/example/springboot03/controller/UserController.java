package com.example.springboot03.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot03.common.BaseResponse;
import com.example.springboot03.common.ErrorCode;
import com.example.springboot03.common.ResultUtils;
import com.example.springboot03.exception.BusinessException;
import com.example.springboot03.model.domain.User;
import com.example.springboot03.model.domain.request.UserLoginRequest;
import com.example.springboot03.model.domain.request.UserRegisterRequest;

import com.example.springboot03.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springboot03.constant.UserConstant.ADMIN_ROLE;
import static com.example.springboot03.constant.UserConstant.USER_LOGIN_STATE;
import static com.example.springboot03.service.UserService.*;

@RestController
@RequestMapping("/user")

public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        if (userRegisterRequest ==null){
//            return null;
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);

        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
//            return null;
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
       return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest ==null){
//            return null;
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
//        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
//            return null;
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user =  userService.userLogin(userAccount,userPassword,request);
        return ResultUtils.success(user);
    }

    @PostMapping("/loginout")
    public BaseResponse<Integer> userLoginout(HttpServletRequest request){
        if (request ==null){
//            return null;
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result =  userService.userLogout(request);
        return ResultUtils.success(result);
    }



    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser ==null){
//            return null;
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        //todo校验用户是否合法
        User user = userService.getById(userId);
        User safeUser =  userService.getSafetyUser(user);
        return ResultUtils.success(safeUser);
    }


    @GetMapping("/search")
    public BaseResponse<List<User>> serchUsers(String username,HttpServletRequest request){
//        //仅管理员可查询
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User users = (User) userObj;
//        if (users ==null || users.getUserRole() != ADMIN_ROLE){
//            return new ArrayList<>();
//        }
        if(!isAdmin(request)){
//            return new ArrayList<>();
            throw new BusinessException(ErrorCode.NO_AUTH);

        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list  = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
//        return userService.list(queryWrapper);
//        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }


    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request){
//        //仅管理员可查询
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User user = (User) userObj;
//        if (user ==null || user.getUserRole() != ADMIN_ROLE){
//            return  false;
//        }
        if (isAdmin(request)){
//            return null;
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id<=0){
//            return null;
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b =  userService.removeById(id);
        return ResultUtils.success(b);
    }


    public boolean isAdmin(HttpServletRequest request){
        //仅管理员可查询
        Object userOBJ = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userOBJ;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }


}
