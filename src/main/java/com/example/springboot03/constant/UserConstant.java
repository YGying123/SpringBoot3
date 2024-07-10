package com.example.springboot03.constant;

public interface UserConstant {
//    把用户登录状态键搬过来

    String USER_LOGIN_STATE = "userLoginState";

    //----权限-----
    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;


    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;

}
