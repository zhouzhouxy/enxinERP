package com.asura.enxin.service.impl;

import com.asura.enxin.entity.User;
import com.asura.enxin.mapper.UserMapper;
import com.asura.enxin.service.IUserService;
import com.asura.enxin.entity.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 用户表; InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
 @Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Result validateUser(String loginName, String password) {


        //判断该用户的状态
        try {
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.lambda().eq(User::getLoginName,loginName).eq(User::getStatus,0);
            List<User> users = userMapper.selectList(qw);
            if(users.size()==0&& CollectionUtils.isEmpty(users)){
                return new Result("用户名错误或状态不可用",false);
            }
        } catch (Exception e) {
            logger.error("登录失败"+e);
        }

        try {
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.lambda().eq(User::getLoginName,loginName).eq(User::getPassword,password).eq(User::getStatus,0);
            User user = userMapper.selectOne(qw);
            if(user!=null){
                return new Result("登录成功",true);
            }else{
                return new Result("用户名或密码错误",false);
            }
        } catch (Exception e) {
            logger.error("登录失败"+e);
//            e.printStackTrace();
        }
        return new Result("登录失败",false);
    }

    @Override
    public User getUserByLoginName(String loginName) {
        return null;
    }
}
