package com.example.springboot03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot03.common.ErrorCode;
import com.example.springboot03.exception.BusinessException;
import com.example.springboot03.model.domain.User;
import com.example.springboot03.service.UserService;
import com.example.springboot03.Mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.springboot03.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author LTX-Xuwenze
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-06-18 15:30:17
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;

    private static final String SAL = "yupi";

  /*
  用户登录状态键
   */


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {

//        a、非空
//        b账户长度不小于4位
//        c密码就不小于8位
//        d账户不能重复
//        e账户不包含特殊字符
//         f密码和校验密码相同
//        对密码进行加密（密码千万不要直接以明文存储到数据库中）
//        向数据库插入用户数据

        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }

        if(userPassword.length()<8||checkPassword.length()<8){
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        if (planetCode.length()>5){
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"认证编号过长");
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return -1;
        }
//        密码和校验密码相同
        if(!userPassword.equals(checkPassword)){
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count>0){
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }
        //编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count>0){
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"认证编号重复");
        }


//        对密码进行加密
//        final String SAL = "yupi";
        String encryptPassword = DigestUtils.md5DigestAsHex((SAL+userPassword).getBytes());

//        向数据库插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return user.getId();

    }



    @Override
    public User userLogin(String userAccount, String userPassword,HttpServletRequest request) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length()<4){
            return null;
        }
        if(userPassword.length()<8){
            return null;
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return null;
        }
        //密码和校验密码相同
//        if(userPassword.equals(checkPassword)){
//            return -1;
//        }
        //账户不能重复
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("userAccount",userAccount);
//        long count = userMapper.selectCount(queryWrapper);
//        if (count>0){
//            return -1;
//        }

//        对密码进行加密
//        final String SAL = "yupi";
        String encryptPassword = DigestUtils.md5DigestAsHex((SAL+userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user=userMapper.selectOne(queryWrapper);

        //用户不存在
        if (user ==null){
            log.info("user login failed,userAccount Cannot match userPassword");
            return null;
        }





//        4.用户脱敏
        User safetyUser = getSafetyUser(user);
//        safetyUser.setUpdateTime();
//        safetyUser.setIsDelete();
        //记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }

    @Override
    public User getSafetyUser(User originUser){
        if(originUser ==null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
//        safetyUser.setUserPassword(user.getEmail());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录状态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




