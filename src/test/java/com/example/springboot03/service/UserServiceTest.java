package com.example.springboot03.service;
import java.util.Date;

import com.example.springboot03.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void testAddUser(){
        User user = new User();
//        user.setId(0L);
        user.setUsername("appYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://www.baidu.com");
        user.setGender(0);
        user.setUserPassword("16025552");
        user.setEmail("123");
//        user.setUserStatus(0);
        user.setPhone("456");
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
//        user.setIsDelete(0);



//        userService.save();
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

//    @Test
//    public void userRegister() {
//        String userAccount = "yupi";
//        String userPassword = "";
//        String checkPassword = "123456";
//        long result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//    }


    @Test
    public void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode="1";

        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "dogYupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount ="yupis";
        userPassword="12345678";
        checkPassword="12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount ="yupi";
        userPassword="12345678";
        checkPassword="15634452";
        result = userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);
    }



//    @Test
//    public void testUserRegister() {
//        String userAccount = "yupi";
//        String userPassword = "12345678";
//        String checkPassword = "123456";
//        Long result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//    }
}