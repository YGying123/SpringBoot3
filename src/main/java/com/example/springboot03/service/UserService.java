package com.example.springboot03.service;

import com.example.springboot03.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author LTX-Xuwenze
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-06-18 15:30:17
*/

/**
 * 用户服务
 * @author Administrator
 */
public interface UserService extends IService<User> {

//    写注册代理

    /**
     *用户注释
     * @param userAccount  用户账户
     * @param userPassword  用户密码
     * @param checkPassword  校验密码
     * @return  新用户 id
     */
    /**
     * 用户登录状态键

     */


    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    /**
     * 用户登录
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
