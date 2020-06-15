package com.asura.enxin.controller;


import com.asura.enxin.entity.User;
import com.asura.enxin.service.IUserService;
import com.asura.enxin.entity.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表; InnoDB free: 6144 kB 前端控制器
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    //登录
    @PostMapping(value = "/login")
    public ResponseEntity<Result> login(@RequestParam(value = "username", required = false) String username,
                                       @RequestParam(value = "password", required = false) String password,
                                       HttpServletRequest request) {

        Result result = userService.validateUser(username, password);
        return ResponseEntity.ok(result);
    }

    //注册
    @PutMapping(value="/register")
    public ResponseEntity<Result> register(@RequestBody User user){
        userService.register(user);
        return ResponseEntity.ok(new Result("注册成功",true));
    }
}

