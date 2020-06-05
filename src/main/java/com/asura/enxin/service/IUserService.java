package com.asura.enxin.service;

import com.asura.enxin.entity.User;
import com.asura.enxin.entity.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表; InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IUserService extends IService<User> {

    Result validateUser(String loginName, String password);

    User getUserByLoginName(String loginName);
}
